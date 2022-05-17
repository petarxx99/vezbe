package com.glavnipaket;

import javax.swing.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class BazaPodataka {
    private String stringZaKonekciju;
    private String username;
    private String lozinka;
    private String imeBaze;
    private String adresaServera;
    private int portServera;

    public static int  NISTA_ZA_UPDATE=-1, NEISPRAVNE_GODINE=-2,  NEISPRAVNI_DOHODAK=-3, SQL_EXCEPTION=-4;

    public BazaPodataka(String imeBaze, String adresaServera, int portServera, String username, String lozinka){
        this.imeBaze = imeBaze;
        this.adresaServera = adresaServera;
        this.portServera = portServera;
        this.username = username;
        this.lozinka = lozinka;
        this.stringZaKonekciju = String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s", adresaServera, portServera, imeBaze, username, lozinka);
    }

    public int ubaciteZaposlenogUBazu(Zaposleni zaposleni, String imeTabele){
        String sql = String.format("INSERT INTO %s (ime, godine, adresa, visina_dohotka) VALUES ('%s', %s, '%s', %s);", imeTabele, zaposleni.getIme(), zaposleni.getGodine(), zaposleni.getAdresa(), String.valueOf(zaposleni.getVisinaDohotka()));
        try(Connection connection = DriverManager.getConnection(stringZaKonekciju)){
            Statement st = connection.createStatement();
            return st.executeUpdate(sql);
        } catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<Zaposleni> prikaziSveZaposlene(String imeTabele){
        try(Connection connection = DriverManager.getConnection(stringZaKonekciju)){
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + imeTabele + ";");
            ArrayList<Zaposleni> zaReturn = new ArrayList<>();
            while(rs.next()){
                zaReturn.add(new Zaposleni(rs.getInt(1), rs.getString("ime"), rs.getInt("godine"), rs.getString("adresa"), rs.getBigDecimal("visina_dohotka")));
            }
            return zaReturn;
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Zaposleni> prikaziZaposlenePoUslovu(String imeTabele, String uslov){
        String sql = String.format("SELECT * FROM %s WHERE %s;", imeTabele, uslov);
        try(Connection connection = DriverManager.getConnection(stringZaKonekciju)){
            Statement statement = connection.createStatement();
            statement.executeQuery(sql);

            ResultSet rs = statement.executeQuery(sql);
            ArrayList<Zaposleni> zaReturn = new ArrayList<>();
            while(rs.next()){
                zaReturn.add(new Zaposleni(rs.getInt(1), rs.getString("ime"), rs.getInt("godine"), rs.getString("adresa"), rs.getBigDecimal("visina_dohotka")));
            }
            return zaReturn;
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }




    public int izbrisiteZaposlenog(int id, String imeTabele){
        String sql = String.format("DELETE FROM %s WHERE id=%s", imeTabele, id);
        try(Connection connection = DriverManager.getConnection(stringZaKonekciju)){
            Statement st = connection.createStatement();
            return st.executeUpdate(sql);
        } catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }


    public int update(int id, String ime, String godineString, String adresa, String visinaDohotkaString, String imeTabele){
        if (ime==null && godineString==null && adresa==null && visinaDohotkaString==null) {
            JOptionPane.showMessageDialog(null, "Niste stiklirali nista.");
            return NISTA_ZA_UPDATE;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE " + imeTabele + " SET ");

        if(ime != null){
            sb.append("ime="+ "'" + ime + "'");
        }

        if(adresa != null) {
            sb.append(", adresa=" + "'" + adresa + "'");
        }

        if(godineString!=null) {
            try {
                int godine = Integer.parseInt(godineString);
                if (godine < 0) return NEISPRAVNE_GODINE;
                sb.append(", godine=" + godine);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Neispravne godine.");
                return NEISPRAVNE_GODINE;
            }
        }

        if(visinaDohotkaString!=null){
            try{
                BigDecimal visinaDohotka = new BigDecimal(visinaDohotkaString);
                sb.append(", visina_dohotka=" + visinaDohotka);
            } catch(Exception e){
                JOptionPane.showMessageDialog(null, "Neispravni dohodak");
                return NEISPRAVNI_DOHODAK;
            }
        }
        sb.append(";");

        try(Connection connection = DriverManager.getConnection(stringZaKonekciju)){
            Statement st = connection.createStatement();
            return st.executeUpdate(sb.toString());
        } catch(SQLException e){
            e.printStackTrace();
            return SQL_EXCEPTION;
        }
    }




}
