#include"includeFajl.h"

#include"StrukturaNebitno.h"
#include"mojString.h"
#include"KarakteriZamene.h"
#include"PodaciOFajlu.h"
#include"Zivotinja.h"
#include"Pas.h"


void ispisiDaLiJeNull(void *pointer, const char *imeOnogaStoJeMozdaNull){
    if(pointer == NULL){
        printf("%s je NULL \n", imeOnogaStoJeMozdaNull);
    } else {
        printf("%s nije NULL \n", imeOnogaStoJeMozdaNull);
    }
}



bool testStruct();
bool testString();
void testIzmeniTekst();
void testKarakteriZamene();
void testCitanjePisanjeFajla();
void citajObradiUpisiUFajl();
void testirajZivotinjePse();
void testirajPolimorfizamPsaZivotinje();

int main()
{
   // testString();
    //testStruct();
    //testCitanjeFajla();
    //testKarakteriZamene();
   // testIzmeniTekst();
   //testCitanjePisanjeFajla();
  // testirajZivotinjePse();
    testirajPolimorfizamPsaZivotinje();
    return 0;
}

/*
Sta je kljucno da bi radio polimorfizam? Ja sam u fajlu za pse mogao za pseci VTable da se metoda zove
ispisiPodatkeOPsu umesto ispisiPodatkeOZivotinji i sledeca procedura bi i dalje radila (procedura testirajZivotinjePse() ne bi,
jer u toj proceduri nad pointerom tipa Pas* pozivam pas->vTable->ispisiPodatkeOZivotinji, tako da bi kompajler tu prijavio gresku,
jer bi kompajler ocekivao da se metoda zove ispisiPodatkePoPsu.
Medjutim, u ovoj metodi testirajPolimorfizamPsaZivotinje pozivam ->vTable->ispisiPodatkeOZivotinji nad pointerom
tipa Zivotinja*, tako da ce kompajler svakako ocekivati da se metoda zove ispisiPodatkeOZivotinji, cak iako u fajl Pas.c
stavim da se metoda iz vTable zove ispisiPodatkeOPsu.
Ok, ako budem citao ovaj tekst u buducnosti i zapitam se ,,Pa sta je ono sto cini da ovaj kod radi, zasto radi?".
Kod radi zato sto je vTable prvo polje strukture Zivotinja i prvo polje strukture Pas.
Kada napisem Zivotinja *zivotinja_koja_je_zapravo_pas = Pas_new_sve(itd.);
zivotinja_koja_je_zapravo_pas->vTable
 ja zapravo idem na prvo polje strukture, kompjuter cita prvih 8 bajtova (jer je ovo 64bitni racunar, to je nebitno, bilo bi 4 bajta da je 32bitni racunar),
te ide na memorijsku adresu koja je sacuvana u tih prvih 8 (ili 4, nebitno je) bajtova strukture.
Tako da kada napisem zivotinja_koja_je_zapravo_pas->vTable kompjuter dobije strukturu psa, uzme prvih 8 bajtova i ode
na tu adresu (adresu na koju pokazuju tih 8 bajtova, u tih 8 bajtova je zapisana adresa).
Na toj adresi ce naci Vtable. U ovom slucaju naci ce vTable psa.
Zasto radi sledeci deo poziva? vTable->ispisiPodatkeOZivotinji(zivotinja_koja_je_zapravo_pas);
radi zato sto je ispisiPodatkeOZivotinji prva metoda vTable i kod Zivotinje i kod Psa.
Kompajler misli da radi sa vTable-om zivotinje iako zapravo radi sa vTable-om psa. Prevara je uspesna zato sto
kada kompajler procita ispisiPodatkeOZivotinji on ide na prvu metodu sa vTable adrese
 (tj. u instruction pointer ide prvih 8 bajtova koji se nalaze na vTable adresi).
 Ja sam mogao metodu u fajlu Pas.c da nazovem i ispisiPodatkeOPsu, prevara bi i dalje bila uspesna dokle god je metoda
 ispisiPodatkeOZivotinji prva metoda vTable.
 Zato metode vTable zivotinje i psa moraju da budu istim redosledom upisane, a metode koje poseduje pas, a zivotinja ne
 poseduje moraju da budu na kraju vTable, kao sto sam to ja i uradio.

 Kada napisem zivotinja_koja_je_zapravo_pas->vTable->ispisiPodatkeOZivotinji(zivotinja_koja_je_zapravo_pas);
kompjuter uzima prvih 8 bajtova sa adrese na koju pokazuje pas->vTable i stavlja ih u instruction pointer, tj poziva callq
u asembliju.

Kada napisem zivotinja_koja_je_zapravo_pas->vTable->dajGodine(zivotinja_koja_je_zapravo_pas);
kompjuter uzima drugih 8 bajtova sa adrese na koju pokazuje pas->vTable i stavlja ih u instruction pointer.
*/
void testirajPolimorfizamPsaZivotinje(){
    Zivotinja *zivotinja = Zivotinja_new(10);
    zivotinja->vTable->ispisiPodatkeOZivotinji(zivotinja);

    Zivotinja *pas = Pas_new_sve(5, 50, "Milojko");
    pas->vTable->ispisiPodatkeOZivotinji(pas); /*Kompajler zove prvu metodu vTable-a, koja je u ovom slucaju procedura iz Pas.c fajla. Kompjuter uzima prvih 8 bajtova sa adrese na koju pokazuje pas->vTable i stavlja ih u instruction pointer. */
    int godine = pas->vTable->dajGodine(pas); /* Kompajler zove drugu metodu vTable, uzima drugih 8 bajtova sa adrese pas->vTable i stavlja ih u instruction pointer.*/
    printf("godine psa koji je kastovan da bude tip zivotinja = %d \n", godine);
}

