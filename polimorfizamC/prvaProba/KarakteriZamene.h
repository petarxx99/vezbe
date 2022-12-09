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


bool obrisiKaraktereZamene(KarakteriZamene *kz);

bool obrisiKaraktereZameneIStaviNaNull(KarakteriZamene **adresaPointeraKZ);

KarakteriZamene* KarakteriZamene_new(String *karakteriZaSifru, int brojKojiDodajemNaASCI);

void dodajParKarakteraZaMenjanje(KarakteriZamene* karakteriZamene, char karakterZaIzmenu, char karakterUKojiSeMenja);

void zaSvakiParKarakteraKojiMenjam(KarakteriZamene *karakteriZamene, void* (primeniNaSvakiPar)(char c1, char c2));

char karakteriZameneNadjiPoKljucu(KarakteriZamene *karakteriZamene, char kljuc);

char pretumbajASCIIkarakter(char karakter, int brojZaPovecanje);

char karakteriZameneDodajASCII(KarakteriZamene *karakteriZamene, char karakter);


String* izmeniTekst(KarakteriZamene* karakteriZamene, String *tekst);

#endif // KARAKTERI_ZAMENE


