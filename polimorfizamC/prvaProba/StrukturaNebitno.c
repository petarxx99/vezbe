
#include"includeFajl.h"
#include"mojString.h"



typedef struct strukturaStruct {
    String *imeFajla;
    int brojCitanja;
} Struktura;

Struktura* Struktura_new(String *imeFajla){
    Struktura* struktura = (Struktura*) malloc(sizeof(Struktura));

    struktura->brojCitanja = 0;

    struktura->imeFajla = (String*) malloc(sizeof(String));
    memcpy(struktura->imeFajla, imeFajla, sizeof(String));

    return struktura;
}

Struktura* Struktura_new_brojKaraktera(const char *imeFajla, int maksBrojKaraktera){

    String *imeFajlaString = String_new(imeFajla, maksBrojKaraktera);
    Struktura *struktura = Struktura_new(imeFajlaString);
    obrisiStringIstaviGaNaNull(&imeFajlaString);

    return struktura;
}

bool obrisiStrukturu(Struktura *struktura){
    obrisiStringIstaviGaNaNull(&(struktura->imeFajla));
    free(struktura);
}

bool obrisiStrukturuIStaviNaNull(Struktura **adresaPointeraStrukture){
    Struktura *struktura = *adresaPointeraStrukture;
    obrisiStrukturu(struktura);
    *adresaPointeraStrukture = NULL;
    return true;
}


