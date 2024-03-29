package glavnipaket;

import java.util.*;
public class BrziNacinZaNRN implements NajveciNizInterfejs {

    @Override
    public ArrayList<Integer> dajNajveciRastuciNiz(int[] niz){
        ArrayList<ArrayList<Integer>> sviRastuciNizovi = nadjiSveRastuceNizove(niz);
        return pronadjiNajduzuListu(sviRastuciNizovi);
    }

    public static ArrayList<ArrayList<Integer>> nadjiSveRastuceNizove(int[] niz){
        ArrayList<ArrayList<Integer>> rastuciNizovi = new ArrayList<>();

        for(int i=0; i<niz.length; i++){
            final int ELEMENT_NIZA = niz[i];
            rastuciNizovi.add(napraviListu(ELEMENT_NIZA));
            ArrayList<ArrayList<Integer>> noveListe = updejtujStareINapraviNoveListe(rastuciNizovi, ELEMENT_NIZA);
            rastuciNizovi.addAll(noveListe);
        }

        return rastuciNizovi;
    }

    public static ArrayList<ArrayList<Integer>> updejtujStareINapraviNoveListe(ArrayList<ArrayList<Integer>> liste, int noviElement){
        ArrayList<ArrayList<Integer>> noveListe = new ArrayList<>();
        liste.forEach(rastucaLista -> povecajRastucuListuIliNapraviNovu(rastucaLista, noveListe, noviElement));

        return noveListe;
    }

    public static void povecajRastucuListuIliNapraviNovu(ArrayList<Integer> lista, ArrayList<ArrayList<Integer>> noveListe, int noviElement){
        final int POSLEDNJI_INDEKS = lista.size() - 1;

        if(lista.get(POSLEDNJI_INDEKS) < noviElement){
            lista.add(noviElement);
        } else {
            ArrayList<Integer> novaLista = napraviNovuListu(lista, noviElement);
            if(novaLista != null){
                noveListe.add(novaLista);
            }
        }
    }

    public static ArrayList<Integer> napraviNovuListu(ArrayList<Integer> staraLista, int noviElement){
        final int POSLEDNJI_INDEKS = staraLista.size() -1 ;

        for(int i=POSLEDNJI_INDEKS; i>=0; i--){
            if(staraLista.get(i) < noviElement){
                final int BROJ_ELEMENATA_KOJE_KOPIRAM = i+1;
                ArrayList<Integer> novaLista = kopirajListu(staraLista, BROJ_ELEMENATA_KOJE_KOPIRAM);
                novaLista.add(noviElement);
                return novaLista;
            }
        }

        return null;
    }

    public static <T> ArrayList<T> kopirajListu(ArrayList<T> lista, int kolikoElemenataKopiram){
        if(lista == null) return null;
        kolikoElemenataKopiram = Math.min(kolikoElemenataKopiram, lista.size());

        ArrayList<T> novaLista = new ArrayList<>();
        for(int i=0; i<kolikoElemenataKopiram; i++){
            novaLista.add(lista.get(i));
        }

        return novaLista;
    }

    public static ArrayList<Integer> napraviListu(int broj){
        ArrayList<Integer> lista = new ArrayList<>();
        lista.add(broj);
        return lista;
    }


    public static <T> ArrayList<T> pronadjiNajduzuListu(ArrayList<ArrayList<T>> liste){
        if(liste == null) return null;

        ArrayList<T> maxLista = new ArrayList<>();
        for(ArrayList<T> lista : liste){
            if(lista.size() > maxLista.size()){
                maxLista = lista;
            }
        }

        return maxLista;
    }
}
