package com.mainpackage;

import java.util.ArrayList;

public class Student extends Person {
    private String fakultet;

    public Student(int pid, String ime, String prezime, int godine, String fakultet) {
        super(pid, ime, prezime, godine);
        this.fakultet = fakultet;
    }

    public Student(String ime, String prezime, int godine, String fakultet){
        super(ime, prezime, godine);
        this.fakultet = fakultet;
    }

    public Student(){}

    public String getFakultet() {
        return fakultet;
    }

    public void setFakultet(String fakultet) {
        this.fakultet = fakultet;
    }

    @Override
    public void initObject(ArrayList konstruktorArgumenti){
        super.initObject(konstruktorArgumenti);
        this.fakultet = (String) konstruktorArgumenti.get(4);
    }

    @Override
    public int getDatabaseConstructorParameterCount(){
        return super.getDatabaseConstructorParameterCount() + this.getClass().getDeclaredFields().length;
    }

    @Override
    public ArrayList getInfoForDatabase(){
        ArrayList output = super.getInfoForDatabase();
        output.add(fakultet);
        return output;
    }

    @Override
    public String getFieldsForDatabase(){
        StringBuilder sb = new StringBuilder(super.getFieldsForDatabase());
        sb.append(", fakultet varchar(40)");
        return sb.toString();
    }

    @Override
    public String toString(){
        return super.toString() + "fakultet= " + fakultet;
    }


}
