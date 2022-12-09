#ifndef KARAKTERI_ZAMENE
#define KARAKTERI_ZAMENE


#include"includeFajl.h"


#include"mojString.h"


typedef struct KarakteriZameneStruct{
    String *karakteriZaSifru;
    int brojKojiDodajemNaASCI;

    char **paroviKarakteraKojeMenjam;
    int brojParovaKarakteraKojeMenjam;
} KarakteriZamene;

bool obrisiKaraktereZamene(KarakteriZamene *kz){
    if(kz->karakteriZaSifru != NULL){
        obrisiStringIstaviGaNaNull(&(kz->karakteriZaSifru));
    }

    if(kz->paroviKarakteraKojeMenjam != NULL){
        for(int i=0; i<kz->brojParovaKarakteraKojeMenjam; i++){
            free(kz->paroviKarakteraKojeMenjam[i]);
        }
        free(kz->paroviKarakteraKojeMenjam);
    }
}

bool obrisiKaraktereZameneIStaviNaNull(KarakteriZamene **adresaPointeraKZ){
    KarakteriZamene *kz = *adresaPointeraKZ;
    obrisiKaraktereZamene(kz);
    *adresaPointeraKZ = NULL;
}


KarakteriZamene* KarakteriZamene_new(String *karakteriZaSifru, int brojKojiDodajemNaASCI){
    KarakteriZamene *karakteriZamene = (KarakteriZamene*) malloc(sizeof(KarakteriZamene));

    karakteriZamene->karakteriZaSifru = kopirajString(karakteriZaSifru);

    karakteriZamene->paroviKarakteraKojeMenjam = NULL;
    karakteriZamene->brojParovaKarakteraKojeMenjam = 0;

    return karakteriZamene;
}

void dodajParKarakteraZaMenjanje(KarakteriZamene* karakteriZamene, char karakterZaIzmenu, char karakterUKojiSeMenja){
    if(karakteriZamene->paroviKarakteraKojeMenjam == NULL){
        karakteriZamene->paroviKarakteraKojeMenjam = (char**) malloc(sizeof(char*));
    } else {
        karakteriZamene->paroviKarakteraKojeMenjam = (char**) realloc(karakteriZamene->paroviKarakteraKojeMenjam,
                                                                      karakteriZamene->brojParovaKarakteraKojeMenjam * sizeof(char*));
    }
    const int BROJ_PAROVA_KARAKTERA_KOJE_MENJAM = karakteriZamene->brojParovaKarakteraKojeMenjam;
    karakteriZamene->paroviKarakteraKojeMenjam[BROJ_PAROVA_KARAKTERA_KOJE_MENJAM] = (char*) malloc(2 * sizeof(char));
    karakteriZamene->paroviKarakteraKojeMenjam[BROJ_PAROVA_KARAKTERA_KOJE_MENJAM][0] = karakterZaIzmenu;
    karakteriZamene->paroviKarakteraKojeMenjam[BROJ_PAROVA_KARAKTERA_KOJE_MENJAM][1] = karakterUKojiSeMenja;

    karakteriZamene->brojParovaKarakteraKojeMenjam++;
}

void zaSvakiParKarakteraKojiMenjam(KarakteriZamene *karakteriZamene, void* (primeniNaSvakiPar)(char c1, char c2)){
    zaSvakiParKaraktera(primeniNaSvakiPar, karakteriZamene->paroviKarakteraKojeMenjam, karakteriZamene->brojParovaKarakteraKojeMenjam);
}


char karakteriZameneNadjiPoKljucu(KarakteriZamene *karakteriZamene, char kljuc){

    for(int i=0; i<karakteriZamene->brojParovaKarakteraKojeMenjam; i++){
        char* parKaraktera = karakteriZamene->paroviKarakteraKojeMenjam[i];
        if(parKaraktera[0] == kljuc){
            return parKaraktera[1];
        }
    }

    return kljuc;
}

char pretumbajASCIIkarakter(char karakter, int brojZaPovecanje){
    const int POCETAK_ASCII_SLOVA = 65;
    const int KRAJ_ASCII_SLOVA = 122;
    const int BROJ_SLOVA = KRAJ_ASCII_SLOVA - POCETAK_ASCII_SLOVA + 1;

    karakter = karakter - POCETAK_ASCII_SLOVA;

    karakter += brojZaPovecanje;
    karakter = karakter % BROJ_SLOVA;

    karakter += POCETAK_ASCII_SLOVA;
    return karakter;
}

char karakteriZameneDodajASCII(KarakteriZamene *karakteriZamene, char karakter){
    if(stringSadrziKarakter(karakteriZamene->karakteriZaSifru, karakter)){
        return pretumbajASCIIkarakter(karakter, karakteriZamene->brojKojiDodajemNaASCI);
    }
    return karakter;
}


String* izmeniTekst(KarakteriZamene* karakteriZamene, String *tekst){
    String *novTekst = kopirajString(tekst);

    for(int i=0; i<novTekst->brojKaraktera; i++){
            novTekst->karakteri[i] = karakteriZameneDodajASCII(karakteriZamene, novTekst->karakteri[i]);
            novTekst->karakteri[i] = karakteriZameneNadjiPoKljucu(karakteriZamene, novTekst->karakteri[i]);
    }

    return novTekst;
}


#endif // KARAKTERI_ZAMENE

