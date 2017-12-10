
CC=gcc
CFLAGS=-W -Wall -ansi -pedantic
LDFLAGS=
EXEC=ServerMain ClientMain save

all: $(EXEC)

ServerMain: ServerMain.c
	$(CC) ServerMain.c -o ServerMain   $(LDFLAGS)

ClientMain: ClientMain.c
	$(CC) ClientMain.c -o ClientMain   $(LDFLAGS)

save: ServerMain.c
	$(CC) save.c -o save   $(LDFLAGS)

mrproper:
	rm -rf *.o

clean: mrproper
	rm -rf $(EXEC)
