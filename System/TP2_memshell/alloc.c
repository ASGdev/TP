#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "alloc.h"
//pointe sur un descripteur de zone libre
fb* zone_libre;


void mem_init(){
    //Initialise ou réinitialise la liste des zones libres.
    zone_libre = (fb*) mem_heap;
    zone_libre->size = HEAP_SIZE;
    zone_libre->next = NULL;
}


void *mem_alloc(size_t size){
    fb* zone_courante;
    zone_courante = zone_libre;
    int dispo=0;
    //fb* zone_memory;
    //zone_memory = zone_libre;
    void* zone_alloue = NULL; //Pointeur de retour sur la zone
    //Modification de size si il n'est pas modulo la taille de notre bloc de base 
    if(size % sizeof(fb)){
        printf("size before: %lu \n ",size);        
        size = size - (size % sizeof(fb)) + sizeof(fb);
        printf("size after: %lu fin \n ",size);
    }
    while(dispo!=1 && zone_courante != NULL){
        if(zone_courante->size - sizeof(fb) >= size){
            dispo=1;
        }
        else{
            zone_courante = zone_courante->next;
        }
    }
    if(dispo){

        unsigned long size_memory=zone_courante->size;

        zone_courante->size -=size;
        //on donne l'adresse de la zone allouée
        zone_alloue=(void*)(zone_courante + zone_courante->size);
        //on relie au descripteur au dessus de la zone allouée
        /*
        if(zone_courante->next==NULL){
            zone_memory = zone_courante; 
            zone_memory=(void*)(zone_alloue+size);
            zone_memory->next=NULL;
            zone_memory->size=size_memory-zone_courante->size;
            zone_courante->next=zone_memory; //courant fixe son next sur memory
        } */
    }
    return zone_alloue;
}


void mem_free(void *zone, size_t size){
    fb* zone_courante=zone_libre;
    fb* zone_suivante;
}


void mem_show(void (*print)(void *zone, size_t size)){
    fb* zone_printed = zone_libre;
    while(zone_printed!=NULL){
        print((void*) zone_printed,zone_printed->size);
        zone_printed=zone_printed->next;
    }
}