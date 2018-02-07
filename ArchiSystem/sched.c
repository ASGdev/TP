/*
 * Copyright(C) 2011-2016 Pedro H. Penna   <pedrohenriquepenna@gmail.com>
 *              2015-2016 Davidson Francis <davidsondfgl@hotmail.com>
 *
 * This file is part of Nanvix.
 *
 * Nanvix is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Nanvix is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Nanvix. If not, see <http://www.gnu.org/licenses/>.
 */

#include <nanvix/clock.h>
#include <nanvix/const.h>
#include <nanvix/hal.h>
#include <nanvix/pm.h>
#include <signal.h>
#include <nanvix/config.h>


#include <stdlib.h>
//==================================ADDED BY THE SQUALE TEAM
struct tab_oldness
{
	int oldness[PROC_MAX]; 	// The number of quantums remaining for one process
};
typedef struct tab_oldness tab_oldness;

PRIVATE struct tab_oldness tab;

//Initiate the tab_prio tab with the processes in proctab
PUBLIC void init_tab_oldness();

//Get the number of quantum according to the process priority
PUBLIC int translate_priority(struct process p);

//TODO -> check the return value if works correctly
PUBLIC int translate_priority(struct process p)
{
	int niceness = p.nice; 	//number between 0 and 39

	return (niceness/4)+1;	//The number of quantum is 1 + the rank in between niceness/4
							//we have chosen 4 for the size of quantum 
}


PUBLIC void init_tab_prio()
{
	/*	At a point this will be set up to 1 which means that 
	*	the following i in the loop will be null in the proctab
	*/ 

	for(int i = 0 ; i < PROC_MAX; i++)
	{
		
	}
}

PUBLIC int do_we_re_init ()
{
	
	for(int i = 0 ; i < PROC_MAX ;  i++)
	{
		
	}
	return 0;
}


//================================== SQUALE TEAM OVER


/**
 * @brief Schedules a process to execution.
 * 
 * @param proc Process to be scheduled.
 */
PUBLIC void sched(struct process *proc)
{
	proc->state = PROC_READY;
	proc->counter = 0;
}

/**
 * @brief Stops the current running process.
 */
PUBLIC void stop(void)
{
	curr_proc->state = PROC_STOPPED;
	sndsig(curr_proc->father, SIGCHLD);
	yield();
}

/**
 * @brief Resumes a process.
 * 
 * @param proc Process to be resumed.
 * 
 * @note The process must stopped to be resumed.
 */
PUBLIC void resume(struct process *proc)
{	
	/* Resume only if process has stopped. */
	if (proc->state == PROC_STOPPED)
		sched(proc);
}

/**
 * @brief Yields the processor.
 */
PUBLIC void yield(void)
{
	struct process *p;    /* Working process.     */
    struct process *next; /* Next process to run. */

    /* Re-schedule process for execution. */
        if (curr_proc->state == PROC_RUNNING)
                sched(curr_proc);

        /* Remember this process. */
        last_proc = curr_proc;

        /* Check alarm. */
        for (p = FIRST_PROC; p <= LAST_PROC; p++)
        {
                /* Skip invalid processes. */
                if (!IS_VALID(p))
                        continue;

                /* Alarm has expired. */
                if ((p->alarm) && (p->alarm < ticks))
                        p->alarm = 1, sndsig(p, SIGALRM);
        }

        /* Choose a process to run next. */
        next = IDLE;
        for (p = FIRST_PROC; p <= LAST_PROC; p++)
        {
                /* Skip non-ready process. */
                if (p->state != PROC_READY)
                        continue;

                /*
                 * Process with higher
                 * waiting time found.
                 */
                if (p->counter > next->counter)
                {
                        next->counter++;
                        next = p;
                }

                /*
                 * Increment waiting
                 * time of process.
                 */
                else
                        p->counter++;
        }

        /* Switch to next process. */
        next->priority = PRIO_USER;
        next->state = PROC_RUNNING;
        next->counter = PROC_QUANTUM;
        switch_to(next);

}


