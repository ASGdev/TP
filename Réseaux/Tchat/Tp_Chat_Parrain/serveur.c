/*************************** RICOU-VALENTIN-TIAMIOU
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <netinet/in.h>
#include <netdb.h> 


/******************************************************************************/
	
/*------------------------- programme serveur -------------------------------*/
	

void serveur_appli(unsigned short portEcoute){

	/***************** Déclarations des variables ******************/	
	struct	sockaddr_in  adr_serv, adr_client;
	int num_socket,num_socket_client ;
	int nombreClients;
	socklen_t taille = sizeof(struct sockaddr_in);
	char *recupPseudo;
	char rcpPseudo[50];
	char *message;
	char msg[200];
	char * temp ;
	char *commande;
	char cmd[50];
	int retourRead, retourRead1,retourBind, retourListen,retourSelect,i;
	struct Connecte {
		int numSocket;
		char pseudo[50];	
	};
	struct Connecte tabConnectes[500];
	int indiceTabConnectes=0, nbConnectes=0;					;
	fd_set	sock_ecoutes, copie_sock_ecoutes;
	int n, espace=0, dest=-1, source=-1,lgmes=0;
	char *destinataire;
					 



	// Création de la socket client
  	num_socket = socket (AF_INET, SOCK_STREAM, IPPROTO_TCP);
 	if (num_socket!=-1)	printf("Socket creee correctement\n");

	//Renseignement de la structure sockaddr_in adr_serv
  	adr_serv.sin_family = AF_INET;
  	adr_serv.sin_port = htons(portEcoute);
  	adr_serv.sin_addr.s_addr = INADDR_ANY;

	//Bind
	retourBind = bind (num_socket, (struct sockaddr *) &adr_serv, taille);
  	if (retourBind==0)	printf("Bind bien passe\n"); //else exit(-1);

	//Mise en écoute de la socket
  	retourListen = listen(num_socket, SOMAXCONN);
  	if (retourListen==0)	printf("Listen bien passe : j'ecoute...\n");

	//Initialisation de la liste des sockets
	nombreClients = getdtablesize();
	FD_ZERO(&sock_ecoutes);
	FD_SET(num_socket, &sock_ecoutes);
	
	//Initialisation des sockets terminee
	printf("Initialisation terminee\n\nBienvenue sur le Serveur\n\n");

	while (1) {

		//Copie de la liste des sockets
		bcopy ((char*) &sock_ecoutes, (char*) &copie_sock_ecoutes, sizeof(sock_ecoutes)) ;

		//Ecoute de plusieurs descripteurs 
		select(nombreClients, &copie_sock_ecoutes,NULL,NULL,NULL);
		//if (retourSelect) printf("Il a des données disponibles\n");		

		if (FD_ISSET(num_socket, &copie_sock_ecoutes)) {

			num_socket_client = accept(num_socket,(struct sockaddr *) &adr_client, &taille);
			/*if (num_socket_client >= 0)	
				printf("Accept bien passe : %d\n", num_socket_client);*/
			printf("J'ai recu une demande du client : %s \n", inet_ntoa(adr_client.sin_addr)); 

			
			//Ajout de la socket dans la liste des sockets clients
			FD_SET(num_socket_client, &sock_ecoutes);
			
			//Réception du pseudo du client
			strcpy(rcpPseudo,"");
			retourRead = read(num_socket_client, rcpPseudo, sizeof(rcpPseudo));
			recupPseudo = (char*)malloc(retourRead*sizeof(char));
			int j;
			for (j=0; j<retourRead; j++)
				recupPseudo[j] = rcpPseudo[j];
			printf("Connexion du client : %s \n\n", recupPseudo);

			
			//On ajoute le client dans la liste
			strcpy(tabConnectes[indiceTabConnectes].pseudo,recupPseudo);
			tabConnectes[indiceTabConnectes].numSocket=num_socket_client;
			indiceTabConnectes++;

			//Envoi du premier message
			temp=(char *) malloc (23*sizeof (char));
			strcpy(temp, "Bienvenue sur le tchat!");
  			write(num_socket_client, temp, strlen(temp));

			//Envoi de la liste des connectés
			/*int nbConnectes=0;					
			strcpy(msg,"");
			for (j=0; j<indiceTabConnectes; j++) {
				strcat(msg,tabConnectes[j].pseudo);
				strcat(msg,",");
				nbConnectes = nbConnectes + strlen(tabConnectes[j].pseudo) + 1;
			}
			message = (char*)malloc(nbConnectes*sizeof(char));
			for (j=0; j<(nbConnectes-1); j++)
				message[j] = msg[j];						
			write(num_socket_client, message, strlen(message));*/
	  	}
  		
  		for (i=0; i<nombreClients; i++) 
  		{
  			if (FD_ISSET(i, &copie_sock_ecoutes) && i!=num_socket)
  			{
				//Réception d'une commande
				strcpy(msg,"");
				retourRead1 = read(i,msg, sizeof(msg));
				commande = (char*)malloc(retourRead1*sizeof(char));
				int j;
				for (j=0; j<retourRead1; j++)
					commande[j] =msg[j];
				printf("Tchat : %s\n" , commande);
				
				//Demande de la liste des connectes				
				if (strcmp(commande, "Liste")==0) {
					strcpy(msg,"");
					for (j=0; j<indiceTabConnectes; j++) {
						strcat(msg,tabConnectes[j].pseudo);
						strcat(msg,",");
						nbConnectes = nbConnectes + strlen(tabConnectes[j].pseudo) + 1;
					}
					message = (char*)malloc(nbConnectes*sizeof(char));
					for (j=0; j<(nbConnectes-1); j++)
						message[j] = msg[j];						
					write(i, message, strlen(message));
				}

				//Deconnexion d'un client
				else if (strcmp(commande, "quit")==0) {
					//printf("Deconnexion d'un client\n\n");
					j=0;
					while (j<indiceTabConnectes && tabConnectes[j].numSocket!=i) {
						j++;
					}
					printf("%s est deconnecte\n", tabConnectes[j].pseudo);					
					int n;
					for (n=j; n<indiceTabConnectes-1; n++)
					{
						strcpy(tabConnectes[n].pseudo, tabConnectes[n+1].pseudo);
						tabConnectes[n].numSocket = tabConnectes[n+1].numSocket;
					}
					indiceTabConnectes--;
					close(i);
					FD_CLR(i, &sock_ecoutes);
				}

				//Envoi de message
				else {
					//On lit le pseudo du destinataire(tt envoi doit respecter : pseudo' '<message>)
					j=0;
					while (j<strlen(commande) && espace==0) {
						if (commande[j]==' ')
							espace=j;
						j++;
					} 
					
					if (espace == 0) {
						//la syntaxe n est pas reconnue
						printf("Commade invalide\n\n");
						temp=(char *) malloc (46*sizeof (char));
						strcpy(temp, "La commande que vous avez entree n est pas valide");
  						write(i, temp, strlen(temp));						

					}
					else {
						destinataire = (char*)malloc(500*sizeof(char));
					
						//destinataire du message
						for (j=0; j<espace; j++) {
							destinataire[j] = commande[j];
						}
						
						//vérification destinatire  est connecté
						int existe=0;
						for (j=0; j<indiceTabConnectes; j++) {
							if (strcmp(destinataire,tabConnectes[j].pseudo)==0) {
								dest = j;
								existe=1;
							}
						}
						
						//client est connecté
						if (dest > -1 && existe) {
							
							//recupere la source
							for (j=0; j<indiceTabConnectes; j++) {
								if (tabConnectes[j].numSocket == i)
									 source = j;
							}							
							
							strcpy(msg,"");
							strcpy(msg,tabConnectes[source].pseudo);
							strcat(msg, " : ");
							lgmes = strlen(tabConnectes[source].pseudo) + 3;
											
							//on met le message dans msg
							n=0;
							for (j=espace+1; j<strlen(commande); j++) {
								msg[strlen(tabConnectes[source].pseudo)+3+n] = commande[j];
								lgmes++;
								n++;
							}
							
							message = (char*)malloc(lgmes*sizeof(char));
							for (j=0; j<lgmes; j++)
								message[j] =msg [j];
							
							//on envoie le message au destinataire
							write(tabConnectes[dest].numSocket, message, strlen(message));
						}
						else {
							//le pseudo n'est pas valide
							printf("Commade invalide\n\n");
							temp=(char *) malloc (46*sizeof (char));
							strcpy(temp, "La commande que vous avez entree n est pas valide");
  							write(i, temp, strlen(temp));
						}
					}
				}
	  		}
  		}
	}
}

/******************************* Fin serveur **********************************/


/******************************************************************************/
int main(int argc,char *argv[])
{
	unsigned short port; /* numero de port d'ecoute */

	/* Permet de passer un nombre de parametre variable a l'executable */
	switch (argc){
		case 2:
			  port=(u_short)atoi(argv[1]);
			  printf("numero de port d'ecoute = %u\n", port);
			  break;
		default :
			  printf("Usage : serveur <numero de port>\n");
			  return -1;
 	}

	serveur_appli(port);
}

/******************************************************************************/