void testirajZivotinjePse(){
    Zivotinja *zivotinja = Zivotinja_new(10);
    Pas *pas = Pas_new_sve(5, 50, "Milojko");

    zivotinja->vTable->ispisiPodatkeOZivotinji(zivotinja);
    pas->vTable->ispisiPodatkeOZivotinji(pas);

}

void citajObradiUpisiUFajl(
                         String* (*procitajFajl)(void* strukturaNadKojomOperisem),
                         void *strukturaZaFajl,

                         String* (*obradiTekst)(String* tekst, void* strukturaZaObraduTeksta),
                         void *strukturaZaObraduTeksta,

                         void (*upisiUFajl)(String *tekstFajla, void *strukturaNadKojomOperisem),
                         void *strukturaZaUpisujuciFajl){


        String *tekstFajla = procitajFajl(strukturaZaFajl);
        String *izmenjenTekst = obradiTekst(strukturaZaObraduTeksta, tekstFajla);
        upisiUFajl(izmenjenTekst, strukturaZaUpisujuciFajl);

        obrisiStringIstaviGaNaNull(&tekstFajla);
        obrisiStringIstaviGaNaNull(&izmenjenTekst);
}


void testCitanjePisanjeFajla(){
    PodaciOFajlu *podaciOCitajucemFajlu = PodaciOFajlu_new(150, "prviFajl", "txt");
    PodaciOFajlu *podaciOIzlaznomFajlu = PodaciOFajlu_new(200, "prviFajlProbaIzmene", "txt");

    String *karakteriZaSifrovanje = String_new("abc", 4);
    KarakteriZamene *karakteriZamene = KarakteriZamene_new(karakteriZaSifrovanje, 10);

    dodajParKarakteraZaMenjanje(karakteriZamene, 'S', 'M');
    dodajParKarakteraZaMenjanje(karakteriZamene, 'k', 'K');

    citajObradiUpisiUFajl(citajFajlPrviNacin,
                          podaciOCitajucemFajlu,
                          izmeniTekst,
                          karakteriZamene,
                          upisiUFajlPrviNacin,
                          podaciOIzlaznomFajlu);


}

void testIzmeniTekst(){

    String *karakteriZaSifru = String_new("abc", 4);
    KarakteriZamene *karakteriZamene = KarakteriZamene_new(karakteriZaSifru, 10);

    obrisiStringIstaviGaNaNull(&karakteriZaSifru);

    dodajParKarakteraZaMenjanje(karakteriZamene, 'r', 'R');
    dodajParKarakteraZaMenjanje(karakteriZamene, 'o', 'O');

    String *tekst = String_new("Neki tekst. Videcemo kako ce da zameni, da li ce rano program prekinuti da radi.", 100);
    String *izmenjenTekst = izmeniTekst(karakteriZamene, tekst);
    obrisiStringIstaviGaNaNull(&tekst);

    printf("izmenjen tekst = %s \n", izmenjenTekst->karakteri);

}



void testKarakteriZamene(){
    String *karakteriZaSifru = String_new("abc", 4);
    KarakteriZamene *karakteriZamene = KarakteriZamene_new(karakteriZaSifru, 10);
    obrisiStringIstaviGaNaNull(&karakteriZaSifru);

    dodajParKarakteraZaMenjanje(karakteriZamene, 'r', 'R');
    dodajParKarakteraZaMenjanje(karakteriZamene, 'o', 'O');

    printf("broj karaktera za menjanje = %d \n", karakteriZamene->brojParovaKarakteraKojeMenjam);
    zaSvakiParKarakteraKojiMenjam(karakteriZamene, print2karaktera);
}


void testCitanjeFajla(){
    String *imeFajla = String_new("prviFajl", 10);
    PodaciOFajlu *podaciOFajlu = PodaciOFajlu_new(150, "prviFajl", "txt");
    String *tekstFajla = citajFajlPrviNacin(podaciOFajlu);

    printf("%s \n", tekstFajla->karakteri);

    PodaciOFajlu *fajlZaIspis = PodaciOFajlu_new(200, "prviFajlPreradjen", "txt");
    upisiUFajlPrviNacin(tekstFajla, fajlZaIspis);

   // citajFajlPrviNacin(imeFajla, )

}

bool testStruct(){
    Struktura *struktura = Struktura_new_brojKaraktera("prviFajl.txt", 20);
    printf("fajl = %s \n", struktura->imeFajla->karakteri);

    String *stringIzStrukture = struktura->imeFajla;
    obrisiStrukturuIStaviNaNull(&struktura);

    ispisiDaLiJeNull(struktura, "struktura");
}

bool testString(){
     previseKaraktera(10, "123456789012");
    String *string1 = String_new("Ovo je prvi string.", 20);
    printf("Pokusaj prvog string: %s \n", string1->karakteri);
    String *string2 = String_new("Ovo je drugi string.", 5);
    if(string2 == NULL){
        printf("string2 je null \n");
    }

    String *string3 = String_new("Ovo je treci string", 150);
    concatStringIUnistiPrviString(&string1, string3);
    printf("concat string = %s \nbroj karaktera = %d \n", string1->karakteri, string1->brojKaraktera);

    obrisiStringIstaviGaNaNull(&string1);
    if(string1 == NULL){
        printf("string1 je NULL");
    }

    return true;
}
