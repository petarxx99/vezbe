
#include"includeFajl.h"
#include"Zivotinja.h"
#include"Pas.h"


void testirajPolimorfizamPsaZivotinje();

int main()
{
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
Na toj adresi ce naci Vtable. U ovom slucaju naci ce vTable psa, jer prvih 8 bajtova strukture
zivotinja_koja_je_zapravo_pas cuva adresu vTable psa.
Zasto radi sledeci deo poziva? vTable->ispisiPodatkeOZivotinji(zivotinja_koja_je_zapravo_pas);
radi zato sto je ispisiPodatkeOZivotinji druga metoda vTable i kod Zivotinje i kod Psa (ovo ce se mozda promeniti ako dodam
jos metoda, ali poenta je da je metoda ispisiPodatkeOZivotinji uvek na istom mestu i kod vTable psa i kod vTable zivotinje.
Ako je 2. metoda u vTable-u zivotinje onda mora da bude 2. metoda i u vTable-u psa).
Kompajler misli da radi sa vTable-om zivotinje iako zapravo radi sa vTable-om psa. Prevara je uspesna zato sto
kada kompajler procita ispisiPodatkeOZivotinji on ide na drugu metodu sa vTable adrese
 (tj. u instruction pointer ide drugih 8 bajtova koji se nalaze na vTable adresi).
 Ja sam mogao metodu u fajlu Pas.c da nazovem i ispisiPodatkeOPsu, prevara bi i dalje bila uspesna dokle god je metoda
 ispisiPodatkeOZivotinji druga metoda vTable.
 Zato metode vTable zivotinje i psa moraju da budu istim redosledom upisane, a metode koje poseduje pas, a zivotinja ne
 poseduje moraju da budu na kraju vTable, kao sto sam to ja i uradio.

 Kada napisem zivotinja_koja_je_zapravo_pas->vTable->ispisiPodatkeOZivotinji(zivotinja_koja_je_zapravo_pas);
kompjuter uzima drugih 8 bajtova sa adrese na koju pokazuje pas->vTable i stavlja ih u instruction pointer, tj poziva callq
u asembliju.

Kada napisem zivotinja_koja_je_zapravo_pas->vTable->dajGodine(zivotinja_koja_je_zapravo_pas);
kompjuter uzima trecih 8 bajtova sa adrese na koju pokazuje pas->vTable i stavlja ih u instruction pointer.
*/
void testirajPolimorfizamPsaZivotinje(){
    Zivotinja *zivotinja = Zivotinja_new(10);
    zivotinja->vTable->ispisiPodatkeOZivotinji(zivotinja);
    zivotinja->vTable->obrisi(&zivotinja);

    Zivotinja *pas = Pas_new_sve(5, 50, "Milojko");
    pas->vTable->ispisiPodatkeOZivotinji(pas); /*Kompajler zove prvu metodu vTable-a, koja je u ovom slucaju procedura iz Pas.c fajla. Kompjuter uzima drugih 8 bajtova sa adrese na koju pokazuje pas->vTable i stavlja ih u instruction pointer. */
    int godine = pas->vTable->dajGodine(pas); /* Kompajler zove drugu metodu vTable, uzima trecih 8 bajtova sa adrese pas->vTable i stavlja ih u instruction pointer.*/
    printf("godine psa koji je kastovan da bude tip zivotinja = %d \n", godine);
    pas->vTable->obrisi(&pas);
}
