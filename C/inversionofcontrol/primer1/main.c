#include"includeFajl.h"

#include"StrukturaNebitno.h"
#include"mojString.h"
#include"KarakteriZamene.h"
#include"PodaciOFajlu.h"

void testCitanjePisanjeFajla();

int main(){
  testCitanjePisanjeFajla();
  return 0;
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
