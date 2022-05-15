package com.mainpackage;
import com.mainpackage.util.Refleksija;

import java.lang.reflect.Field;
import java.util.*;
import java.sql.*;


public class Main {
    public static void main(String[] args){
    	String username = "";
    	String password = "";
    	String dbName = "";
        int port = 3306;
        String address = "localhost";
        Database db = new Database(dbName, username, password, address, port);
        
        db.execute("DROP TABLE IF EXISTS db1.car");
        Car car1 = new Car(2009, 85, "Renault");
        Car car2 = new Car(2004, 100, "Opel");
        db.insertObject(car1);
        db.insertObject(car2);

        ArrayList<Car> carResults = db.queryObjectTable(Car.class);
        for(Car c : carResults){
            System.out.println(c);
        }


        Student student1 = new Student("Ilija", "Mikic", 20, "FON");
        db.insertObject(student1);
        ArrayList<Student> studentResults = db.queryObjectTable(Student.class);
        for(Student s : studentResults){
            System.out.println(s);
        }
       

        Person p1 = new Person("Kosta", "Ilic", 19);
        db.insertPerson2(p1);
        ArrayList<Person> pQuery = db.queryPerson("SELECT * FROM person;");
        System.out.println(pQuery);

        ArrayList<Person> pQuery2 = db.queryObjectTable(Person.class);
        for(Person p : pQuery2){
            System.out.println(p);
        }  


    }
}


