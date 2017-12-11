#ifdef WIN32 /* si vous êtes sous Windows */

#include <winsock2.h> 

#elif defined (linux) /* si vous êtes sous Linux */

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <errno.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
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

static void search(char *chaine)
{
    char *p = strchr(chaine, '\n');
    if (p)
    {
        *p = 0;
    }
}



void client(unsigned long add_IP,unsigned long port, char* pseudo){
    struct sockaddr_in  adr_serv, adr_client;     
	int  num_socket;	
	char *message; 		
    char msg[255];
    char* temp;
    int CheckConnection;
    int recep_message;
    int nbclient;
    fd_set sock_listening, socket_list;
    


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
    strcpy(msg,"");
	recep_message = read( num_socket, msg, sizeof(msg));
	message = (char*)malloc(recep_message*sizeof(char));
	for (int i=0; i<recep_message; i++)
		message[i] = msg[i];
    printf("%s\n", message);

    //Envoie du pseudo , on rajoute "!" pour parser facilement
    temp=(char *) malloc (50*sizeof (char));
    strcpy(temp, pseudo);
    write(num_socket, temp, strlen(temp));  
    char liste[5]="liste";
   
    char* commande= (char*) malloc(50*sizeof(char));
    //Tant que l'utilisateur ne tape pas "quit", il reste connecté
    while(strcmp(commande,"quit")!=0){
        //Nouvelle commande
        printf("Rentrez une commande : ");   
        fgets(commande,sizeof(commande),stdin);
        search(commande);
        write(num_socket,commande, strlen(commande));

        //Ecoute le retour
        strcpy(msg,"");
        recep_message = read( num_socket, msg, sizeof(msg));
        message = (char*)malloc(recep_message*sizeof(char));
        for (int i=0; i<recep_message; i++)
            message[i] = msg[i];
        printf("%s\n", message);
        
    }
    close(num_socket);
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

	


