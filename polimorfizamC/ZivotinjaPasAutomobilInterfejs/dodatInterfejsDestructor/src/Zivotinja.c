

#include<stdio.h>
#include<stdlib.h>


typedef struct ZivotinjaVTableStruct{
    void (*ispisiPodatkeOZivotinji)(void *zivotinja);
    int (*dajGodine) (void *zivotinja);
} ZivotinjaVTable;


typedef struct ZivotinjaStruct{
    ZivotinjaVTable *vTable;
    int godine;
} Zivotinja;


void ispisiPodatkeOOvojZivotinji(Zivotinja* zivotinja){
    printf("Ova zivotinja ima %d godina. \n", zivotinja->godine);
}

int dajGodineOveZivotinje(Zivotinja *z){
    return z->godine;
}

void Zivotinja_obrisi(Zivotinja **adresaPointeraKaZivotinji){
    Zivotinja *zivotinjaZaBrisanje = *adresaPointeraKaZivotinji;
    free(zivotinjaZaBrisanje);
    *adresaPointeraKaZivotinji = NULL;
    printf("Zivotinja sa adrese %lu je obrisana. \n", zivotinjaZaBrisanje);
}

struct ZivotinjaVTableStruct zivotinjaVTableGlobal = {Zivotinja_obrisi, ispisiPodatkeOOvojZivotinji, dajGodineOveZivotinje};


Zivotinja* Zivotinja_new(int godine){
    ZivotinjaVTable *vTable = &zivotinjaVTableGlobal;

    Zivotinja *z = (Zivotinja*) malloc(sizeof(Zivotinja));
    z->vTable = vTable;
    z->godine = godine;
}



