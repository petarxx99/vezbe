
#ifndef PAS_H
#define PAS_H


#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>



typedef struct PasVTableStruct{
    void (*obrisi)(void **Pas);
    void (*ispisiPodatkeOZivotinji)(void *Pas);
    int (*dajGodine) (void *Pas);
    bool (*daLiJeVlasnikStariji) (void *Pas);
} PasVTable;


typedef struct PasStruct{
    PasVTable *vTable;
    int godine;
    const char* vlasnik;
    int godineVlasnika;
} Pas;

void ispisiPodatkeOPsu(void *pasVoid);
int dajGodinePsa(void *pasVoid);
bool daLiJeVlasnikStariji(Pas *pas);

struct PasVTableStruct pasVTableGlobal;


Pas* Pas_new(int godine);

Pas *Pas_new_sve(int godine, int godineVlasnika, char* imeVlasnika);

void Pas_obrisi(Pas **adresaPointeraPsa);

#endif // PAS_H
