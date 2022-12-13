


#include"includeFajl.h"

void Interfejs_obrisi(
                    void **adresaPointeraInterfejsa,
                    void *strukturaKojaMeImplementira,
                    void (*obavestiStrukturuKojaImplementiraInterfejs)(void *strukturaKojaMeImplementira, void *interfejs)){


    if(adresaPointeraInterfejsa == NULL){
        printf("Interfejs_obrisi je dobila NULL pointer. \n", stderr);
        return;
    }

    void *strukturaZaBrisanje = *adresaPointeraInterfejsa; /* Citam i pamtim adresu na kojoj se instanca ovog interfejsa nalazi.*/
    if(strukturaZaBrisanje == NULL){
        printf("Interfejs_obrisi je dobila instancu interfejsa koja pokazuje na NULL.\n", stderr);
        return;
    }


    free(strukturaZaBrisanje);
    *adresaPointeraInterfejsa = NULL;
    /* strukturaZaBrisanje je vec uzela/zapamtila memorijsku adresu instance ovog interfejsa,
    tako da ova linija koda ni na koji nacin ne menja adresu koju strukturaZaBrisanje sadrzi. Adresa je sacuvana u toj promenljivoj./*/

    obavestiStrukturuKojaImplementiraInterfejs(strukturaKojaMeImplementira, strukturaZaBrisanje);
}


