package com.mainpackage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Osoba implements IdInterface{
    private String pid;
    private int godine;
    private String ime;
    private boolean polMuski;
    private int bod = 0;

    public boolean isPolMuski() {
        return polMuski;
    }

    public void setPolMuski(boolean polMuski) {
        this.polMuski = polMuski;
    }

    public int getBod() {
        return bod;
    }

    public void setBod(int bod) {
        this.bod = bod;
    }

    public Osoba(){}

    public Osoba(String pid, int godine, String ime, boolean polMuski) {
        this.pid = pid;
        this.godine = godine;
        this.ime = ime;
        this.polMuski = polMuski;
    }

    @Override
    public String getPid() {
        return this.pid;
    }

    @Override
    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getGodine() {
        return godine;
    }

    public void setGodine(int godine) {
        this.godine = godine;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void uFajlJSON(String imeFajla){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        String serialisedJson = gson.toJson(this);
        FileUtil.upisiUFajl(imeFajla, serialisedJson);
    }

    public static Osoba izFajlaJSON(String imeFajla){
        String serialisedJson = FileUtil.citajFajl(imeFajla);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(serialisedJson, Osoba.class); // this.getClass() moze umesto Osoba.class
    }


    public void uFajlXML(String imeFajla){
        try(FileOutputStream fis = new FileOutputStream(imeFajla);
            XMLEncoder xmlEncoder = new XMLEncoder(fis);){

            xmlEncoder.writeObject(this);
        } catch(IOException e){
            e.printStackTrace();
        }
    }


    public static Osoba izFajlaXML(String imeFajla){
        try(FileInputStream fis = new FileInputStream(imeFajla);
            XMLDecoder xmlDecoder = new XMLDecoder(fis);){

            return (Osoba) xmlDecoder.readObject();
        }
        catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public String toString() {
        return
                "pid=" + pid + "\n" +
                        "godine=" + godine + "\n" +
                        "ime=" + ime + "\n" +
                        "bodovi=" + bod + "\n";
    }



}



