/*
 * Copyright (C) SIEST Damien & VEGREVILLE Thibaud
 */

#include <stdio.h>
#include <stdlib.h>
#include "readcmd.h"
#include <unistd.h> // AJOUT
#include <sys/types.h> // AJOUT
#include <sys/wait.h> // AJOUT
#include <fcntl.h> // AJOUT

int main()
{
	int pid, status;
	int num_fichier;
	char* path;
	while (1) {
		struct cmdline *l;
		int i;
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
		}
		
		/* CREATION DE THREAD */
		pid = fork();
		if(pid == -1)
		{
			perror("[E] erreur création du fork()");
			exit(-1);
		}
		else if(pid == 0) // code du fils du shell	
		{
			int size=0;
			for(i=0; l->seq[i]!=0; i++){
				size++;
			}
			printf("command size: %d\n", size);
			int pipes [size-1][2];
			for(i=1; l->seq[i]!=0; i++){
				int temp[2];
				if (pipe(temp))
		    	{
					fprintf (stderr, "Pipe failed.\n");
					return EXIT_FAILURE;
		    	}
		    	pipes[i-1][0]=temp[0];
		    	pipes[i-1][1]=temp[1];
			}


			for (i=0; l->seq[i]!=0; i++)
			{	
				
				printf("okok\n");
				if(i==0 && size > 1){
					dup2(pipes[i+1][0],STDOUT_FILENO);
				}
				if(l->seq[i+1]==0 && size > 1){
					dup2(pipes[i][1],STDIN_FILENO);
				}
				if(i!=0 && l->seq[i+1]!=0 && size > 1){
					dup2(pipes[i][1],STDIN_FILENO);
					dup2(pipes[i+1][0],STDOUT_FILENO);
				}

				if (l->in && i==0 )
				{
					num_fichier = open(l->in,O_RDONLY);
					if (num_fichier<0)
					{
						printf("[E] Fichier introuvable\n");
						exit(0);
					}
					dup2(num_fichier,STDIN_FILENO);
					close(num_fichier);			
				}
				if (l->out && 	l->seq[i+1]==0)
				{
					num_fichier = open(l->out, O_RDWR|O_CREAT, 0666);
					dup2(num_fichier,STDOUT_FILENO);
					close(num_fichier);	
				}
				if(l->err && l->seq[i+1]==0){
					num_fichier = open(l->err, O_RDWR|O_CREAT, 0666);
					dup2(num_fichier,STDOUT_FILENO);
					close(num_fichier);	
				}
				execvp(l->seq[i][0],l->seq[i]);
			}
		}
		else// le pere attend la terminaison du fils
		{
			wait(&status);
		}
	}
}
