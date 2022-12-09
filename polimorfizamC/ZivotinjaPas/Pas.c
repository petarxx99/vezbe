#ifndef PAS_C
#define PAS_C


#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>
#include"Zivotinja.h"


typedef struct PasVTableStruct{
    bool (*obrisi)(void **Pas);
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

void ispisiPodatkeOPsu(void *pasVoid){
    Pas *pas = (Pas*)pasVoid;
    printf("Pas ima %d godina, vlasnik se zove %s, vlasnik ima %d godina. \n", pas->godine, pas->vlasnik, pas->godineVlasnika);
}

int dajGodinePsa(void *pasVoid){
    Pas *pas = (Pas*) pasVoid;
    return pas->godine;
}

bool daLiJeVlasnikStariji(Pas *pas){
    return pas->godineVlasnika >= pas->godine;
}

void Pas_obrisi(Pas **adresaPointeraPsa){
    Pas *pasZaBrisanje = *adresaPointeraPsa;
    free(pasZaBrisanje);
    *adresaPointeraPsa = NULL;
}


struct PasVTableStruct pasVTableGlobal = {
    Pas_obrisi,
    ispisiPodatkeOPsu,
    dajGodinePsa,
    daLiJeVlasnikStariji
};


Pas* Pas_new(int godine){
    Pas *z = (Pas*) malloc(sizeof(Pas));
    z->vTable = (&pasVTableGlobal);
}

Pas *Pas_new_sve(int godine, int godineVlasnika, char* imeVlasnika){
    Pas *pas = (Pas*) malloc(sizeof(Pas));
    pas->godine = godine;
    pas->godineVlasnika = godineVlasnika;
    pas->vlasnik = imeVlasnika;
    pas->vTable = &pasVTableGlobal;
}


#endif // PAS_C
