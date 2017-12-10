/*Une partie des entête, inclusion et certaine fonction proviennent du site http://broux.developpez.com/articles/c/sockets/
afin de garantir un dévellopement portable car notre groupe avais des machines linux et windows.*/

#ifdef WIN32 /* si vous êtes sous Windows */

#include <winsock2.h>
//Il faut compiler avec la commande gcc ServerMain.c -o prog -lws2_32

#elif defined(linux) /* si vous êtes sous Linux */
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <string.h>
#include <unistd.h> /* close */
#include <netdb.h>  /* gethostbyname */
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
    if (err < 0)
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

int main(int argc, char *argv[])
{

    /***************** Déclarations des variables ******************/
    struct sockaddr_in adr_serv, adr_client;
    /*Le listener serveur*/
    int listener_socket, result;
    /* le descriteur de la dernière co accepté */
    int newfd;
    /* nombre max de file descriptor */
    int fdmax;
    char msg[1024];
    char cmd[50];
    struct Connecte
    {
        int numSocket;
        char pseudo[50];
    };
    struct Connecte tabConnectes[500];
    fd_set masterset, tempset;

    // Création de la socket client
    listener_socket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (listener_socket != -1)
        printf("Socket creee correctement\n");

    //Renseignement de la structure sockaddr_in adr_serv
    adr_serv.sin_family = AF_INET;
    adr_serv.sin_port = htons(PORT);
    adr_serv.sin_addr.s_addr = INADDR_ANY;

    //Bind
    if (bind(listener_socket, (struct sockaddr *)&adr_serv, sizeof(adr_client)) == 0)
        printf("Bind Done\n"); //else exit(-1);

    //Mise en écoute de la socket
    if (listen(listener_socket, SOMAXCONN) == 0)
        printf("Listen ON\n");

    //Initialisation de la liste des sockets, et on ajoute le listener server
    FD_ZERO(&masterset);
    FD_SET(listener_socket, &masterset);
    FD_ZERO(&tempset);
    fdmax = listener_socket; // on garde la trace du plus grand file descriptor,

    //Initialisation des sockets terminee
    printf("Initialisation terminee\n\nBienvenue sur le Serveur\n\n");

    while (1)
    {
        tempset = masterset;

        if (select(fdmax + 1, &tempset, NULL, NULL, NULL) == -1)

        {
            printf("Server-select() a planté\n");
            exit(1);
        }

        printf("Server-select() ON\n");

        //On itère sur les données a lire
        for (int i = 0; i <= fdmax; i++)
        {
            if (FD_ISSET(i, &tempset))
            { //On a une donnée a lire sur l'indice i
                if (i == listener_socket)
                {
                    if ((newfd = accept(listener_socket, (struct sockaddr *)&adr_client, sizeof(adr_client))) == -1)
                    {
                        printf("Server accept fail\n");
                    }
                    else
                    {
                        FD_SET(newfd, &masterset); /* on ajoute la connection au master set */
                        if (newfd > fdmax)
                        { /* On vérifie le nb max de file descriptor */
                            fdmax = newfd;
                        }
                        printf("New connection accepted\n");
                    }
                }
                else//Donnée arrivant d'un client
                {
                    
                }
            }
        }
    }
}