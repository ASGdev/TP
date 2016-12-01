#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>
#include "alloc.h"

/* Pointeur vers le premier élément */
void *mem_heap=0;

/* On définit la structure des informations stockées */
struct mem{
    unsigned long taille; /*4 octets ou 8 octets (selon 32 ou 64 bits)*/
    struct mem *suiv; /*4 octets donc un total de Tinfo=8 ou 16 octets pour ce qu'on appelera les "blocs d'information" qui se trouve en tete de zones de memoire libres */
};

typedef struct mem *Zone;

/*TailleInfo représente la taille qu'occupe l'information des zones vides dans la mémoire*/
unsigned long Tinfo=sizeof(size_t)+sizeof(Zone);

/*
Cette procédure initialise la liste des zones libres avec une seule
zone correspondant à l'ensemble du tableau mem_heap. Lorsqu’on appelle
mem_init alors que des allocations et libérations ont déjà été effectuées,
l’ensemble des structures de données est réinitialisé.
*/
int mem_init()
{
    /* On alloue une espace memoire de 2^20+Tinfo octets contigue(le +Tinfo sert à pouvoir allouer 2^20=HEAP_SIZE au max sans perdre le pointeur de début de zone*/
	mem_heap=malloc((HEAP_SIZE+Tinfo)*sizeof(char));

	if(mem_heap!=NULL)
	{
	    /* On crée une zone,mem_heap contenant l'information de cette espace et on le place au début*/
        Zone p=mem_heap;
        p->taille=HEAP_SIZE;
        p->suiv=p;
		return 0;
	} return 1;
}

/*
Cette procédure reçoit en paramètre la taille size de la zone à allouer (cette
taille est arrondie au plus petit multiple de la taille du descripteur de zone supérieur ou
égal à size). Elle retourne un pointeur vers la zone allouée et NULL en cas
d’allocation impossible.
*/
void *mem_alloc(size_t tailleZone)
{
	Zone p=mem_heap;

	// Cas particulier mem_heap non allouée
	if (p==NULL)
        return 0;

	// On refuse les demandes farfelues de l'utilisateur
	if((tailleZone==0) || (tailleZone>HEAP_SIZE))
        return 0;

	/*on alloue les blocs par multiples de Tinfo=8 ou 16 afin de s'assurer de ne pas avoir des groupes de blocs d'information seuls ou tronques*/
	/*l'inconvenient de cette methode est que l'on donne à l'utilisateur de la memoire dont il ne se servira pas (maximum 7 ou 15 octets) mais elle permet de liberer facilement la memoire apres utilisation*/
	if(tailleZone%Tinfo!=0)
		tailleZone=((tailleZone/Tinfo)+1)*Tinfo;

	// On parcours la liste des zones libres
	do
	{
        /* Si l'espace de la zone est supérieure à l'espace demandée + Tinfo, alors on accorde l'espace en actualisant l'espace disponible dans la zone sans la détruire. NB : on remplit les zones libres "par le bas"*/
        if(tailleZone<=p->taille)
        {
            p->taille=(p->taille)-tailleZone;
            return (void*)(size_t)p + p->taille + Tinfo;
        }
        /*cas ou la place n'est pas assez grande pour allouer sans effacer le bloc d'information -> on court-cuircuite le bloc d'information et on le donne a l'utilisateur avec le reste.
        Note: on ne touche pas au premier bloc d'information quoi qu'il arrive, il ne sera jamais alloue. Cela permet de pouvoir toujours acceder à la liste par le haut de l'espace memoire et facilite la recherche des blocs lors de la liberation*/
        else if (p!=mem_heap && (tailleZone<=((p->taille)+Tinfo)))
        {
            Zone p2=mem_heap;
            /* On raccorde la chaine sans la zone en question */
            while(p2->suiv!=p)
            {
                p2=p2->suiv;
            }
            p2->suiv=p->suiv;
            return p;
        }
        p=p->suiv;
	}while (p!=mem_heap);
	return(void*) 0;
}

/*
Cette procédure reçoit en paramètres l’adresse zone et la taille size de la zone
à libérer. Elle met à jour la liste des zones libres avec fusion éventuelle des zones libres
contiguës.
*/
int mem_free(void * zone, size_t TailleZone){
    //cas particulier probleme : la memoire a ete detruite <<<< mem heap n'est theoriquement jamais détruite
    if(mem_heap==NULL)
        return 1;

    //On controle si l'utilisateur ne demande pas de liberer un pointeur situé hors de la zone possible
    if((zone<mem_heap+Tinfo) || (zone> mem_heap + (HEAP_SIZE+Tinfo)))
        return 1;

    /* On crée une zone mémoire de taille TailleZone-Tinfo */
    Zone z = zone;

    // On réadapte TailleZone au multiple de Tinfo supérieur ou égal (soit ce qu'on a réellement donné à l'utilisateur)
    if(TailleZone%Tinfo!=0)
        z->taille=(TailleZone/Tinfo+1)*Tinfo - Tinfo;
    else
        z->taille=TailleZone-Tinfo;

    /* On va "inserer" cette zone mémoire dans la chaine */
    Zone parcours=mem_heap;

    //Recherche de l'emplacement dans la chaine//
    while((parcours->suiv)<z && parcours->suiv!=mem_heap)
        parcours=parcours->suiv;

    //Insertion//
    z->suiv=parcours->suiv;
    parcours->suiv=z;

    /* La suite du programme va nous permettre de regrouper les zones mémoires */
    /* On regarde la zone suivante */
    if((size_t)z+ Tinfo + z->taille == (size_t)z->suiv)
    {
        z->taille= z->taille + Tinfo+ (z->suiv)->taille;
        z->suiv=z->suiv->suiv;
    }
    /* On regarde la zone précedente */
    if((size_t)(parcours)+Tinfo+parcours->taille == (size_t)parcours->suiv )
    {
        parcours->taille= parcours->taille + Tinfo + (parcours->suiv)->taille;
        parcours->suiv=parcours->suiv->suiv;
    }
    return 0;
}

/*
Cette procédure reçoit en paramètre l’adresse d’une procédure print ayant elle-
même deux paramètres, l’adresse d’une zone et une taille. Cette procédure print doit
être utilisée pour afficher à l’écran la spécification d’une zone libre.
*/
int mem_show(void (*print)(void *zone, size_t size)) {
    // on traite le cas d'erreur
    if(mem_heap==NULL)
        return 1;

    Zone parcours=mem_heap;

    do
    {
        print(parcours,parcours->taille);
        parcours=parcours->suiv;
    }while(parcours != mem_heap);

    return 0; //si pas d'erreur on rend 0
}
