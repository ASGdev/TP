/*Une partie des entête, inclusion et certaine fonction proviennent du site http://broux.developpez.com/articles/c/sockets/
afin de garantir un dévellopement portable car notre groupe avais des machines linux et windows.*/


#ifdef WIN32 /* si vous êtes sous Windows */

#include <winsock2.h> 
#include <ws2tcpip.h>
//Il faut compiler avec la commande gcc ServerMain.c -o prog -lws2_32

#elif defined (linux) /* si vous êtes sous Linux */

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
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
#include <string.h>

#include <fcntl.h>

int PORT = 9999;
int MAX_CLIENT = 500;


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

    /*gérer le serveur*/
    int sock,i=0,nb_client=0,port=1026,flags=0;
    struct sockaddr_in sin;
 
    /*gérer les clients*/
    int csock[MAX_CLIENT];
    struct sockaddr_in csin[MAX_CLIENT];
 
    /*gérer les données*/
    char buf[32];
    socklen_t size=sizeof(struct sockaddr_in);
 
    if(argc > 1)
    {
        port=strtol(argv[1],NULL,10);
    }
 
    sock=socket(AF_INET,SOCK_STREAM,0);
    if (sock == -1)
    {
        perror("socket");
        exit(-1);
    }
 
    /*mode non bloquant ( pour accept() )*/
    flags=fcntl(sock,F_GETFL);
    fcntl(sock,F_SETFL,flags | O_NONBLOCK);
 
    sin.sin_family=AF_INET;
    sin.sin_port=htons(port);
    sin.sin_addr.s_addr=htonl(INADDR_ANY);
    if (bind(sock,(struct sockaddr*)&sin,sizeof sin) == -1)
    {
        perror("bind");
        exit(-1);
    }
 
    listen(sock,MAX_CLIENT);
 
    /*on met toutes les sockets en invalides*/
    for(i=0;i<MAX_CLIENT;i++)
    {
        csock[i]=-1;
    }
 
    while (1)
    {
        i=0;
 
        /*si on peut encore recevoir des clients*/
        if (nb_client < MAX_CLIENT)
        {
            /*gérer la connection de nouveaux clients*/
            struct sockaddr_in new_sin;
            int new_client=0;
            int success=0;
 
            new_client = accept(sock,(struct sockaddr*)&new_sin,&size);
            /*si le client est valide (il peut être invalide puisque sock est en mode non bloquante)*/
            if (new_client != -1)
            {
                printf("Un nouveau client tente de se connecter : «%s» .Socket : %d . ",inet_ntoa(new_sin.sin_addr),new_client);
 
                /*on trouve une place pour le client*/
                while(i < MAX_CLIENT)
                {
                    if(csock[i] == -1)
                    {
                        csock[i]=new_client;
                        csin[i]=new_sin;
                        success=1;
                        break;
                    }
                    i++;
                }
 
                if(success)
                {
                    puts("Accepter");
                    nb_client++;
                }
                /*si il n'y a plus de place ( normalement il doit y en avoir puisqu'on à vérifier que nb_client < MAX_CLIENT )*/
                else
                {
                    puts("Refuser");
                }
            }
        }
 
        i=0;
 
        /*on parcours le tableau de client*/
        while (i < MAX_CLIENT)
        {
            /*si client valide*/
            if (csock[i] != -1)
            {
                int s_recv=0;
                if ( (s_recv=recv(csock[i],buf,32,MSG_DONTWAIT) ) == 32)
                {
                    /*tout c'est bien passer*/
                    printf("%s (csock : %d) : envoit : «%s»\n",inet_ntoa(csin[i].sin_addr),csock[i],buf);
                    /*on renvoit*/
                    send(csock[i],buf,32,0);
                }
                else if (s_recv == 0)
                {
                    /*si la taille reçu égale à 0 : déconnection */
                    printf("%s (csock : %d) : déconnection\n",inet_ntoa(csin[i].sin_addr),csock[i]);
                    /*on ferme la socket*/
                    close(csock[i]);
                    /*on libère une place de client*/
                    csock[i]=-1;
                    nb_client--;
                }
                else if (s_recv == -1);/* = pas de données reçu ( mode non bloquant de recv)*/
 
            }
            i++;
        }
        /*
        mettre gestion du temps avec usleep() si posix ou sleep() si windows
        */
    }
 
    close(sock);

    end();
}
