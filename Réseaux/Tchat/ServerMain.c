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

    /* DECLARATION DES VARIABLES
        
    */
    struct	sockaddr_in  adr_serv, adr_client;
    int num_socket,num_socket_client ;
    int nbClient;
    //INIT SOCKET

    SOCKET sock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if(sock == INVALID_SOCKET)
    {
        perror("socket()");
        exit(errno);
    }
    //Renseignement de la structure sockaddr_in adr_serv
  	adr_serv.sin_family = AF_INET;
  	adr_serv.sin_port = htons(PORT);
  	adr_serv.sin_addr.s_addr = htonl(INADDR_ANY);

	//Bind
  	if (bind (num_socket, (struct sockaddr *) &adr_serv, sizeof adr_serv)==0)	printf("Bind bien passe\n"); else exit(-1);
    printf("Bind bien passe\n");
	//Mise en écoute de la socket
  	if (listen(num_socket, SOMAXCONN)==0)	printf("Listen bien passe : j'ecoute...\n");
    printf("Bind bien passe2\n");

    end();
}
