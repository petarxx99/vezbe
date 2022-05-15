package com.mainpackage.util;
import java.util.*;
import java.lang.reflect.*;
import java.util.stream.Collectors;

public class Refleksija {

    public static ArrayList<String> getFields(Class klasa){
        Method[] methods = klasa.getMethods();
        return Arrays.stream(methods).filter(Refleksija::pocinjeSaSet).map(Refleksija::setUString).collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<String> constructorFields(Class klasa){
           Constructor[] sviKonstruktori = klasa.getConstructors();
           Constructor konstruktor = najveciKonstruktor(sviKonstruktori);

           Parameter[] parametri = konstruktor.getParameters();
           Class[] parameterTypes = konstruktor.getParameterTypes();

           for(int i=0; i<konstruktor.getParameterCount(); i++) {
               String tip = parameterTypes[i].getSimpleName();
               String ime = parametri[i].getName();
               System.out.println(tip + " " + ime);
           }

           return null;

    }

    public static Constructor najveciKonstruktor(Constructor[] konstruktori){
        int max = 0;
        int maxParametara = 0;

        for(int i=0; i<konstruktori.length; i++){
            int brojParametara = konstruktori[i].getParameterTypes().length;
            if (brojParametara > maxParametara){
                max = i;
                maxParametara = brojParametara;
            }
        }

        return konstruktori[max];
    }

    private static String setUString(Method m){
        return m.getName().substring("set".length());
    }
    
    private static boolean pocinjeSaSet(Method method){
        String s = method.getName();
        if (s.startsWith("set")) {
            return true;
        }
        return false;
    }


}
