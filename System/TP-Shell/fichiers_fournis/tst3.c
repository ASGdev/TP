/*
 * Copyright (C) 2002, Simon Nieuviarts
 */

#include <stdio.h>
#include "readcmd.h"
#include <unistd.h> // AJOUT
#include <sys/types.h> // AJOUT
#include <sys/wait.h> // AJOUT

int main()
{
		char *in;
		char *out;
		char ***seq;
		int pid, status;
	while (1)
	{
		struct cmdline *cmd;
		
		printf("shell> ");
		cmd = readcmd();	
		
		in = cmd->in;
		out = cmd->out;
		seq = cmd->seq;
		
		pid = fork();
		if(pid == -1)
		{
			perror("[E] erreur création du fork()");
			exit(-1);
		}
		else if(pid == 0) // code du fils	
		{
			execvp(seq[0][0],seq[0]);
		}
		else// le pere attend la terminaison du fils
		{
			wait(&status);
		}
	}
}
