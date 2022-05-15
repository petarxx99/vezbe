package com.mainpackage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;
import java.util.*;

public class Person implements Makeable{
    private int pid;
    private String ime;
    private String prezime;
    private int godine;

    public Person(){}

    public Person(int pid, String ime, String prezime, int godine) {
        this.pid = pid;
        this.ime = ime;
        this.prezime = prezime;
        this.godine = godine;
    }

    public Person(String ime, String prezime, int godine){
        this.pid = 0;
        this.ime = ime;
        this.prezime = prezime;
        this.godine = godine;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public int getGodine() {
        return godine;
    }

    public void setGodine(int godine) {
        this.godine = godine;
    }

    public int getId(){
        return pid;
    }

    public void setId(int id){
        this.pid = id;
    }


    public String toJson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.toJson(this);
    }

    public static Person fromJson(String json){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(json, Person.class);
    }


    @Override
    public void initObject(ArrayList argumenti){
        this.pid = (int) argumenti.get(0);
        this.ime = (String) argumenti.get(1);
        this.prezime = (String) argumenti.get(2);
        this.godine = (int) argumenti.get(3);
    }


    @Override
    public int getDatabaseConstructorParameterCount(){
        return 4;
    }

    @Override
    public ArrayList getInfoForDatabase(){
        ArrayList output = new ArrayList();
        output.add(this.pid);
        output.add(this.ime);
        output.add(this.prezime);
        output.add(this.godine);

        return output;
    }


    @Override
    public String getFieldsForDatabase(){
        return "ime varchar(20), prezime varchar(20), godine int";
    }

    @Override
    public String toString(){
        return  "pid: " + pid + "\n" +
                "ime: " + ime + "\n" +
                "prezime: " + prezime + "\n" +
                "godine: " + godine + "\n";
    }
}
