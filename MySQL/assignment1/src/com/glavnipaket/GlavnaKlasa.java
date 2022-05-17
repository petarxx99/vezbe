package com.glavnipaket;

import com.glavnipaket.prozor.GlavniProzor;

public class GlavnaKlasa {

    public static void main(String[] args){
        BazaPodataka baza = new BazaPodataka("assignment_zaposleni", "localhost", 3306,"assignment_user", "password");
        GlavniProzor glavniProzor = new GlavniProzor(500, 500, baza, "zaposleni", 50, 50, new int[]{8, 4});

    }


}
