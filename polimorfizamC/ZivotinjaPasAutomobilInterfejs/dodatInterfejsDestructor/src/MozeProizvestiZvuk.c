
#include"MozeProizvestiZvuk.h"
#include"includeFajl.h"

typedef void (*DupliSupplier)(void *strukturaKojaMeImplementira, void *interfejsZaBrisanje);

void MozeProizvestiZvuk_obrisi(MozeProizvestiZvuk **adresaPointeraMozeProizvestiZvuk){


    if(adresaPointeraMozeProizvestiZvuk == NULL){
        printf("MozeProizvestiZvuk_obrisi je dobila NULL pointer. \n", stderr);
        return;
    }

    MozeProizvestiZvuk *strukturaZaBrisanje = *adresaPointeraMozeProizvestiZvuk; /* Citam i pamtim adresu na kojoj se instanca ovog interfejsa nalazi.*/
    if(strukturaZaBrisanje == NULL){
        printf("MozeProizvestiZvuk_obrisi je dobila instancu interfejsa koja pokazuje na NULL.\n", stderr);
        return;
    }

    void *strukturaKojaMeImplementira = strukturaZaBrisanje->strukturaKojuMetodeInterfejsaKoriste;
    DupliSupplier primiPorukuDaJeInterfejsIzbrisan = strukturaZaBrisanje->primiPorukuDaJeInstancaInterfejsaObrisana;

    free(strukturaZaBrisanje);
    *adresaPointeraMozeProizvestiZvuk = NULL;
    /* strukturaZaBrisanje je vec uzela/zapamtila memorijsku adresu instance ovog interfejsa,
    tako da ova linija koda ni na koji nacin ne menja adresu koju strukturaZaBrisanje sadrzi. Adresa je sacuvana u toj promenljivoj./*/

    primiPorukuDaJeInterfejsIzbrisan(strukturaKojaMeImplementira, strukturaZaBrisanje);
}

