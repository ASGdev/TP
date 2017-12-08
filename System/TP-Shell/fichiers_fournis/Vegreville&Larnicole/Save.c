
#include <stdio.h>
#include <stdlib.h>
#include "readcmd.h"
#include <unistd.h>	// AJOUT
#include <sys/types.h> // AJOUT
#include <sys/wait.h>  // AJOUT
#include <fcntl.h>	 // AJOUT

int main()
{
	int status;
	int num_fichier;
	char *path;
	while (1)
	{
		struct cmdline *l;
		int i;
		printf("shell> ");
		l = readcmd();
		/* If input stream closed, normal termination */
		if (!l)
		{
			printf("exit\n");
			exit(0);
		}
		if (l->err)
		{
			/* Syntax error, read another command */
			printf("error: %s\n", l->err);
			continue;
		}

		int size = 0;
		for (i = 0; l->seq[i] != 0; i++)
		{
			size++;
		}
		printf("command size: %d\n", size);
		int pipes[size - 1][2];
		for (i = 0; i < size - 1; i++)
		{
			if (pipe(pipes[i]))
			{
				fprintf(stderr, "Pipe failed.\n");
				return EXIT_FAILURE;
			}
		}

		/* CREATION DE THREADs */

		int pid[size];
		for (i = 0; i < size; i++)
		{

			pid[i] = fork();
			if (pid[i] == -1)
			{
				perror("[E] erreur création du fork()\n");
				exit(-1);
			}
			else if (pid[i] == 0) // code d'un fils du shell
			{

				printf("1 processus de plus\n");
				if (i == 0 && size > 1)
				{	
					close(pipes[i + 1][0]);
					if (dup2(pipes[i + 1][1], STDOUT_FILENO) < 0)
					{
						printf("error dup2 start\n");
					}
					close(pipes[i + 1][1]);
				}
				if (l->seq[i + 1] == 0 && size > 1)
				{
					close(pipes[i][1]);
					if (dup2(pipes[i][0], STDIN_FILENO) < 0)
					{
						printf("error dup2 middle\n");
					}
					close(pipes[i][0]);
				}
				if (i != 0 && l->seq[i + 1] != 0 && size > 1)
				{
					close(pipes[i + 1][0]);
					close(pipes[i][1]);
					if (dup2(pipes[i][0], STDIN_FILENO) < 0 ||
						dup2(pipes[i + 1][1], STDOUT_FILENO) < 0)
					{
						printf("error dup2 last\n");
					}
					close(pipes[i + 1][1]);
					close(pipes[i][0]);
				}

				if (l->in && i == 0)
				{
					num_fichier = open(l->in, O_RDONLY);
					if (num_fichier < 0)
					{
						printf("[E] Fichier introuvable\n");
						exit(0);
					}
					dup2(num_fichier, STDIN_FILENO);
					close(num_fichier);
				}
				if (l->out && l->seq[i + 1] == 0)
				{
					num_fichier = open(l->out, O_RDWR | O_CREAT, 0666);
					dup2(num_fichier, STDOUT_FILENO);
					close(num_fichier);
				}
				if (l->err && l->seq[i + 1] == 0)
				{
					num_fichier = open(l->err, O_RDWR | O_CREAT, 0666);
					dup2(num_fichier, STDOUT_FILENO);
					close(num_fichier);
				}

				execvp(l->seq[i][0], l->seq[i]);
			}
			else // le pere attend la terminaison du fils
			{
				//nothing, on crée tout les proc
			}
		}
		//Si on sort de la boucle for, c'et que on est obligatoirement le père
		wait(&status);
	}
}
