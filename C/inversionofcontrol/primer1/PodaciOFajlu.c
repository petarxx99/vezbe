
#include"includeFajl.h"
#include"mojString.h"


long minimum(long a, long b){
    if (a>b) return b;
    return a;
}


typedef struct PodaciOFajluStruct{
    unsigned long maksimalanBrojKaraktera;
    String *imeFajla;
    String *ekstenzija;
} PodaciOFajlu;

PodaciOFajlu* PodaciOFajlu_new(int maksimalanBrojKaraktera, char* imeFajla50karakteraMax, char* ekstenzija10slovaMax){
    PodaciOFajlu *p = (PodaciOFajlu*) malloc(sizeof(PodaciOFajlu));
    p->maksimalanBrojKaraktera = maksimalanBrojKaraktera;
    String *ekstenzija = String_new(ekstenzija10slovaMax, 11);
    p->ekstenzija = ekstenzija;

    String *imeFajla = String_new(imeFajla50karakteraMax, 51);
    p->imeFajla = imeFajla;

    return p;
}

bool obrisiPodatkeOFajlu(PodaciOFajlu *p){
    obrisiStringIstaviGaNaNull(&(p->ekstenzija));
    obrisiStringIstaviGaNaNull(&(p->imeFajla));
    free(p);
    return true;
}

String *celoImeFajla(PodaciOFajlu *podaciOFajlu){
    const int MAKS_BROJ_KARAKTERA = podaciOFajlu->imeFajla->brojKaraktera + 1;
    String *celoImeFajla = String_new(podaciOFajlu->imeFajla->karakteri, MAKS_BROJ_KARAKTERA);

    String *tacka = String_new(".", 2);
    concatStringIUnistiPrviString(&celoImeFajla, tacka);
    concatStringIUnistiPrviString(&celoImeFajla, podaciOFajlu->ekstenzija);

    obrisiStringIstaviGaNaNull(&tacka);
    return celoImeFajla;
}


String* citajFajlPrviNacin(PodaciOFajlu *podaciOFajlu){
    String *nazivFajla = celoImeFajla(podaciOFajlu);

    FILE *file = fopen(nazivFajla->karakteri, "r"); //Otvaram fajl stream.

    unsigned long brojOcitanihKaraktera = 0;
    const int BROJ_KARAKTERA_KOJE_CITAM_U_JEDNOM_CUGU = 10;
    const int VELICINA_JEDNOG_KARAKTERA = 1;
    int brojCitanja = 0;
    int brojIspravnoOcitanihKaraktera = 0;

    char *karakteriFajla = (char*) malloc(1);

    do {
        karakteriFajla = realloc(karakteriFajla, (brojCitanja+1) * BROJ_KARAKTERA_KOJE_CITAM_U_JEDNOM_CUGU);
        brojIspravnoOcitanihKaraktera = fread(karakteriFajla + brojOcitanihKaraktera, VELICINA_JEDNOG_KARAKTERA, BROJ_KARAKTERA_KOJE_CITAM_U_JEDNOM_CUGU, file);
        brojOcitanihKaraktera += brojIspravnoOcitanihKaraktera;
        brojCitanja++;
    } while(brojIspravnoOcitanihKaraktera == BROJ_KARAKTERA_KOJE_CITAM_U_JEDNOM_CUGU &&
        brojOcitanihKaraktera <= podaciOFajlu->maksimalanBrojKaraktera);

    //Ukoliko sam procitao vise karaktera nego sto je trebalo, samo stavljam NULL onde gde je trebalo da bude kraj.
    if(brojOcitanihKaraktera > podaciOFajlu->maksimalanBrojKaraktera){
        karakteriFajla[podaciOFajlu->maksimalanBrojKaraktera] = NULL;
    }

    // Za slucaj da sam bas uzeo onoliko memorije koliko mi treba za karaktere onda mi treba memorije i za NULL karakter.
    if(brojOcitanihKaraktera % BROJ_KARAKTERA_KOJE_CITAM_U_JEDNOM_CUGU == 0){
        karakteriFajla = (char*) realloc(karakteriFajla, brojOcitanihKaraktera+1);
    }

    karakteriFajla[brojOcitanihKaraktera] = NULL; //  String se mora zavrsiti sa NULL.
    fclose(file); // Zatvaram fajl.
    obrisiStringIstaviGaNaNull(&nazivFajla);

    const int VECI_BROJ_OD_BROJA_KARAKTERA = brojOcitanihKaraktera + 50;
    return String_new(karakteriFajla, VECI_BROJ_OD_BROJA_KARAKTERA);
}

void upisiUFajlPrviNacin(String *tekstFajla, PodaciOFajlu *podaciOFajlu){
    const int BROJ_KARAKTERA_ZA_UPIS_U_FAJL = minimum(tekstFajla->brojKaraktera, podaciOFajlu->maksimalanBrojKaraktera);
    String *ceoNazivFajla = celoImeFajla(podaciOFajlu);

    FILE *file = fopen(ceoNazivFajla->karakteri, "w+");
    fwrite(tekstFajla->karakteri, BROJ_BAJTOVA_JEDNOG_KARAKTERA, BROJ_KARAKTERA_ZA_UPIS_U_FAJL, file);
    fclose(file);

    obrisiStringIstaviGaNaNull(&ceoNazivFajla);
}

