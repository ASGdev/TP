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
#include <string.h>

int PORT = 9999;
int SIZE = 1024;

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

int append(char *s, size_t size, char c)
{
    if (strlen(s) + 1 >= size)
    {
        return 1;
    }
    int len = strlen(s);
    s[len] = c;
    s[len + 1] = '\0';
    return 0;
}

int main(int argc, char *argv[])
{

    /***************** Déclarations des variables ******************/
    struct sockaddr_in adr_serv, adr_client;
    socklen_t taille = sizeof(struct sockaddr_in);
    /*Le listener serveur*/
    int listener_socket, result;
    /* le descriteur de la dernière co accepté */
    int newfd;
    /* nombre max de file descriptor */
    int fdmax;
    char msg[SIZE];
    int recep_message;
    char *message;
    int nbytes; // quantité d'octet lu pour le buffer
    struct Connecte
    {
        int numSocket;
        char *pseudo;
    };
    int tempInt; //utilisé pour les insertions et autre opération
    char *temp;
    struct Connecte tabConnectes[500];
    fd_set masterset, tempset;

    //On vide bien la table de connection :
    for (int i = 0; i < 500; i++)
    {
        tabConnectes[i].numSocket = -1;
        tabConnectes[i].pseudo = "";
    }

    // Création de la socket client
    listener_socket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (listener_socket != -1)
        printf("Socket creee correctement\n");

    //Renseignement de la structure sockaddr_in adr_serv
    adr_serv.sin_family = AF_INET;
    adr_serv.sin_port = htons(PORT);
    adr_serv.sin_addr.s_addr = INADDR_ANY;

    //Bind
    if (bind(listener_socket, (struct sockaddr *)&adr_serv, taille) == 0)
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

        //On itère sur les données a lire, i donne le fd (i == fd)
        for (int i = 0; i <= fdmax; i++)
        {

            if (FD_ISSET(i, &tempset))
            { //On a une donnée a lire sur l'indice i
                if (i == listener_socket)
                {
                    if ((newfd = accept(listener_socket, (struct sockaddr *)&adr_client, &taille)) == -1)
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
                        //Message d'accueil
                        char *temp = (char *)malloc(50 * sizeof(char));
                        strcpy(temp, "Bienvenue sur le chat");
                        write(newfd, temp, strlen(temp));
                        //Réception du pseudo
                        strcpy(msg, "");
                        recep_message = read(newfd, msg, sizeof(msg));
                        message = (char *)malloc(recep_message * sizeof(char));
                        for (int j = 0; j < recep_message; j++)
                            message[j] = msg[j];
                        printf("%s vient de se connecter\n", message);

                        //Et on ajoute les infos de la connections
                        tempInt = 0;
                        for (int j = 0; tabConnectes[j].numSocket != -1; j++)
                        {
                            tempInt++;
                        }
                        tabConnectes[tempInt].numSocket = i;
                        tabConnectes[tempInt].pseudo = message;
                    }
                }
                else //Donnée arrivant d'un client
                {
                    char liste[5]="liste";
                    strcpy(msg, "");
                    recep_message = read(newfd, msg, sizeof(msg));
                    message = (char *)malloc(recep_message * sizeof(char));
                    for (int j = 0; j < recep_message; j++)
                        message[j] = msg[j];
                    if(strcmp(msg,liste)){
                        printf("on va afficher la liste");
                    }
                    /*Idée de l'algo :
                        - Si nom seul : un client demande a communiquer spécifiqueent avec un autre
                        - sinon, c'est un message
                        donc on fait ca sous la forme suivante "Nom: Message"
                        On parse ce qu'il y a avant les deux point (donc pseudo sans :), et on le match
                        avec notre lsite de connecter, et ensuite on l'expédie sous la meme forme.
                        On pensera a remplacer le nom par celui de l'expéditeur cependant.
                        Un client peut set son pseudo en envoyant sous la forme "!Peudo", ler serveur
                        le détecte et l'inscrit.
                        Le client peut taper des commndes server en tapant "?Command"
                    */

                    /* On gère maintenant les données clients */
                    /* Erreur ou connecion close par le client */
                    if ((nbytes = recv(i, msg, sizeof(msg), 0)) <= 0)

                    {

                        if (nbytes == 0)
                            /* connection closed */
                            printf("Fin de connection du client %s", tabConnectes[i].pseudo);
                        else
                            perror("recv() erreur!");
                        /* On ferme la cnnection */
                        close(i);
                        /* Et on l'enleve du master set */
                        FD_CLR(i, &masterset);

                        //Et on l'enleve de la table de connection
                        /*
                        tempInt = 0;
                        for (int j = 0; tabConnectes[j].numSocket != i; j++)
                        {
                            tempInt++;
                        }
                        tabConnectes[tempInt].numSocket = -1;
                        tabConnectes[tempInt].pseudo = "";*/
                    }
                    else //On a un message en attente : on le parse et le redirige
                    {
                        strcpy(msg, "");
                        recep_message = read(newfd, msg, sizeof(msg));
                        message = (char *)malloc(recep_message * sizeof(char));

                        if (msg[0] == '?')
                        {
                        }
                        else
                        {   //On forard le message vers le client désigné
                            strcpy(message, "");
                            printf("Ca s passe\n");
                            tempInt = 0;
                            for (int j = 0; message[j] != ' '; j++)
                                message[j] = msg[j];
                            for (int j = 0; strcmp(tabConnectes[j].pseudo, message)!=0; i++)
                            {
                                tempInt++;
                            }
                            printf("Ca s passe\n");
                            strcpy(message, "");
                            for (int j = 0;j < recep_message; j++)
                                message[j] = msg[j];
                            char *temp = (char *)malloc(1024 * sizeof(char));
                            write(tabConnectes[tempInt].numSocket,temp,strlen(temp));
                        }
                    }
                }
            }
        }
    }
}