
import java.util.*;

public class Mainclass {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pokrecem program. Koliko brojeva zelite da upisete. Write how many numbers your array will have: ");
        int duzinaNiza = scanner.nextInt();
        System.out.printf("Upisite %d brojeva. Write your array: ", duzinaNiza);
        int[] niz = preuzmiNiz(duzinaNiza, scanner);
        System.out.println("Upisali ste brojeve. ");

        ArrayList najveciRastuciNiz = najveciRastuciNiz(niz);

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


    
    public static ArrayList najveciRastuciNiz(int[] niz){
        ArrayList najduzaListaSvakogElementa = najduzeListeIzSvakogElementa(niz);
        int indeksNajduzeListe = indeksNajveceListe(najduzaListaSvakogElementa);
        ArrayList<Integer> najvecaLista = (ArrayList<Integer>) najduzaListaSvakogElementa.get(indeksNajduzeListe);
        return najvecaLista;
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



/*Matematicka indukcija najobicnija, nek radi za n+1 i nek radi za prvi slucaj. 
,,Grane" su razlicite liste. graneElementa su liste koje pocinju od istog elementa.
Ova metoda vraca najvecu listu koja pocinje iz svakog elementa. */
    public static ArrayList najduzeListeIzSvakogElementa(int niz[]){
        ArrayList listaGrana = new ArrayList();
               
        for(int i=0; i<niz.length; i++){
            /* Nek radi za prvi slucaj (a i za n+1) */
            ArrayList<Integer> list = new ArrayList<>();
            list.add(niz[i]);
            
            ArrayList grane = new ArrayList();
            grane.add(list);
            listaGrana.add(grane);

/* Nek radi za n+1, svakom nizu ubacujem novi element ako novi element nastaljva rastuci niz.
-1 zato sto je poslednja grana (lista) dodata u kodu iznad. */
            for(int j=0; j<listaGrana.size()-1; j++){
                ArrayList graneElementa = (ArrayList) listaGrana.get(j);
                updatujGrane(graneElementa, niz, i);
            }
        }

        ArrayList listaNizova = new ArrayList();
        for(int i=0; i<listaGrana.size(); i++){
            ArrayList graneElementa =  (ArrayList) listaGrana.get(i);
            int indeksNajveceGrane = indeksNajveceListe(graneElementa);
            listaNizova.add(graneElementa.get(indeksNajveceGrane));
        }
        return listaNizova;
    }

/* Ova metoda ubacuje novi element na kraj svih grana (listi) jednog elementa, ili,
 ukoliko se element ne moze dodati na kraj liste, onda ova metoda zove
 eventualnoDodajGranu koja pravi novu granu u skladu sa tim gde se novi element uklopi.
 Bitno je da se sizeGraneElementa racuna pre petlje, jer kod u petlji menja broj elemenata
 ArrayList graneElementa, tako da ako bi graneElementa.size() pisalo u uslovu petlje, petlja se 
 nikad ne bi zavrsila. */
    public static void updatujGrane(ArrayList graneElementa, int[] niz, int indeksElementa){
        int sizeGraneElementa = graneElementa.size();
        for(int i=0; i<sizeGraneElementa; i++){
            ArrayList<Integer> staraLista = (ArrayList<Integer>) graneElementa.get(i);
            int poslednjiIndeks = staraLista.size() - 1;
            if (staraLista.get(poslednjiIndeks) < niz[indeksElementa]){
                staraLista.add(niz[indeksElementa]);
            } else {
                eventualnoDodajGranu(graneElementa, staraLista, niz, indeksElementa);
            }
        }
    }

/* Ova metoda prolazi kroz celokupnu listu i gleda koji element liste je manji od novog elementa u nizu,
kako bi napravio novu granu kojoj je poslednji clan novi element u nizu. */
    public static void eventualnoDodajGranu(ArrayList graneElementa, ArrayList<Integer> staraLista, int[] niz, int indeksElementa){
        int poslednjiIndeks = staraLista.size() - 1;
        for(int i = poslednjiIndeks; i>=0; i--){
            if(staraLista.get(i) < niz[indeksElementa]){
                ArrayList<Integer> novaLista = kopiraj(staraLista, i+1);
                novaLista.add(niz[indeksElementa]);
                graneElementa.add(novaLista);
                break;
            }
        }
    }

/* Vracam kopiju ArrayList-a do odredjenog elementa. */
    public static ArrayList kopiraj(ArrayList l, int koliko){
        if (l == null) return null;

        koliko = Math.min(koliko, l.size());
        ArrayList result = new ArrayList();
        for(int i=0; i<koliko; i++){
            result.add(l.get(i));
        }

        return result;
    }
}
