#ifndef MOJ_STRING.H
#define MOJ_STRING.H


#include<stdbool.h>

typedef struct StringStruct{
    char* karakteri;
    int brojKaraktera;
    bool karakteriSuNaHeapu;
} String;


bool obrisiString(String* string);

bool obrisiStringIstaviGaNaNull(String **adresaDoStringPointera);

String* String_new(char* karakteri, int maksBrojKaraktera);

String* kopirajString(String *string);

String *concatString(String *prviString, String *drugiString);

void concatStringIUnistiPrviString(String **adresaPointeraPrvogStringa, String *drugiString);

bool stringSadrziKarakter(String *string, char karakter);

void zaSvakiParKaraktera(void* (*uradiNestoParuKaraktera)(char karakter1, char karakter2), char** paroviKaraktera, int brojParova);

void* print2karaktera(char c1, char c2);

void previseKaraktera(int maksBrojKaraktera, const char* karakteri);



#endif // MOJ_STRING
