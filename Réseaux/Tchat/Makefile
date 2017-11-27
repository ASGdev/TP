
CC=gcc
CFLAGS=-W -Wall -ansi -pedantic
LDFLAGS=
EXEC=ServerMain ClientMain

all: $(EXEC)

ServerMain: ServerMain.c
	$(CC) ServerMain.c -o ServerMain   $(LDFLAGS)

ClientMain: ClientMain.c
	$(CC) ClientMain.c -o ClientMain   $(LDFLAGS)


clean:
	rm -rf *.o

mrproper: clean
	rm -rf $(EXEC)