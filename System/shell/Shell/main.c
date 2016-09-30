#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <fcntl.h>

#include "readcmd.h"
//#define DEBUG

int main()
{
	while (1) {
		struct cmdline *l;
		int i, j;

		printf("shell> ");
		l = readcmd();

		/* If input stream closed, normal termination */
		if (!l) {
			printf("exit\n");
			exit(0);
		}

		if (l->err) {
			/* Syntax error, read another command */
			printf("error: %s\n", l->err);
			continue;
		}else{
            #ifdef DEBUG

            if (l->in) printf("in: %s\n", l->in);
            if (l->out) printf("out: %s\n", l->out);

            /* Display each command of the pipe */
            for (i=0; l->seq[i]!=0; i++) {}
                char **cmd = l->seq[i];
                printf("seq[%d]: ", i);
                for (j=0; cmd[j]!=0; j++) {
                    printf("%s ", cmd[j]);
                }
                printf("\n");
            }
            #endif // DEBUG


            pid_t pid ;
            do{
                pid = fork();            }while(pid == -1);

            if(pid == 0){

                    char **cmd = l->seq[0];
                    if(l->in){
                       int filedesc;
                        filedesc = open(l->out,O_CREAT|O_RDWR);
                        dup2(filedesc,STDIN_FILENO);
                        close(filedesc);
                    }
                    if(l->out){
                        int filedesc2;
                        filedesc2 = open(l->out,O_CREAT|O_RDWR,S_IRWXU);
                        dup2(filedesc2,STDOUT_FILENO);
                        close(filedesc2);
                    }


                       execvp(cmd[0],cmd);



            }else{
                int status;
                pid_t res = wait(&status);
            }








}
}
}

