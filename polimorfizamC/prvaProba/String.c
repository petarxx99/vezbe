

#include<stdlib.h>
#include<stdio.h>
#include<stdbool.h>

#define NULL_KARAKTER \0

#define BROJ_BAJTOVA_JEDNOG_KARAKTERA 1

typedef struct StringStruct{
    char* karakteri;
    int brojKaraktera;
    bool karakteriSuNaHeapu;
} String;


bool obrisiString(String* string){
    if(string->karakteriSuNaHeapu){
        free(string->karakteri);
    }

    free(string);
    return true;
}

bool obrisiStringIstaviGaNaNull(String **adresaDoStringPointera){
    String *stringZaBrisanje = *adresaDoStringPointera;
    bool uspesnoObrisano = obrisiString(stringZaBrisanje);
    if(uspesnoObrisano){
        *adresaDoStringPointera = NULL;
    }

    return true;
}


String* String_new(char* karakteri, int maksBrojKaraktera){
    String* string = (String*) malloc(sizeof(String)); //malloc string
    string->brojKaraktera = strnlen(karakteri, maksBrojKaraktera);
    string->karakteriSuNaHeapu = false;

    if(string->brojKaraktera >= maksBrojKaraktera){
        previseKaraktera(maksBrojKaraktera, karakteri);
        obrisiStringIstaviGaNaNull(&string);
        return NULL;
    }

    string->karakteri = karakteri;
    return string;
}

String* kopirajString(String *string){
    String *novString = (String*) malloc(sizeof(String));
    memcpy(novString, string, sizeof(String));

    novString->karakteri = (char*) malloc(novString->brojKaraktera + 1); // +1 da bi se string zavrsio sa NULL.
    memcpy(novString->karakteri, string->karakteri, novString->brojKaraktera + 1);

    novString->karakteriSuNaHeapu = true;
    return novString;
}


String *concatString(String *prviString, String *drugiString){

    int duzinaConcatStringa = prviString->brojKaraktera + drugiString->brojKaraktera;
    char *spojeniKarakteri = (char*) malloc(duzinaConcatStringa + 1); // +1 za NULL karakter na kraju.

    memcpy(spojeniKarakteri, prviString->karakteri, prviString->brojKaraktera);
    memcpy(spojeniKarakteri + prviString->brojKaraktera,  drugiString->karakteri, drugiString->brojKaraktera);
    *(spojeniKarakteri+duzinaConcatStringa) = NULL;

    const unsigned long BROJ_VECI_OD_BROJA_KARAKTERA = duzinaConcatStringa + 1;
    String *spojeniString = String_new(spojeniKarakteri, BROJ_VECI_OD_BROJA_KARAKTERA);
    spojeniString->karakteriSuNaHeapu = true;

    return spojeniString;
}

void concatStringIUnistiPrviString(String **adresaPointeraPrvogStringa, String *drugiString){
    String *prviString = *adresaPointeraPrvogStringa;

    String *spojeniString = concatString(prviString, drugiString);

    obrisiStringIstaviGaNaNull(adresaPointeraPrvogStringa);
    *adresaPointeraPrvogStringa = spojeniString;
}

bool stringSadrziKarakter(String *string, char karakter){
    for(int i=0; i<string->brojKaraktera; i++){
        if(string->karakteri[i] == karakter){
            return true;
        }
    }
    return false;
}


void zaSvakiParKaraktera(void* (*uradiNestoParuKaraktera)(char karakter1, char karakter2), char** paroviKaraktera, int brojParova){
    for(int i=0; i<brojParova; i++){
        char* par = *(paroviKaraktera+i);
        uradiNestoParuKaraktera(par[0], par[1]);
    }
}

void* print2karaktera(char c1, char c2){
    printf("prvi karakter = %c, drugi karakter = %c \n", c1, c2);
    return NULL;
}

void previseKaraktera(int maksBrojKaraktera, const char* karakteri){
    char* substringDozvoljenBrojKaraktera = (char*) malloc(maksBrojKaraktera + 1);
    memcpy(substringDozvoljenBrojKaraktera, karakteri, maksBrojKaraktera);

    printf("Dostignut maksimum broj karaktera %d. Ovo je string u pitanju %s. \n", maksBrojKaraktera, substringDozvoljenBrojKaraktera);
    free(substringDozvoljenBrojKaraktera);
}
