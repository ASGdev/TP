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
		else if(pid == 0) // code du fils	
		{
			for (i=0; l->seq[i]!=0; i++)
			{
				if (l->in)
				{
					num_fichier = open(l->in,O_RDONLY);
					if (num_fichier<0)
					{
						printf("[E] Fichier introuvable\n");
						exit(0);
					}
					dup2(num_fichier,0);
					close(num_fichier);			
				}
				if (l->out)
				{
					num_fichier = creat(l->out,O_RDWR);
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
