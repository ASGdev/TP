
CC=gcc
CFLAGS=-W -Wall -ansi -pedantic
LDFLAGS=
EXEC=ClientMain ServeurMain

all: $(EXEC)

ServeurMain: ServeurMain.o ClientMain.o
	$(CC) -o ServeurMain ServeurMain.o ClientMain.o $(LDFLAGS)

ServeurMain.o: ServeurMain.c
	$(CC) -o ServeurMain.o -c ServeurMain.c $(CFLAGS)

ClientMain: ClientMain.c
	$(CC) -o ClientMain.o -c ClientMain.c $(CFLAGS)

ClientMain.o: ClientMain.c ServeurMain.h
	$(CC) -o ClientMain.o -c ClientMain.c $(CFLAGS)



clean:
	rm -rf *.o

mrproper: clean
	rm -rf $(EXEC)