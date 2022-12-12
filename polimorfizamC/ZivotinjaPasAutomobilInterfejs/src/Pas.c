

#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>
#include"Zivotinja.h"
#include"MozeProizvestiZvuk.h"

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

    unsigned int kolikoPutaJeProizveoZvuk;
    MozeProizvestiZvuk* (*getMozeProizvestiZvuk)(struct PasStruct *_this);

} Pas;

MozeProizvestiZvuk* Pas_getMozeProizvestiZvuk(Pas *_this);

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
    z->godine = godine;
    z->kolikoPutaJeProizveoZvuk = 0;
    z->vTable = (&pasVTableGlobal);
    z->getMozeProizvestiZvuk = Pas_getMozeProizvestiZvuk;

    return z;
}

Pas *Pas_new_sve(int godine, int godineVlasnika, char* imeVlasnika){
    Pas *pas = (Pas*) malloc(sizeof(Pas));

    pas->kolikoPutaJeProizveoZvuk = 0;
    pas->godine = godine;
    pas->godineVlasnika = godineVlasnika;
    pas->vlasnik = imeVlasnika;
    pas->vTable = &pasVTableGlobal;
    pas->getMozeProizvestiZvuk = Pas_getMozeProizvestiZvuk;

    return pas;
}



unsigned int Pas_getBrojZvukova(Pas *_this){
    return _this->kolikoPutaJeProizveoZvuk;
}

void Pas_proizvediZvuk(Pas *_this){
    printf("Pas godina %d, vlasnika %s je proizveo zvuk.\n", _this->godine, _this->vlasnik);
    _this->kolikoPutaJeProizveoZvuk++;
}

MozeProizvestiZvuk* Pas_getMozeProizvestiZvuk(Pas *_this){
    MozeProizvestiZvuk *mozeProizvestiZvuk = (MozeProizvestiZvuk*) malloc(sizeof(MozeProizvestiZvuk));

    mozeProizvestiZvuk->getKolikoPutaJeProizveoZvuk = Pas_getBrojZvukova;
    mozeProizvestiZvuk->proizvediZvuk = Pas_proizvediZvuk;
    mozeProizvestiZvuk->strukturaKojuMetodeInterfejsaKoriste = _this;

    return mozeProizvestiZvuk;
}
