
#ifndef ZIVOTINJA_H
#define ZIVOTINJA_H


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


void ispisiPodatkeOOvojZivotinji(Zivotinja* zivotinja);

int dajGodineOveZivotinje(Zivotinja *z);

struct ZivotinjaVTableStruct zivotinjaVTableGlobal;

Zivotinja* Zivotinja_new(int godine);

#endif // ZIVOTINJA_H

