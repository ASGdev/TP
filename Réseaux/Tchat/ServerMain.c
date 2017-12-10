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
    int num_socket, num_socket_client;
    int nombreClients;
    socklen_t taille = sizeof(struct sockaddr_in);
    char *recupPseudo;
    char rcpPseudo[50];
    char *message;
    char msg[200];
    char *temp;
    char *commande;
    char cmd[50];
    struct Connecte
    {
        int numSocket;
        char pseudo[50];
    };
    struct Connecte tabConnectes[500];
    int indiceTabConnectes = 0, nbConnectes = 0;
    ;
    fd_set sock_ecoutes, copie_sock_ecoutes;
    int n, espace = 0, dest = -1, source = -1, lgmes = 0;
    char *destinataire;


    // Création de la socket client
    num_socket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (num_socket != -1)
        printf("Socket creee correctement\n");

    //Renseignement de la structure sockaddr_in adr_serv
    adr_serv.sin_family = AF_INET;
    adr_serv.sin_port = htons(PORT);
    adr_serv.sin_addr.s_addr = INADDR_ANY;

    //Bind
    if (bind(num_socket, (struct sockaddr *)&adr_serv, taille) == 0)
        printf("Bind Done\n"); //else exit(-1);

    //Mise en écoute de la socket
    if (listen(num_socket, SOMAXCONN) == 0)
        printf("Listen ON\n");

    //Initialisation de la liste des sockets
    nombreClients = getdtablesize();
    FD_ZERO(&sock_ecoutes);
    FD_SET(num_socket, &sock_ecoutes);

    //Initialisation des sockets terminee
    printf("Initialisation terminee\n\nBienvenue sur le Serveur\n\n");
}