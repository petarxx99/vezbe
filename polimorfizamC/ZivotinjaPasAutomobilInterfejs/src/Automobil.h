#ifndef AUTOMOBIL_H
#define AUTOMOBIL_H


#include"MozeProizvestiZvuk.h"


typedef struct AutomobilStruct{
    const char *naziv;
    int godinaProizvodnje;

    unsigned int kolikoPutaJeProizveoZvuk;
    MozeProizvestiZvuk* (*getMozeProizvestiZvuk)(struct AutomobilStruct *_this);
}Automobil;

MozeProizvestiZvuk* Automobil_getMozeProizvestiZvuk(Automobil *automobil);


Automobil* Automobil_new(const char* naziv, int godiste);
void Automobil_delete(Automobil **adresaAutomobilPointera);

unsigned int Automobil_getKolikoJeZvukovaProizveo(Automobil *automobil);

void Automobil_proizvediZvuk(Automobil *automobil);

#endif // AUTOMOBIL_H
