CC=gcc
SRC=alloc.c memshell.c
CFLAGS=-Wall -o
EXEC=alloc

all: $(EXEC)

$(EXEC): $(SRC)
	$(CC) $(CFLAGS) $@ $^

clean:
	rm -rf *.o

mrproper: clean
	rm -rf $(EXEC)
