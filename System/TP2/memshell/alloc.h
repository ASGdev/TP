#ifndef __MEM_ALLOC_H
#define __MEM_ALLOC_H

#include <stddef.h>

#define MAX_INDEX 20
#define HEAP_SIZE (1 << MAX_INDEX)

char mem_heap[HEAP_SIZE]; //variable globale stockant l'adresse de la zone (utile pour le shell)
int mem_init();
void *mem_alloc(size_t size);
int mem_free(void *zone, size_t size);
int mem_show(void (*print)(void *zone, size_t size));

#endif
