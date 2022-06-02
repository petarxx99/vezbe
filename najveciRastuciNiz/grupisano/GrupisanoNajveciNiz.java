import java.util.*;

/*
Zadatak je da se u nizu pronadje najveci rastuci niz. Npr. ako imamo niz {50, 2, 10, 5, 6} najveci rastuci niz je {2, 5, 6}.
Zadatak sam resio tako sto od svakog elementa niza pocinjem grane (liste). Kada obradjujem novi element niza, u metodi koja se zove (ili se zvala 
u vreme pisanja ovog komentara) updatujGraneElementa proveravam da li je elementNiza veci od poslednjeg clana grane. 
Ako jeste, onda stavljam taj element niza u granu. Ako nije, onda proveravam da li je element niza veci od nekih od prethodnih clanova grane. 
Ako jeste, onda pravim novu granu (npr. kad dodjem do broja 5 pravim novu granu {2, 5}, jer je 5 manje od 10, pa ne moze da se nastavi predjasnja grana).
Zato iz svakog elementa ne pocinje lista, nego vise listi. Te liste sam nazvao grane, jer ovaj proces podseca na grananje.

*/

public class GrupisanoNajveciNiz {
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
Mogao sam da uradim i drugacije, ali sam zeleo da imam u kodu i jasan deo koji racuna element iz koga pocinje najduzi rastuci niz, jer i to bi mogao
da bude zadatak.
*/
    public static ArrayList dajNajveciRastuciNiz(int[] niz){
    	ArrayList sveGraneSvihElemenata = dajGraneSvihElemenata(niz);
        ArrayList najduzaListaSvakogElementa = dajNajduzeListeIzSvakogElementa(sveGraneSvihElemenata);
        int indeksNajduzeListe = indeksNajveceListe(najduzaListaSvakogElementa);
        ArrayList<Integer> najvecaLista = (ArrayList<Integer>) najduzaListaSvakogElementa.get(indeksNajduzeListe);
        return najvecaLista;
    }



/*
Kada obradjujem novi element niza, proveravam da li on proizvodi grananje iz proslih elemenata niza.
Zatim pravim grane iz tog novog elementa niza.
*/
    public static ArrayList dajGraneSvihElemenata(int niz[]){
        ArrayList graneSvihElemenata = new ArrayList();
               
        for(int i=0; i<niz.length; i++){
            updatujGraneSvihElemenata(graneSvihElemenata, niz[i]);
            ArrayList graneNovogElementa = kreirajGraneNovogElementa(niz[i]);
            graneSvihElemenata.add(graneNovogElementa);
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
  
  

    public static void updatujGraneSvihElemenata(ArrayList graneSvihElemenata, int elementNiza){
        for(int i=0; i<graneSvihElemenata.size(); i++){
                ArrayList graneElementa = (ArrayList) graneSvihElemenata.get(i);
                updatujGraneElementa(graneElementa, elementNiza);
        }
    }
  
    
   

/* Ova metoda ubacuje novi element na kraj svih grana (listi) jednog elementa, ili,
 ukoliko se element ne moze dodati na kraj liste, onda ova metoda zove
 eventualnoDodajGranu koja pravi novu granu u skladu sa tim gde se novi element uklopi.
 Bitno je da se graneElementaSize racuna pre petlje, jer kod u petlji menja broj elemenata
 ArrayList graneElementa, tako da ako bi graneElementa.size() pisalo u uslovu petlje, petlja se 
 nikad ne bi zavrsila. */
    public static void updatujGraneElementa(ArrayList graneElementa, int elementNiza){
        int graneElementaSize = graneElementa.size();

        for(int i=0; i<graneElementaSize; i++){
            ArrayList<Integer> staraGrana = (ArrayList<Integer>) graneElementa.get(i);
            int poslednjiIndeks = staraGrana.size() - 1;
            if (staraGrana.get(poslednjiIndeks) < elementNiza){
                staraGrana.add(elementNiza);
            } else {
                eventualnoDodajGranu(graneElementa, staraGrana, elementNiza);
            }
        }
    }

  
/* Ova metoda prolazi kroz celokupnu listu i gleda koji element liste je manji od novog elementa u nizu,
kako bi napravio novu granu kojoj je poslednji clan novi element u nizu.
+1 za kopiraj, zato sto npr. ako je nulti element manji od elemnta niza, treba da se kopira 1 element
starog niza. Matematickom indukcijom sledi da je tacno i za sve ostale primere. */
    public static void eventualnoDodajGranu(ArrayList graneElementa, ArrayList<Integer> staraGrana, int elementNiza){
        int poslednjiIndeks = staraGrana.size() - 1;
        for(int i = poslednjiIndeks; i>=0; i--){
            if(staraGrana.get(i) < elementNiza){
                ArrayList<Integer> novaGrana = kopiraj(staraGrana, i+1);
                novaGrana.add(elementNiza);
                graneElementa.add(novaGrana);
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


     public static ArrayList dajNajduzeListeIzSvakogElementa(ArrayList graneSvihElemenata){
        ArrayList listaNizova = new ArrayList();
        for(int i=0; i<graneSvihElemenata.size(); i++){
            ArrayList graneElementa =  (ArrayList) graneSvihElemenata.get(i);
            int indeksNajveceGrane = indeksNajveceListe(graneElementa);
            listaNizova.add(graneElementa.get(indeksNajveceGrane));
        }
        return listaNizova;
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
