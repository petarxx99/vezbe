
#ifndef STRUKTURA_NEBITNO
#define STRUKTURA_NEBITNO


#include"mojString.h"

typedef struct strukturaStruct {
    String *imeFajla;
    int brojCitanja;
} Struktura;

Struktura* Struktura_new(String *imeFajla);


Struktura* Struktura_new_brojKaraktera(const char *imeFajla, int maksBrojKaraktera);

bool obrisiStrukturu(Struktura *struktura);

bool obrisiStrukturuIStaviNaNull(Struktura **adresaPointeraStrukture);


#endif // STRUKTURA_NEBITNO
