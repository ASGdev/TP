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

    SOCKET sock = socket(AF_INET, SOCK_STREAM, 0);
    if(sock == INVALID_SOCKET)
    {
        perror("socket()");
        exit(errno);
    }

    end();
}
