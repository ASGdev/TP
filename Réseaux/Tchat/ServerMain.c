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
    char msg[255];
    int recep_message;
    char* message;
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
                    char *pseudo_rcv;
                    strcpy(msg, "");
                    recep_message = read(newfd, msg, sizeof(msg));
                    pseudo_rcv = (char *)malloc(recep_message * sizeof(char));
                    for (int j = 0; j < recep_message; j++)
                        pseudo_rcv[j] = msg[j];
                    printf("%s vient de se connecter\n", pseudo_rcv);
                    //Et on ajoute les infos de la connections
                    tempInt = 0;
                    while(tabConnectes[tempInt].numSocket != -1)
                    {
                        tempInt++;
                    }
                    tabConnectes[tempInt].numSocket = i;
                    tabConnectes[tempInt].pseudo = pseudo_rcv;
                    }
                    
                }
                else //Donnée arrivant d'un client
                {
                    strcpy(msg,"");
                    recep_message = read(i, msg, sizeof(msg));
                    message = (char*)malloc(recep_message*sizeof(char));
                    for (int i=0; i<recep_message; i++)
                        message[i] = msg[i];
                    
                    if(strcmp(message,"liste")==0){
                        char *temp_list = (char *)malloc(50 * sizeof(char));
                        int parcours=0;
                        while(tabConnectes[parcours].numSocket != -1)
                        {
                            strcat(temp_list, tabConnectes[parcours].pseudo);
                            strcat(temp_list," ");
                            parcours++;
                        }
                        
                        write(i, temp_list, strlen(temp_list));
                    }
                    if (strcmp(message,"quit")==0)
                        {
                            /* connection closed */
                            printf("Fin de connection du client %s \n", tabConnectes[tempInt].pseudo);
                            tabConnectes[tempInt].pseudo = "";
                            /* On ferme la cnnection */
                            close(i);
                            tabConnectes[tempInt].numSocket = -1;
                            /* Et on l'enleve du master set */
                            FD_CLR(i, &masterset);

                           
                        }
                    if(strcmp(message,"quit")!=0 && strcmp(message,"liste")!=0)
                         {
                            int p=0;
                            int temp_bis=0;
                            char message_send[500];
                            char pseudo_bis[50];
                            
                            char str[80] = "robert coucou";
                            const char s[2] = " ";
                            char *token;
                        //     /* get the first token */
                            token = strtok(str, s);
                            strcpy(pseudo_bis,token);
                             /* walk through other tokens */
                            while( token != NULL ) {
                                token = strtok(NULL, s);
                                strcat(message_send,token);
                            }
                        
                        //     // token = strtok(message," ");
                        //     // printf("pseudo envoyé : %s",token);
                        //     // strcpy(pseudo_bis,token);
                        //     // while(token != NULL){
                        //     //     token=strtok(NULL," ");
                        //     //     strcat(message_send,token);
                        //     // }
                        //     while(strcmp(tabConnectes[temp_bis].pseudo,pseudo_bis)!=0){
                        //         temp_bis++;
                        //     }
                        //     write(tabConnectes[temp_bis].numSocket, message_send, strlen(message_send));
                        }
                }
            }
        }
    }
}

