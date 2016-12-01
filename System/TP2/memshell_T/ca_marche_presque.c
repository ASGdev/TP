#include "alloc.h"
#include <stdio.h>
#include "alloc.h"
#include <stdio.h>

#define COMMENTAIRE 0

zone* TETE;
//TailleInfo représente la taille qu'occupe l'information des zones vides dans la mémoire
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
	
	/*
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
	}*/
	
	////////////////////////////////////////////////////////////:
	/*on alloue les blocs par multiples de Tinfo=8 ou 16 afin de s'assurer de ne pas avoir des groupes de blocs d'information seuls ou tronques*/
	/*l'inconvenient de cette methode est que l'on donne à l'utilisateur de la memoire dont il ne se servira pas (maximum 7 ou 15 octets) mais elle permet de liberer facilement la memoire apres utilisation*/
	if(size%Tinfo!=0)
		size_bloc=((size/Tinfo)+1)*Tinfo;
	///////////////////////////////////////////////:
	
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
	else // AJOUT TETE= (zone*)mem_heap
	{
		printf("ajout 1\n");
		temp_size = TETE->size;
		if (COMMENTAIRE)printf("SIZE%ld\n",size_bloc);
		CC=TETE; // CC pointe sur l'adresse de la structure TETE de la liste chainé
		if (COMMENTAIRE)printf("l'adresse de TETE : %d pointe sur %ld || adresse de CC : %d pointe sur %ld\n",&TETE,TETE,&CC,CC);
		TETE = (size_t)CC + size_bloc; // on déplace l'adresse de TETE de "size_bloc"
		TETE = (zone*)TETE;
		if (COMMENTAIRE)printf("l'adresse de TETE : %d pointe sur %ld || adresse de CC : %d pointe sur %ld\n",&TETE,TETE,&CC,CC);
		
		
		CC->size = size_bloc;TETE->size = temp_size - size_bloc;
		CC->next=NULL;
		if (COMMENTAIRE)
		{printf("CC->size %ld\n",CC->size);
		printf("TETE->size %ld\n",TETE->size);}
		return CC;
	}
  
}


// FONCTION APPARTIENT
// Si le pointeur de l'adresse appartient à la liste chainée des adresses libres
int appartient_liste_chaine_libre(zone* z){
	zone* CC = TETE;
	printf("z=%ld CC=%ld\n",z,CC);
	if (z==NULL){return 0;}
	else{
		while (CC!=NULL && CC->next!=z){
			CC = CC->next;
			printf("z=%ld CC=%ld\n",z,CC);
		}printf("return %d\n",CC!=NULL);
		return CC!=NULL;
	}
}

//FUSION DROITE
void fusion_droite(){
}
// FUSION GAUCHE
void fusion_gauche(){
}

void mem_free(void * z, size_t size){
	// On parcourt la liste chainée
	zone* ptr_temp = (zone*)mem_heap;
	zone* parcours = NULL;
	zone* ptr_prec = NULL;
	zone* ptr_post = NULL;
	int gauche,droite;
	
	printf("z %ld \n",z);
	while(ptr_temp!= z ){
		ptr_prec = ptr_temp;
		ptr_temp = (unsigned long)ptr_prec + ptr_prec->size;
		ptr_temp = (zone*)ptr_temp;
	printf("ptr_prec %ld , ptr_temp %ld , ptr_prec->size:%ld,\n",ptr_prec,ptr_temp,ptr_prec->size);	
	}
	printf("ptr_post=%ld\n",(unsigned long)ptr_temp+ptr_temp->size);
	ptr_post=(unsigned long)ptr_temp+ptr_temp->size;
	ptr_post = (zone*)ptr_post;
	
	
	//On parcours la liste_chainée de zone libre et rajoute la nvll Cellule libre
	parcours = TETE;
	if (parcours!=NULL){
		if ( ((unsigned long)TETE)==((unsigned long)ptr_temp+ptr_temp->size) ) 
		{
			printf("FUSION DROITE---\n");
			ptr_temp->size = (ptr_temp->size) + TETE->size;
			ptr_temp = (zone*)ptr_temp;
			TETE=ptr_temp;
		}
		else if ( ((unsigned long)TETE+TETE->size)==((unsigned long)ptr_temp) )
		{
			printf("FUSION GAUCHE---\n");
			TETE->size = TETE->size + ptr_temp->size;
			TETE = (zone*)TETE;
			// VERIFICATION PAS DE FUSION TOTAL
		}
		else if(TETE>ptr_temp) // AJOUT EN TETE
		{
			ptr_temp->next=TETE;
			TETE=ptr_temp;		
		}
		else// AJOUT EN QUEUE
		{
			while (parcours->next!=NULL){
			parcours = parcours->next;
			}
			parcours->next=ptr_temp;
		}
	}

	// On a trouvé la zone à liberer : PLUSIEURS CAS POSSIBLE
	gauche=appartient_liste_chaine_libre(ptr_prec);
	droite=appartient_liste_chaine_libre(ptr_post);

	// CAS 1: zone libre à GAUCHE (FUSION GAUCHE)
	if(gauche){
		printf("FUSION GAUCHE\n");
		printf("AVANT ptr_prec->size=%ld\n",ptr_prec->size);
		ptr_prec->size = ptr_prec->size + ptr_temp->size;
		printf("APRES ptr_prec->size=%ld\n",ptr_prec->size);
		ptr_prec = (zone*)ptr_prec;
		
	
	}
	// CAS 2: zone libre à DROITE (FUSION DROITE)
	if(droite){
		printf("FUSION DROITE\n");
		printf("AVANT ptr_temp->size=%ld\n",ptr_temp->size);
		ptr_temp->size = (ptr_temp->size) + (ptr_temp->next->size);
		printf("APRES ptr_temp->size=%ld\n",ptr_temp->size);
		ptr_temp = (zone*)ptr_temp;
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
