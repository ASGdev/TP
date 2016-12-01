#include "alloc.h"
#include <stdio.h>

info_bloc* inf;
/*
 * Initialisation de la mémoire
 */
 void mem_init(){
		inf= (info_bloc*)mem_heap;      
        inf->size=HEAP_SIZE;
        inf->next=NULL;
}
/*
 * Allocation de la mémoire
 */
void *mem_alloc(size_t size){
	info_bloc* bloc_courant;
	size_t taille_bloc = 0;
	int k=1;
	while(taille_bloc<size){
		taille_bloc=k*sizeof(&inf);
		k++;
	}
	if(taille_bloc<=inf->size){
		bloc_courant = inf;
		bloc_courant->size = taille_bloc;
		
		inf= (unsigned long)inf + taille_bloc;
		inf=(info_bloc*)inf;
		inf->size = inf->size - taille_bloc;

		bloc_courant->next = (struct info_bloc *)inf;
  } 
  return  bloc_courant;
 }
/*
 * Libération de la mémoire
 */
 void mem_free(void *zone, size_t size){
	 
	 }
 /*
  * 
  */
 void mem_show(void (*print)(void *zone, size_t size)){
	 info_bloc* courant;
	 
	 courant= (info_bloc*) mem_heap;
	 while(courant->next!=NULL){
		 (*print)(courant, courant->size);
		 courant = (info_bloc*) courant-> next;
		 }
	}

