
import java.util.*;

public class Mainclass {
    public static void main(String[] args){
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pokrecem program. Koliko brojeva zelite da upisete. Write how many numbers your array will have: ");
        int duzinaNiza = scanner.nextInt();
        System.out.printf("Upisite %d brojeva. Write your array: ", duzinaNiza);
        int[] niz = preuzmiNiz(duzinaNiza, scanner);
        System.out.println("Upisali ste brojeve. ");

        ArrayList najveciRastuciNiz = dajNajveciRastuciNiz(niz);

        System.out.println("Broj elemenata = " + najveciRastuciNiz.size());
        System.out.println("Najveci rastuci niz (the biggest growing array within the array): " + najveciRastuciNiz);
    }

    public static int[] preuzmiNiz(int duzinaNiza, Scanner scanner){
        int[] niz = new int[duzinaNiza];
        for(int i=0; i<duzinaNiza; i++){
            niz[i] = scanner.nextInt();
        }
        return niz;
    }


    
    public static ArrayList dajNajveciRastuciNiz(int[] niz){
    	ArrayList sveGraneSvihElemenata = dajGraneSvihElemenata(niz);
        ArrayList najduzaListaSvakogElementa = dajNajduzeListeIzSvakogElementa(sveGraneSvihElemenata);
        int indeksNajduzeListe = indeksNajveceListe(najduzaListaSvakogElementa);
        ArrayList<Integer> najvecaLista = (ArrayList<Integer>) najduzaListaSvakogElementa.get(indeksNajduzeListe);
        return najvecaLista;
    }



/*Matematicka indukcija najobicnija, nek radi za n+1 i nek radi za prvi slucaj. 
,,Grane" su razlicite liste. graneElementa su liste koje pocinju od istog elementa.
Ova metoda vraca najvecu listu koja pocinje iz svakog elementa. */
    public static ArrayList dajGraneSvihElemenata(int niz[]){
        ArrayList<ArrayList> graneSvihElemenata = new ArrayList<ArrayList>();
               
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
        ArrayList<ArrayList> noveGraneElementa = new ArrayList<>();

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
    public static void obradiGranu(ArrayList<Integer> staraGrana, int elementNiza, ArrayList noveGrane){
        final int POSLEDNJI_INDEKS = staraGrana.size() - 1;

        if(staraGrana.get(POSLEDNJI_INDEKS) < elementNiza){
            staraGrana.add(elementNiza);
        } else {
            napraviNovuGranu(noveGrane, staraGrana, elementNiza);
        }
    }

/* Ova metoda prolazi kroz celokupnu listu i gleda koji element liste je manji od novog elementa u nizu,
kako bi napravio novu granu kojoj je poslednji clan novi element u nizu.
+1 za kopiraj, zato sto npr. ako je nulti element manji od elemnta niza, treba da se kopira 1 element
starog niza. Matematickom indukcijom sledi da je tacno i za sve ostale primere. */
    public static void napraviNovuGranu(ArrayList graneNaKojeDodajem, ArrayList<Integer> staraGrana, int elementNiza){
        int poslednjiIndeks = staraGrana.size() - 1;
        for(int i = poslednjiIndeks; i>=0; i--){
            if(staraGrana.get(i) < elementNiza){
                ArrayList<Integer> novaGrana = kopiraj(staraGrana, i+1);
                novaGrana.add(elementNiza);
                graneNaKojeDodajem.add(novaGrana);
                break;
            }
        }
    }

/* Vracam kopiju ArrayList-a do odredjenog elementa. */
    public static ArrayList kopiraj(ArrayList l, int kolikoElemenataKopiram){
        if (l == null) return null;

        kolikoElemenataKopiram = Math.min(kolikoElemenataKopiram, l.size());
        ArrayList result = new ArrayList();
        for(int i=0; i<kolikoElemenataKopiram; i++){
            result.add(l.get(i));
        }

        return result;
    }


     public static ArrayList dajNajduzeListeIzSvakogElementa(ArrayList graneSvihElemenata){
        ArrayList listaNizova = new ArrayList();
        ArrayList<ArrayList> sveGraneSvihElemenata = (ArrayList) graneSvihElemenata;
        
        sveGraneSvihElemenata.forEach(graneJednogElementa -> 
                        listaNizova.add(dajNajduzuGranuElementa(graneJednogElementa)));

        return listaNizova;
    }


    public static ArrayList<Integer> dajNajduzuGranuElementa(ArrayList graneElementa){
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
