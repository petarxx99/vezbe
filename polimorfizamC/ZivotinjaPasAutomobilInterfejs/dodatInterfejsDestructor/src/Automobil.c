
#include<stdio.h>
#include"MozeProizvestiZvuk.h"
#include<stdlib.h>

typedef struct AutomobilStruct{
    const char *naziv;
    int godinaProizvodnje;

    unsigned int kolikoPutaJeProizveoZvuk;
    MozeProizvestiZvuk* (*getMozeProizvestiZvuk)(struct AutomobilStruct *_this);
}Automobil;

MozeProizvestiZvuk* Automobil_getMozeProizvestiZvuk(Automobil *automobil);


Automobil* Automobil_new(const char* naziv, int godiste){
    Automobil *automobil = (Automobil*) malloc(sizeof(Automobil));
    automobil->naziv = naziv;
    automobil->godinaProizvodnje = godiste;
    automobil->kolikoPutaJeProizveoZvuk = 0;

    automobil->getMozeProizvestiZvuk = Automobil_getMozeProizvestiZvuk;

    return automobil;
}

void Automobil_delete(Automobil **adresaAutomobilPointera){

    free(*adresaAutomobilPointera);
    printf("Automobil sa adrese %lu je obrisan. \n", *adresaAutomobilPointera);
    *adresaAutomobilPointera = NULL;
}


unsigned int Automobil_getKolikoJeZvukovaProizveo(Automobil *automobil){
    return automobil->kolikoPutaJeProizveoZvuk;
}

void Automobil_proizvediZvuk(Automobil *automobil){
    printf("Automobil koji se zove %s, godiste %d proizvodi zvuk. \n", automobil->naziv, automobil->godinaProizvodnje);
    automobil->kolikoPutaJeProizveoZvuk++;
}

void Automobil_interfejsMozeProizvestiZvukObrisan(Automobil *_this, void *interfejs){
    if(_this == NULL){
        printf("Automobil_interfejsMozeProizvestiZvukObrisan je dobila NULL pointer. \n", stderr);
        return;
    }

    printf("Automobil %s, godista %d je obavesten da je njegov MozeProizvestiZvuk interfejs sa adrese %lu obrisan. \n",
           _this->naziv,
           _this->godinaProizvodnje,
           interfejs);
}

MozeProizvestiZvuk* Automobil_getMozeProizvestiZvuk(Automobil *automobil){
    MozeProizvestiZvuk *mozeProizvestiZvuk = (MozeProizvestiZvuk*) malloc(sizeof(MozeProizvestiZvuk));

    mozeProizvestiZvuk->strukturaKojuMetodeInterfejsaKoriste = automobil;

    mozeProizvestiZvuk->proizvediZvuk = Automobil_proizvediZvuk;
    mozeProizvestiZvuk->getKolikoPutaJeProizveoZvuk = Automobil_getKolikoJeZvukovaProizveo;
    mozeProizvestiZvuk->primiPorukuDaJeInstancaInterfejsaObrisana = Automobil_interfejsMozeProizvestiZvukObrisan;

    return mozeProizvestiZvuk;
}
