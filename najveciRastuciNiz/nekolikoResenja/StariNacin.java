import java.util.*;


/*
Zadatak je da se u nizu pronadje najveci rastuci niz. Npr. ako imamo niz {50, 2, 10, 5, 6} najveci rastuci niz je {2, 5, 6}.
Zadatak sam resio tako sto od svakog elementa niza pocinjem grane (liste). Kada obradjujem novi element niza, u metodi koja se zove (ili se zvala
u vreme pisanja ovog komentara) obradiGranu proveravam da li je elementNiza veci od poslednjeg clana grane.
Ako jeste, onda stavljam taj element niza u granu. Ako nije, onda proveravam da li je element niza veci od nekih od prethodnih clanova grane.
Ako jeste, onda pravim novu granu (npr. kad dodjem do broja 5 pravim novu granu {2, 5}, jer je 5 manje od 10, pa ne moze da se nastavi predjasnja grana {2,10}).
Zato iz svakog elementa ne pocinje lista brojeva, nego vise listi (tj. iz svakog elementa pocinje lista ArrayList-i).
Te liste sam nazvao grane, jer ovaj proces podseca na grananje.
*/

public class StariNacin implements NajveciNizInterfejs {

    public static void main(String[] args){

    }




    @Override
    public ArrayList dajNajveciRastuciNiz(int[] niz){
        ArrayList sveGraneSvihElemenata = dajGraneSvihElemenata(niz);
        ArrayList najduzaListaSvakogElementa = dajNajduzeListeIzSvakogElementa(sveGraneSvihElemenata);
        return dajNajduzuGranu(najduzaListaSvakogElementa);
    }



    /*Matematicka indukcija najobicnija, nek radi za n+1 i nek radi za prvi slucaj.
    ,,Grane" su razlicite liste. graneElementa su liste koje pocinju od istog elementa.
    Ova metoda vraca najvecu listu koja pocinje iz svakog elementa. */
    public static ArrayList dajGraneSvihElemenata(int niz[]){
        ArrayList<ArrayList> graneSvihElemenata = new ArrayList<>();

        for(int i=0; i<niz.length; i++){
            final int SLEDECI_ELEMENT_NIZA = niz[i];
            graneSvihElemenata.forEach(
                    graneJednogElementa -> updatujGraneElementa(graneJednogElementa, SLEDECI_ELEMENT_NIZA)
            );
            graneSvihElemenata.add(kreirajGraneNovogElementa(SLEDECI_ELEMENT_NIZA));
        }

        return graneSvihElemenata;
    }


    public static ArrayList kreirajGraneNovogElementa(int elementNiza){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(elementNiza);

        ArrayList graneNovogElementa = new ArrayList();
        graneNovogElementa.add(list);
        return graneNovogElementa;
    }


    public static void updatujGraneElementa(ArrayList graneElementa, int elementNiza){
        ArrayList<ArrayList<Integer>> graneJednogElementa = (ArrayList<ArrayList<Integer>>) graneElementa;
        ArrayList<ArrayList<Integer>> noveGraneElementa = new ArrayList<>();

        graneJednogElementa.forEach(jednaGrana -> obradiGranu(jednaGrana, elementNiza, noveGraneElementa));
        if(noveGraneElementa.size() > 0){
            graneElementa.addAll(noveGraneElementa);
        }
    }

    /* Ova metoda ubacuje novi element na kraj svih grana (listi) jednog elementa, ili,
     ukoliko se element ne moze dodati na kraj liste, onda ova metoda zove
     eventualnoDodajGranu koja pravi novu granu u skladu sa tim gde se novi element uklopi.
     U prosloj verziji je bilo bitno da se graneElementaSize racuna pre petlje, jer kod u petlji menja broj elemenata
     ArrayList graneElementa, tako da ako bi graneElementa.size() pisalo u uslovu petlje, petlja se
     nikad ne bi zavrsila. Ova poslednja recenica je nebitna za ovu verziju programa. */
    public static void obradiGranu(ArrayList<Integer> staraGrana, int elementNiza, ArrayList<ArrayList<Integer>> noveGrane){
        final int POSLEDNJI_INDEKS = staraGrana.size() - 1;

        if(staraGrana.get(POSLEDNJI_INDEKS) < elementNiza){
            staraGrana.add(elementNiza);
        } else {
            ArrayList<Integer> novaGrana = napraviNovuGranu(staraGrana, elementNiza);
            if(novaGrana != null){
                noveGrane.add(novaGrana);
            }
        }
    }

    /* Ova metoda prolazi kroz celokupnu listu i gleda koji element liste je manji od novog elementa u nizu,
  kako bi napravio novu granu kojoj je poslednji clan novi element u nizu.
  +1 za kopiraj, zato sto npr. ako je nulti element manji od elemnta niza, treba da se kopira 1 element
  starog niza. Matematickom indukcijom sledi da je tacno i za sve ostale primere. */
    public static ArrayList<Integer> napraviNovuGranu(ArrayList<Integer> staraGrana, int elementNiza){
        final int POSLEDNJI_INDEKS = staraGrana.size() - 1;

        for(int i=POSLEDNJI_INDEKS; i>=0; i--){
            if(staraGrana.get(i) < elementNiza){
                ArrayList<Integer> novaGrana = kopiraj(staraGrana, i+1);
                novaGrana.add(elementNiza);
                return novaGrana;
            }
        }
        return null;
    }

    /* Vracam kopiju ArrayList-a do odredjenog elementa. */
    public static ArrayList kopiraj(ArrayList lista, int kolikoElemenataKopiram){
        if (lista == null) return null;

        kolikoElemenataKopiram = Math.min(kolikoElemenataKopiram, lista.size());
        ArrayList result = new ArrayList();
        for(int i=0; i<kolikoElemenataKopiram; i++){
            result.add(lista.get(i));
        }

        return result;
    }


    public static ArrayList dajNajduzeListeIzSvakogElementa(ArrayList graneSvihElemenata){
        ArrayList listaNizova = new ArrayList();
        ArrayList<ArrayList> sveGraneSvihElemenata = (ArrayList) graneSvihElemenata;

        sveGraneSvihElemenata.forEach(graneJednogElementa ->
                listaNizova.add(dajNajduzuGranu(graneJednogElementa)));

        return listaNizova;
    }


    public static ArrayList<Integer> dajNajduzuGranu(ArrayList graneElementa){
        final int INDEKS_NAJVECE_GRANE = indeksNajveceListe(graneElementa);
        return (ArrayList<Integer>) graneElementa.get(INDEKS_NAJVECE_GRANE);
    }

    /* Ova funkcija uzima listu nizova (tj. listu ArrayList-i) i vraca indeks najduze liste. */
    public static int indeksNajveceListe(ArrayList listaNizova){
        int max = 0;
        for(int i=1; i<listaNizova.size(); i++){
            ArrayList listaI = (ArrayList) listaNizova.get(i);
            ArrayList listaMax = (ArrayList) listaNizova.get(max);
            if (listaI.size() > listaMax.size()){
                max = i;
            }
        }
        return max;
    }


}
