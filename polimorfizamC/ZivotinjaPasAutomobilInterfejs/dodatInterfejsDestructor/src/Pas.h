
#ifndef PAS_H
#define PAS_H


#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>
#include"MozeProizvestiZvuk.h"


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

    unsigned int kolikoPutaJeProizveoZvuk;
    MozeProizvestiZvuk* (*getMozeProizvestiZvuk)(struct PasStruct *_this);

} Pas;


void ispisiPodatkeOPsu(void *pasVoid);
int dajGodinePsa(void *pasVoid);
bool daLiJeVlasnikStariji(Pas *pas);

struct PasVTableStruct pasVTableGlobal;


Pas* Pas_new(int godine);

Pas *Pas_new_sve(int godine, int godineVlasnika, char* imeVlasnika);

void Pas_obrisi(Pas **adresaPointeraPsa);


unsigned int Pas_getBrojZvukova(Pas *_this);
void Pas_proizvediZvuk(Pas *_this);
void Pas_interfejsMozeProizvestiZvukJeObrisan(Pas *_this, void *interfejs);

#endif // PAS_H
