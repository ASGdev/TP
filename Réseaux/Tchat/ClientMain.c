#ifdef WIN32 /* si vous êtes sous Windows */

#include <winsock2.h> 

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
	char *mess; 		
	char mesage[255];
}

int main(int argc, char* argv[]){
    init();
    unsigned long add_IP;
    unsigned long port;
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

    end();
}

