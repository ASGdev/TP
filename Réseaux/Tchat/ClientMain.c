#ifdef WIN32 /* si vous êtes sous Windows */

#include <winsock2.h> 

#elif defined (linux) /* si vous êtes sous Linux */

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h> /* close */
#include <netdb.h> /* gethostbyname */
#define INVALID_SOCKET -1
#define SOCKET_ERROR -1
#define closesocket(s) close(s)
typedef int SOCKET;
typedef struct sockaddr_in SOCKADDR_IN;
typedef struct sockaddr SOCKADDR;
typedef struct in_addr IN_ADDR;

#else /* sinon vous êtes sur une plateforme non supportée */

#endif
static void init(void)
{
#ifdef WIN32
    WSADATA wsa;
    int err = WSAStartup(MAKEWORD(2, 2), &wsa);
    if(err < 0)
    {
        puts("WSAStartup failed !");
        exit(EXIT_FAILURE);
    }
#endif
}
static void end(void)
{
#ifdef WIN32
    WSACleanup();
#endif
}

//Implémentation Client

void client(unsigned long add_IP,unsigned long port, char* pseudo){
    struct sockaddr_in  adr_serv, adr_client;     
	int  num_socket;	
	char *message; 		
	char msg[255];
    int CheckConnection;
    int recep_message;
    char commande[100];
    int nbclient;
    FD_SET sock_listening, socket_list;
    


    num_socket = socket (AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (num_socket!=-1)	printf("La socket a été créée\n");
    //On complète la structure
    adr_serv.sin_family = AF_INET;
    adr_serv.sin_addr.s_addr = add_IP;
    adr_serv.sin_port = htons(port);

    //Se connecte au serveur (le serveur bind la socket client)
    printf("Connexion Server...\n");
    CheckConnection = connect(num_socket,(struct sockaddr *) &adr_serv, sizeof(struct sockaddr_in));
    if (CheckConnection==0)	printf("Connexion OK\n");

    //Ecoute le message d'accueil
    //strcpy(msg,"");
	// recep_message = read( num_socket, msg, sizeof(msg));
	// message = (char*)malloc(recep_message*sizeof(char));
	// for (int i=0; i<recep_message; i++)
	// 	message[i] = msg[i];
    // printf("%s\n", message);
    
    //Envoie du pseudo 
    write(num_socket,pseudo,strlen(pseudo));

    //Liste
    nbclient = getdtablesize();                         
    printf("size tab : %d",nbclient);
    FD_ZERO(&sock_listening);
	FD_SET(0, &sock_listening);
	FD_SET( num_socket, &sock_listening);

    //Tant que l'utilisateur ne tape pas "quit", il reste connecté
    while(strcmp(commande,"quit")!=0){
        //Copie de la liste des sockets
        bcopy ( (char*) &sock_listening, (char*) &socket_list, sizeof(sock_listening)); 
        //Permet d'ecouter plusieurs descripteurs a la fois
        select(nombreClients, &socket_list, 0, 0, 0);
        //Nouvelle commande
        strcpy(commande,"");
        gets(commande);
        write(num_socket,commande, strlen(commande));
        close(num_socket);
    }
   }

int main(int argc, char* argv[]){
    init();
    unsigned long add_IP;
    unsigned short port;
    char* pseudo;

    //On lit les paramètres entrées en commande
    switch (argc){
		case 4 :
			  add_IP=inet_addr(argv[1]);
			  port=(u_short)atoi(argv[2]);
			  pseudo=argv[3];
			  break;
		default :
			  printf("./ClientMain add_IP_serveur port pseudo\n");
              return -1;
    }
    client(add_IP, port, pseudo);
}

	


