package com.mainpackage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Car implements Makeable {
    private int pid;
    private int godiste;
    private int konjskaSnaga;
    private String marka;

    public Car(){}

    public Car(int godiste, int konjskaSnaga, String marka) {
        this.pid = 0;
        this.godiste = godiste;
        this.konjskaSnaga = konjskaSnaga;
        this.marka = marka;
    }


    public Car(int pid, int godiste, int konjskaSnaga, String marka) {
        this.pid = pid;
        this.godiste = godiste;
        this.konjskaSnaga = konjskaSnaga;
        this.marka = marka;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getGodiste() {
        return godiste;
    }

    public void setGodiste(int godiste) {
        this.godiste = godiste;
    }

    public int getKonjskaSnaga() {
        return konjskaSnaga;
    }

    public void setKonjskaSnaga(int konjskaSnaga) {
        this.konjskaSnaga = konjskaSnaga;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public void setId(int id){
        this.pid = id;
    }

    public int getId(){
        return pid;
    }

    public String toJson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.toJson(this);
    }

    public static Car fromJson(String json){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(json, Car.class);
    }


    @Override
    public void initObject(ArrayList argumenti){
        this.pid = (int) argumenti.get(0);
        this.godiste = (int) argumenti.get(1);
        this.konjskaSnaga = (int) argumenti.get(2);
        this.marka = (String) argumenti.get(3);
    }


    @Override
    public int getDatabaseConstructorParameterCount(){
        return 4;
    }

    @Override
    public ArrayList getInfoForDatabase(){
        ArrayList output = new ArrayList();
        output.add(this.pid);
        output.add(this.godiste);
        output.add(this.konjskaSnaga);
        output.add(this.marka);

        return output;
    }


    @Override
    public String getFieldsForDatabase(){
        return "godiste int, konjskaSnaga int, marka varchar(40)";
    }

    @Override
    public String toString() {
        return "pid=" + pid + "\n" +
                "godiste=" + godiste + "\n" +
                "konjskaSnaga=" + konjskaSnaga + "\n" +
                "marka=" + marka + "\n";
    }

}
