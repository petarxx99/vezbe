package com.glavnipaket;

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
            PreparedStatement st = connection.prepareStatement("INSERT INTO ? (ime, godine, adresa, visina_dohotka) VALUES (?, ?, ?, ?);");
            st.setString(1, imeTabele);
            st.setString(2, "'" + zaposleni.getIme() + "'");
            st.setString(3, String.valueOf(zaposleni.getGodine()));
            st.setString(4, "'" + zaposleni.getAdresa() + "'");
            st.setString(5, String.valueOf(zaposleni.getVisinaDohotka()));

            Statement statement = connection.createStatement();
            System.out.println("SQL: " + sql);
            if (1==1) return statement.executeUpdate(sql);
            return st.executeUpdate();
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

    public int izmeniImeZaposlenog(int id, String ime, String imeTabele){
        String sql = String.format("UPDATE %s set ime='%s' WHERE id=%d;", imeTabele, ime, id);

        try(Connection connection = DriverManager.getConnection(stringZaKonekciju)){
            Statement st = connection.createStatement();
            return st.executeUpdate(sql);
        } catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    public int izmeniGodineZaposlenog(int id, int godine, String imeTabele){
        String sql = String.format("UPDATE %s set godine=%d WHERE id=%d;", imeTabele, godine, id);

        try(Connection connection = DriverManager.getConnection(stringZaKonekciju)){
            Statement st = connection.createStatement();
            return st.executeUpdate(sql);
        } catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    public int izmeniAdresuZaposlenog(int id, String adresa, String imeTabele){
        String sql = String.format("UPDATE %s SET adresa='%s' WHERE id=%d;", imeTabele, adresa, id);
        try(Connection connection = DriverManager.getConnection(stringZaKonekciju)){
            Statement st = connection.createStatement();
            return st.executeUpdate(sql);
        } catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    public int izmeniVisinuDohotkaZaposlenog(int id, BigDecimal dohodak, String imeTabele){
        String sql = String.format("UPDATE %s SET visina_dohotka=%s WHERE id=%s;", imeTabele, dohodak, id);
        try(Connection connection = DriverManager.getConnection(stringZaKonekciju)){
            Statement st = connection.createStatement();
            return st.executeUpdate(sql);
        } catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }


}
