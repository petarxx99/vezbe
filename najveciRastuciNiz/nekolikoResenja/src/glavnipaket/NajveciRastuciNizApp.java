package glavnipaket;

import java.util.ArrayList;
import java.util.Scanner;

public class NajveciRastuciNizApp {
    private final NajveciNizInterfejs longestGrowingArrayCalculator;

    public NajveciRastuciNizApp(NajveciNizInterfejs longestGrowingArrayCalculator){
        this.longestGrowingArrayCalculator = longestGrowingArrayCalculator;
    }

    public void startApp(){
        int[] array = preuzmiNizOdKorisnika();
        ArrayList<Integer> longestGrowingArray = longestGrowingArrayCalculator.dajNajveciRastuciNiz(array);
        ispisiPodatke("Broj elemenata najduzeg niza (the number of elements of the longest growing array is) ", longestGrowingArray.size());
        ispisiPodatke("Ovo je najduzi rastuci niz (this is the longest growing array) ", longestGrowingArray);
    }

    private int[] preuzmiNizOdKorisnika() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pokrecem program. Koliko brojeva zelite da upisete. Write how many numbers your array will have: ");
        int duzinaNiza = scanner.nextInt();
        System.out.printf("Upisite %d brojeva. Write your array: ", duzinaNiza);
        int[] niz = preuzmiNiz(duzinaNiza, scanner);
        System.out.println("Upisali ste brojeve. ");
        return niz;
    }

    private static int[] preuzmiNiz(int duzinaNiza, Scanner scanner) {
        int[] niz = new int[duzinaNiza];
        for (int i = 0; i < duzinaNiza; i++) {
            niz[i] = scanner.nextInt();
        }
        return niz;
    }

    private void ispisiPodatke(String nazivPodatka, Object podatak) {
        System.out.println(nazivPodatka + " = " + podatak);
    }

}
