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

/*--------------------------- programme client ------------------------------*/

void client_appli(unsigned long addIP, unsigned short port, char * pseudo) {

	/***************** Déclarations des variables ******************/
	struct sockaddr_in  adr_serv, adr_client;     
	int  num_socket;	
	char *message; 		
	char msg[255];
	char *message1; 
	char msg1[255]; 
	char commande[255];
	int retourRead, retourBind,retourConnect,retourRead1;
	fd_set sock_ecoutes, copie_sock_ecoutes;
	int nombreClients,i;
	/******************** Fin des déclarations  *********************/

	/********************** Début programme ************************/

	//Creation de la socket
	num_socket = socket (AF_INET, SOCK_STREAM, IPPROTO_TCP);
 	if (num_socket!=-1)	printf("Socket creee correctement\n");

	/************************************************************/
 	 /*Renseignement de la structure sockaddr_in adr_client
 	 adr_client.sin_family = AF_INET;
 	 adr_client.sin_addr.s_addr = (u_short)atoi("127.0.0.1");
 	 adr_client.sin_port = htons(6000);
  	retourBind = bind (num_socket, (struct sockaddr *) &adr_client, sizeof(struct sockaddr_in));
  	if (retourBind==0)	printf("Bind bien passe\n");
  	else exit(0);
	/************************************************************/


	//Renseignement de la structure sockaddr_in adr_serv
  	adr_serv.sin_family = AF_INET;
  	adr_serv.sin_addr.s_addr = addIP;
  	adr_serv.sin_port = htons(port);

	// Connexion au serveur
  	printf("Tente une connexion au serveur...\n");
  	retourConnect = connect(num_socket,(struct sockaddr *) &adr_serv, sizeof(struct sockaddr_in));
 	 if (retourConnect==0)	printf("Connexion reussie\n");
	
	//Envoi du pseudo
	write(num_socket, pseudo, strlen(pseudo));
	
	//Reception du message d'accueil
	strcpy(msg,"");
	retourRead = read( num_socket, msg, sizeof(msg));
	message = (char*)malloc(retourRead*sizeof(char));
	for (i=0; i<retourRead; i++)
		message[i] = msg[i];
	printf("%s\n", message);

	//Réception de la liste des connectés
	/*strcpy(msg,"");
	retourRead = read( num_socket, msg, sizeof(msg));	
	message = (char*)malloc(retourRead*sizeof(char));
	for (i=0; i<retourRead; i++)
		message[i] = msg[i];
	printf("%s", message);
	printf("  connectes \n");*/	

	//Initialisation de la liste
	nombreClients = getdtablesize();
	FD_ZERO(&sock_ecoutes);
	FD_SET(0, &sock_ecoutes);
	FD_SET( num_socket, &sock_ecoutes);

	printf("\n *Liste : permet de voir la liste des connectes \n *quit : permet de quitter le chat \n *Pour envoyer un message : pseudo <<message>> \n\n");

	//Boucle d'envoi et de réception des messages
	while (strcmp(commande, "quit")!=0) {
 		
		//Copie de la liste des sockets
		bcopy ( (char*) &sock_ecoutes, (char*) &copie_sock_ecoutes, sizeof(sock_ecoutes)) ;

		//Permet d'ecouter plusieurs descripteurs a la fois
		select(nombreClients, &copie_sock_ecoutes, 0, 0, 0);
		
		if (FD_ISSET(0, &copie_sock_ecoutes)) {
			//Envoi de commande
			strcpy(commande,"");
	  		gets(commande); 
	  		write( num_socket, commande, strlen(commande));
	  		
			//liste des connectés
	  		if (strcmp(commande, "Liste")==0) {
	  			strcpy(msg,"");
	  			retourRead = read( num_socket, msg, sizeof(msg));	
	  			message = (char*)malloc(retourRead*sizeof(char));
				for (i=0; i<retourRead; i++)
					message[i] = msg[i];
	  			printf("%s", message);
				printf("  connectes \n");
	  		}
			//déconnexion
	  		else if (strcmp(commande, "quit")==0){
	  			close( num_socket);
	  			FD_CLR(0, &sock_ecoutes);
	  			FD_CLR( num_socket, &sock_ecoutes);
	  		}
		}
		
		if (FD_ISSET( num_socket, &copie_sock_ecoutes)) {
			//Reception des messages
			strcpy(msg1,"");
			retourRead1 = read( num_socket, msg1, sizeof(msg1));
			message1 = (char*)malloc(retourRead1*sizeof(char));
			for (i=0; i<retourRead1; i++)
				message1[i] = msg1[i];
  			printf("%s\n\n", message1);
		}
	}

}
/******************************* Fin client ***********************************/


/******************************************************************************/
int main(int argc,char *argv[])
{
	unsigned long addIP; /* adresse IP du serveur */
	unsigned short portEcoute; /* numero de port */
	char * pseudo; /* pseudonyme du client sur le chat */


	/* Permet de passer un nombre de parametre variable a l'executable */
	switch (argc){
		case 4 :
			  addIP=inet_addr(argv[1]);
			  portEcoute=(u_short)atoi(argv[2]);
			  pseudo=argv[3];
			  break;
		default :
			  printf("Usage: client <adresse IP serveur> <num port> <pseudo>\n");
			  return -1;
 	}

	client_appli(addIP, portEcoute, pseudo);
}

/******************************************************************************/
