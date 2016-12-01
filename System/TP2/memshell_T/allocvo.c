#include "alloc.h"
#include <stdio.h>
#include "alloc.h"
#include <stdio.h>

zone* TETE;
unsigned long Tinfo=sizeof(size_t)+sizeof(zone);
// &TETE -> adresse *TETE valeur dans le pointeur

//Initialisation de la mémoire
void mem_init(){
		TETE= (zone*)mem_heap; // pointe sur l'adresse du début de la zone memoire
        TETE->size=HEAP_SIZE;
        TETE->next=NULL;
}


//Allocation de la mémoire
void *mem_alloc(size_t size){
	zone* ptr_temp = TETE;
	zone* ptr=NULL;
	zone* CC; // CREATION d'une CelluleCourante
	size_t size_bloc;
	size_t temp_size;
	zone * temp_next;
	
	// size DU BLOC 
	if (size <= sizeof(TETE))
	{
		size_bloc=size;
	}
	else // size est plus grand que la size de la structure
	{
		if (sizeof(TETE)%size!=0)// Redimensionnement la size du bloc
		{
			size_bloc = sizeof(TETE) * (sizeof(TETE)%size);
		}
		else
		{ size_bloc = sizeof(TETE);}
		//printf("size de la TETE %ld-- size bloc %ld --- size %ld\n ",sizeof(TETE),size_bloc,size);
	}
	
	// AJOUT FIRST FIT
	if (ptr_temp->next!=NULL)
	{
		printf("ajout 2\n");
		// RECHERCHE d'une place libre
		while (ptr_temp!=NULL)
		{
			// Verification de la size
			if(ptr_temp->size <= size_bloc)
			{
				CC = ptr_temp;
				ptr_temp = (size_t)ptr_temp + size_bloc; // on déplace l'adresse de TETE a "TETE + size_bloc"
				ptr_temp = (zone*)ptr_temp;
				ptr_temp->size = CC->size - size_bloc;
				if (ptr==NULL)
				{TETE->next=ptr_temp;}
				else {ptr->next=ptr_temp;}
				
				CC->next=NULL;
				return CC;
			}
			else
			{
				ptr= ptr_temp;
				ptr_temp= ptr_temp->next;
			}
		}
		return  NULL;
	}
	else // AJOUT
	{
		printf("ajout 1\n");
		temp_size = TETE->size;
		
		CC =TETE;
		CC->size = size_bloc;
		printf("T=%ld\n",TETE->size);
		TETE = (size_t)TETE + size_bloc; // on déplace l'adresse de TETE a "TETE + size_bloc"
		TETE = (zone*)TETE;
		printf("T=%ld\n",TETE->size);
		TETE->size = temp_size - size_bloc;
		printf("temp_size%ld - size_bloc%ld = %ld\n",temp_size, size_bloc,temp_size - size_bloc);printf("T=%ld\n",TETE->size);
		TETE->next=NULL;
		printf("T=%ld\n",TETE->size);
		printf("TETE->size %ld, CC->size%ld , size_bloc%ld , temp_size%ld \n",TETE->size, CC->size , size_bloc, temp_size);
		return CC;
	}
  
}


// FONCTION APPARTIENT
// Si le pointeur de l'adresse appartient à la liste chainée des adresses libres
zone* appartient(zone* ptr){
	zone* CC = TETE;
	
	if (ptr==NULL)
	{return NULL;}
	else if (TETE==ptr){
		return TETE;
	} 
	else{
		while (CC->next!=ptr){
			CC = CC->next;
		}
		return CC;
	}
}


void mem_free(void * z, size_t size){
	// On parcourt la liste chainée
	zone* ptr_temp = (zone*)mem_heap; 	
	zone* ptr_ancien = NULL;
	zone* avant=NULL;
	zone* apres=NULL;
	printf("z %ld \n",z);
	while(ptr_temp!= z ){
		ptr_ancien = ptr_temp;
		ptr_temp = (unsigned long)ptr_ancien + ptr_ancien->size;
		ptr_temp = (zone*)ptr_temp;
	printf("ptr_ancien %ld , ptr_temp %ld , ptr_ancien->size:%ld, sizeof(ptr_ancien )%ld\n",ptr_ancien,ptr_temp,ptr_ancien->size,sizeof(ptr_ancien));	
	}
	printf("2");
	// On a trouvé la zone à liberer : PLUSIEURS CAS POSSIBLE
	avant=appartient(ptr_ancien);
	apres=appartient(ptr_temp->next);
	printf("avant %ld, apres %ld\n",avant,apres);
	// CAS 1: zone libre à GAUCHE (FUSION GAUCHE)
	if(avant!=NULL){
		printf("FUSION GAUCHE\n");
		ptr_ancien->size = ptr_ancien->size + ptr_temp->size;
		ptr_temp->next = NULL;
	}
	// CAS 2: zone libre à DROITE (FUSION DROITE)
	if(apres!=NULL){
		printf("FUSION DROITE\n");
		ptr_temp->size = ptr_temp->size + ptr_temp->next->size;
		apres->next = ptr_temp;
	}
	// CAS 3: zone pleine à DROITE et GAUCHE (RAJOUT dans la liste chainée)
	if(avant==NULL && apres==NULL){
	printf("en attente\n");
	}	
}


// WRITE
void mem_show(void (*print)(void * zone, size_t size)){
	zone* CC = TETE;

	while(CC!=NULL){
		(*print)(CC, CC->size);
		CC = CC->next;
	}
}
