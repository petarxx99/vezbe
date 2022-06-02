
import java.util.*;

/*
Od svakog elementa pravim grane (liste), na kraju samo gledam koja je najduza.
,,Grane" su razlicite liste. graneElementa su liste koje pocinju od istog elementa. 
*/

public class NajveciRastuciNiz {
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


/*
Moze i drugacije, ali ovako imam i element od koga pocinje najveci rastuci niz.
indeksNajduzeListe promenljiva cuva indeks od koga pocinje najveci rastuci niz.
*/    
    public static ArrayList dajNajveciRastuciNiz(int[] niz){
        ArrayList sveGrane = kreirajSveGraneSvihElemenata(niz);
        ArrayList najduzaListaSvakogElementa = dajNajduzuListuSvakogElementa(sveGrane);
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



/*
Najbitniji korak je ovde. Za svaki novi element niza, kreiram listu grana koja odatle pocinje,
zatim proveravam da li ovaj novi element niza formira nove grane koje pocinju od proslih elemenata niza.
*/

    public static ArrayList kreirajSveGraneSvihElemenata(int niz[]){
        ArrayList graneSvihElemenata = new ArrayList();
               
        for(int i=0; i<niz.length; i++){
            updatujSveGrane(graneSvihElemenata, niz, i);
            ArrayList graneNovogElementa = kreirajGraneNovogElementa(niz, i);
            graneSvihElemenata.add(graneNovogElementa);
        }

        return graneSvihElemenata;
    }

/* Novi element u nizu ima svoje grane, prva grana pocinje tim elementom. */
public static ArrayList kreirajGraneNovogElementa(int[] niz, int indeksElementaUNizu){
            ArrayList<Integer> list = new ArrayList<>();
            list.add(niz[indeksElementaUNizu]);
            
            ArrayList graneNovogElementa = new ArrayList();
            graneNovogElementa.add(list);
            return graneNovogElementa;
}


/* 
Dodajem nove grane svakom elementu ukoliko novi element niza povecava rastuci niz neke od grana. 
Veoma je bitno da ide addAll, a ne add, jer zelim da dodam sve nove grane listi graneElementa.
Ako napisem samo add, onda ce se referenca na noveGraneElementa dodati kao jedan novi element listi graneElementa,
umesto da se reference na sve grane iz noveGraneElementa dodaju u listu graneElementa. */
    public static void updatujSveGrane(ArrayList graneSvihElemenata, int[] niz, int indeksElementaUNizu){
        for(int i=0; i<graneSvihElemenata.size(); i++){
            ArrayList graneElementa = (ArrayList) graneSvihElemenata.get(i);
            ArrayList noveGraneElementa = stvoriNoveGraneElementa(graneElementa, niz, indeksElementaUNizu);
            graneElementa.addAll(noveGraneElementa);
        }
    }

    

/* Ova metoda pravi novu granu u skladu sa tim gde se novi element uklopi.
 U prethodnoj verziji je bilo bitno da se sizeGraneElementa racuna pre petlje, jer kod u petlji menja broj elemenata
 ArrayList graneElementa, tako da ako bi graneElementa.size() pisalo u uslovu petlje, petlja se 
 nikad ne bi zavrsila. 
 U ovoj verziji se ne menja graneElementa u ovoj metodi, tako da ova metoda sme da koristi
 graneElementa.size() kao uslov u petlji. Treba biti veoma oprezan ako se ovaj kod menja,
 jer ako se nesto radi s graneElementa, onda se ne sme koristiti graneElementa.size() u petlji!!!!*/
    public static ArrayList stvoriNoveGraneElementa(ArrayList graneElementa, int[] niz, int indeksElementa){
        ArrayList noveGraneOvogElementa = new ArrayList();

        for(int i=0; i<graneElementa.size(); i++){
            ArrayList<Integer> staraLista = (ArrayList<Integer>) graneElementa.get(i);
            ArrayList<Integer> novaGrana = eventualnoKreirajGranu(graneElementa, staraLista, niz, indeksElementa);
            noveGraneOvogElementa = dodajGranuAkoNijeNull(noveGraneOvogElementa, novaGrana);  
        }
        return noveGraneOvogElementa;
    }


    /* Ova metoda prolazi kroz celokupnu listu i gleda koji element liste je manji od novog elementa u nizu,
kako bi napravio novu granu kojoj je poslednji clan novi element u nizu. */
    public static ArrayList<Integer> eventualnoKreirajGranu(ArrayList graneElementa, ArrayList<Integer> staraLista, int[] niz, int indeksElementa){
        int poslednjiIndeks = staraLista.size() - 1;
        for(int i = poslednjiIndeks; i>=0; i--){
            if(staraLista.get(i) < niz[indeksElementa]){
                ArrayList<Integer> novaLista = kopiraj(staraLista, i+1);
                novaLista.add(niz[indeksElementa]);
                return novaLista;
            }
        }

        return null;
    }


    public static ArrayList dodajGranuAkoNijeNull(ArrayList noveGraneOvogElementa, ArrayList novaGrana){
        if(novaGrana != null){
            noveGraneOvogElementa.add(novaGrana);
        }
        return noveGraneOvogElementa;
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

    public static ArrayList dajNajduzuListuSvakogElementa(ArrayList graneSvihElemenata){
        ArrayList listaGrana = new ArrayList();
        for(int i=0; i<graneSvihElemenata.size(); i++){
            ArrayList graneElementa =  (ArrayList) graneSvihElemenata.get(i);
            int indeksNajveceGrane = indeksNajveceListe(graneElementa);
            listaGrana.add(graneElementa.get(indeksNajveceGrane));
        }
        return listaGrana;
    }
}
