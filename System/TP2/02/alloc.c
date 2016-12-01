#include "alloc.h"
#include <stdio.h>
#include "alloc.h"
#include <stdio.h>

#define COMMENTAIRE 1

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
	zone* parcours = TETE;
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
	
	
	// RECHERCHE d'une place libre
	while (ptr_temp!=NULL)
	{
	if(COMMENTAIRE){
		printf("l'adresse de TETE : %d pointe sur %ld || adresse de CC : %d pointe sur %ld\n",&TETE,TETE,&ptr_temp,ptr_temp);
		printf("ptr_temp->size%ld  size_bloc%ld\n",ptr_temp->size,size_bloc);
	}
		// Verification de la size valide
		if (ptr_temp->size >= size_bloc)
		{
			temp_size = ptr_temp->size;
			CC = ptr_temp;
			CC->size = size_bloc;
			CC->next=NULL;
			ptr_temp = (unsigned long)ptr_temp + size_bloc; // on déplace l'adresse de ptr_temp à "ptr_temp + size_bloc"
			ptr_temp = (zone*)ptr_temp;
			ptr_temp->size = temp_size - size_bloc;
			printf("ptr_temp->size%ld\n",ptr_temp->size);
			if(TETE == CC) // MISE A JOUR
			{
				TETE=ptr_temp;
			}
			else // AJOUT EN QUEUE il faut s'arreter a cc
			{
				while (parcours->next!=NULL){
				if(COMMENTAIRE)printf("parcours%ld TETE%ld\n",parcours,TETE);
				parcours = parcours->next;}
				parcours->next=ptr_temp;
			}			
			return CC;
		}	
		ptr_temp = ptr_temp->next;
	}
	return  NULL;
}
	
//FUSION DROITE:
// La zone p1 fusionne par la droite avec la zone p2
void fusion_droite(zone* p1, zone* p2){
	if(COMMENTAIRE)printf("FUSION DROITE---\n");
	p1->size = (p1->size) + p2->size;
	p1 = (zone*)p1;
}
// FUSION GAUCHE:
// La zone p1 fusionne par la gauche avec la zone p2
void fusion_gauche(zone* p1, zone* p2){
	if(COMMENTAIRE)printf("FUSION GAUCHE---\n");
	p2->size = p2->size + p1->size;
	printf("p2->size%ld p2->next%ld\n",p2->size,p2->next);
	p2 = (zone*)p2;
}
// FONCTION APPARTIENT
// Si le pointeur de l'adresse appartient à la liste chainée des adresses libres
int appartient_liste_chaine_libre(zone* z){
	zone* CC = TETE;
	if(COMMENTAIRE)printf("z=%ld CC=%ld\n",z,CC);
	if (z==NULL){return 0;}
	else if (z==CC) { 
		TETE=z;
		return 1;
	}	
	else{
		while (CC!=NULL && CC->next!=z){
			CC = CC->next;
			printf("z=%ld CC=%ld\n",z,CC);
		}printf("return %d\n",CC!=NULL);
		return CC!=NULL;
	}
}


void mem_free(void * z, size_t size){
	zone* ptr_temp = (zone*)mem_heap;
	zone* parcours = TETE;
	zone* ptr_prec = NULL;
	zone* ptr_post = NULL;
	int gauche,droite;
	
	// On parcourt le tableau mem_heap pour trouver les pointeur (ptr_prec && ptr_post)
	while(ptr_temp != z ){
		ptr_prec = ptr_temp;
		ptr_temp = (unsigned long)ptr_temp + ptr_temp->size;
		ptr_temp = (zone*)ptr_temp;
	}
	ptr_post=(unsigned long)ptr_temp+ptr_temp->size;
	ptr_post = (zone*)ptr_post;
	
	// On a trouvé la zone à liberer : PLUSIEURS CAS POSSIBLE
	printf("gauche\n");
	gauche=appartient_liste_chaine_libre(ptr_prec);printf("droit\n");
	droite=appartient_liste_chaine_libre(ptr_post);
	
	
	// CAS 1: zone libre à GAUCHE (FUSION GAUCHE)
	if(gauche){
		printf("FUSION GAUCHE\n");
		printf("AVANT ptr_prec->size=%ld\n",ptr_prec->size);
		ptr_prec->size = ptr_prec->size + ptr_temp->size;
		printf("APRES ptr_prec->size=%ld\n",ptr_prec->size);
		ptr_prec = (zone*)ptr_prec;
		
		
		if (TETE>ptr_temp) // AJOUT
		{
			ptr_temp->next=TETE;
			TETE=ptr_temp;		
		}
		else// AJOUT EN QUEUEaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		{
			while (parcours->next!=NULL){
			parcours = parcours->next;
			}
			parcours->next=ptr_temp;
		}
		
		
	}
	// CAS 2: zone libre à DROITE (FUSION DROITE)
	if(droite){
		//fusion_droite(ptr_temp,TETE);
		if(COMMENTAIRE){
		printf("FUSION DROITE---\n");
		printf("AVANT ptr_temp->size=%ld\n",ptr_temp->size);}
			
		if(ptr_temp->next!=NULL){ptr_temp->size = (ptr_temp->size) + ptr_temp->next->size;}
		else{ptr_temp->size=TETE->size +ptr_temp->size;}
		ptr_temp = (zone*)ptr_temp;
		if(COMMENTAIRE)printf("APRES ptr_temp->size=%ld\n",ptr_temp->size);
		
		if (((unsigned long)TETE)==((unsigned long)ptr_temp+ptr_temp->size) ){ TETE = ptr_temp;}
		else if(TETE>ptr_temp) // AJOUT
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
	// CAS 3 :
	if(!droite && !gauche){
		
		if (TETE>ptr_temp) // AJOUT
		{
			ptr_temp->next=TETE;
			TETE=ptr_temp;		
		}
		else// AJOUT EN QUEUE aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		{
			while (parcours->next!=NULL){
			parcours = parcours->next;
			}
			parcours->next=ptr_temp;
		}
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
