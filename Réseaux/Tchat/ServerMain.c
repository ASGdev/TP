/*Une partie des entête, inclusion et certaine fonction proviennent du site http://broux.developpez.com/articles/c/sockets/
afin de garantir un dévellopement portable car notre groupe avais des machines linux et windows.*/


#ifdef WIN32 /* si vous êtes sous Windows */

#include <winsock2.h> 
//Il faut compiler avec la commande gcc ServerMain.c -o prog -lws2_32

#elif defined (linux) /* si vous êtes sous Linux */

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <string.h>
#include <unistd.h> /* close */
#include <netdb.h> /* gethostbyname */
#define INVALID_SOCKET -1
#define SOCKET_ERROR -1
#define closesocket(s) close(s)
#include <errno.h>
typedef int SOCKET;
typedef struct sockaddr_in SOCKADDR_IN;
typedef struct sockaddr SOCKADDR;
typedef struct in_addr IN_ADDR;
#else /* sinon vous êtes sur une plateforme non supportée */
#endif
#include <stdio.h>
#include <stdlib.h>

int PORT = 9999;


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

int main(int argc, char* argv[]){
   
    init();
    
    SOCKET sock;
    SOCKADDR_IN sin;
    SOCKET client_socket;
    SOCKADDR_IN csin;
    char buffer[240] = "";
    int recsize = (int) sizeof csin;
    int sock_err,msg_size;
    char* commande;
    char* bienvenue;
    char message[100];
    int recep_message;
    char msg[255];

    //Structure connexion d'un client 
    typedef struct new_client new_client;
    struct new_client{
        char pseudo[30];
        int id;
    };
    new_client tab_client[30]; // on limite à 30 client en même temps

    /* Si les sockets fonctionnent */
  
        sock = socket(AF_INET, SOCK_STREAM, 0);

        /* Si la socket est valide */
        if (sock != INVALID_SOCKET)
        {
            printf("La socket %d est maintenant ouverte en mode TCP/IP\n", sock);
 
            /* Configuration */
            sin.sin_addr.s_addr    = htonl(INADDR_ANY);   /* Adresse IP automatique */
            sin.sin_family         = AF_INET;             /* Protocole (IP) */
            sin.sin_port           = htons(PORT);         /* Listage du port */
            sock_err = bind(sock, (SOCKADDR *) &sin, sizeof sin);
            
            /* Si la socket fonctionne */
            if (sock_err != SOCKET_ERROR)
            {
                /* Démarrage du listage (mode server) */
                sock_err = listen(sock, 5);
                printf("Listage du port %d...\n", PORT);
 
                /* Si la socket fonctionne */
                if (sock_err != SOCKET_ERROR)
                {
                    /* Attente pendant laquelle le client se connecte */
                    printf("Patientez pendant que le client se connecte sur le port %d...\n", PORT);
 
                    client_socket = accept(sock, (SOCKADDR *) &csin, &recsize);
                    printf("Un client se connecte avec la socket %d de %s:%d\n", client_socket, inet_ntoa(csin.sin_addr), htons(csin.sin_port));
                    //message de bienvenue
                    bienvenue=(char *) malloc (23*sizeof (char));
                    strcpy(bienvenue, "Bienvenue sur le tchat");
                    write(client_socket, bienvenue, strlen(bienvenue));
                    
                    
                          //Reçoit la comande "quit"
                    strcpy(msg,"");
                    recep_message = read( client_socket, msg, sizeof(msg));
                    commande = (char*)malloc(recep_message*sizeof(char));
                    printf("ok");
                    if(strcmp(commande, "quit")==0){
                        printf("Connexion close");
                        printf("Fermeture de la socket...\n");
                        closesocket(sock);
                        printf("Fermeture du serveur terminee\n");
                    
                    }
                  
 
                    
                }
            }
 
            /* Fermeture de la socket */
            
        }
 

    /* On attend que l'utilisateur tape sur une touche, puis on ferme */
    getchar();

    end();
}
