#ifndef PODACI_O_FAJLU
#define PODACI_O_FAJLU


#include"includeFajl.h"
#include"mojString.h"


long minimum(long a, long b);


typedef struct PodaciOFajluStruct{
    unsigned long maksimalanBrojKaraktera;
    String *imeFajla;
    String *ekstenzija;
} PodaciOFajlu;

PodaciOFajlu* PodaciOFajlu_new(int maksimalanBrojKaraktera, char* imeFajla50karakteraMax, char* ekstenzija10slovaMax);

bool obrisiPodatkeOFajlu(PodaciOFajlu *p);

String *celoImeFajla(PodaciOFajlu *podaciOFajlu);

String* citajFajlPrviNacin(PodaciOFajlu *podaciOFajlu);

void upisiUFajlPrviNacin(String *tekstFajla, PodaciOFajlu *podaciOFajlu);


#endif // PODACI_O_FAJLU
