#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "alloc.h"
//pointe sur un descripteur de zone libre
fb* firstZone;


void mem_init(){
    //Initialise ou réinitialise la liste des zones libres.
    firstZone = (fb*) mem_heap;
    firstZone -> size = HEAP_SIZE;
    firstZone -> next = NULL;
}


void *mem_alloc(size_t size){
    fb *zone_courante = firstZone;
    //Pointeur de pointeur sur le bloc précédent. Il prend donc une adresse et pas une fb.
    fb **zone_memory = &firstZone;

    if(firstZone == NULL){
        return NULL;
    }

    if(size % sizeof(fb)){
        //printf("size before: %lu \n ",size);  debugging     
        //On rajoute sizeof(fb) pour le nouveau descripteur, et on soustrait le résultat du modulo 
        size = size + sizeof(fb) - (size % sizeof(fb));
        //printf("size after: %lu fin \n ",size); debugging
    }
    // On parcout les blocs pour trouver l'espace necessaire
    while(zone_courante->size < size){
        if(zone_courante->next == NULL){
            return NULL;
        }
        else{
            zone_memory = &(zone_courante->next);
            zone_courante = zone_courante->next;
        }
    }
    //taille du bloc parfaite
    if(zone_courante->size == size){
        *zone_memory = zone_courante->next;
    }
    //size du bloc trop importante
    else{
        //on crée le nouveau bloc qui va fusionner
        fb *newBlock = (fb*) ((char *)zone_courante + size);
        newBlock->size = zone_courante->size - size;
        newBlock->next = zone_courante->next;
        *zone_memory = newBlock;
    }
    return zone_courante;
}


void mem_free(void *zone, size_t size){
    
}


void mem_show(void (*print)(void *zone, size_t size)){
    fb* zone_printed = firstZone;
    while(zone_printed!=NULL){
        print((void*) zone_printed,zone_printed->size);
        zone_printed=zone_printed->next;
    }
}