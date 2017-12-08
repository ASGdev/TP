/*
 * Copyright (C) LARNICOLE Titouan & VEGREVILLE Thibaud
 */

#include <stdio.h>
#include <stdlib.h>
#include "readcmd.h"
#include <unistd.h>	// AJOUT
#include <sys/types.h> // AJOUT
#include <sys/wait.h>  // AJOUT
#include <fcntl.h>	 // AJOUT

int creation_proc(int in, int out, int i, struct cmdline *l) //Ce code ci n'est pas entèrement de notre invention, nous nous
															 //Aider d'un exemple, et l'avons adapté (notament la struture utilisé)
{
	pid_t pid;

	if ((pid = fork()) == 0)
	{	
		printf("1 processus de plus pour la commande %d\n",i);
		if (in != 0)
		{
			dup2(in, 0);
			close(in);
		}

		if (out != 1)
		{
			dup2(out, 1);
			close(out);
		}

		return execvp(l->seq[i][0], l->seq[i]); //i est le numméro de la commande
	}
	
	return pid;
}

int main()
{
	int status;
	int num_fichier;

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

		int in, out, fd[2];
		in = 0;  // on initialise la première entrée sur l'entre standard
		out = 1; //same there

		int size = 0;
		for (i = 0; l->seq[i] != 0; i++)
		{
			size++;
		}
		printf("command size: %d\n", size);

		/* CREATION DE THREADs et piping*/
		int pid[size];
		for (i = 0; l->seq[i+1]; i++) // ici, i correspond aux nb de pipes a faire, donc size -1 (dernière commande executer par le main thread)
		{

			pipe(fd);
			if (l->in && i == 0)
			{
				num_fichier = open(l->in, O_RDONLY);
				if (num_fichier < 0)
				{
					printf("[E] Fichier introuvable\n");
					exit(0);
				}
				creation_proc(num_fichier, fd[1], i, l);
			}else{
				creation_proc(in, fd[1], i, l);
			}
			close(fd[1]);
			in = fd[0];

			execvp(l->seq[i][0], l->seq[i]);
		}
		printf("Dernier processus lancé\n"); // a cause des n-1 pipe, je lace la nieme commande a la main
		 if (in != 0)
		    dup2 (in, 0);

		if (l->out)
		{
			num_fichier = open(l->out, O_RDWR | O_CREAT, 0666);
		}
		else if (l->err)
		{
			num_fichier = open(l->err, O_RDWR | O_CREAT, 0666);
			creation_proc(in,num_fichier, size-1, l);
		}
		else
		{
			creation_proc(in,out,size-1, l);
		}
		wait(&status);
	}
}
