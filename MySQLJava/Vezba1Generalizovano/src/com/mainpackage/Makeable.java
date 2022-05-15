package com.mainpackage;
import java.lang.reflect.Field;
import java.util.*;

public interface Makeable {

    public void initObject(ArrayList konstruktorArgumenti);

    public int getDatabaseConstructorParameterCount();

    public ArrayList getInfoForDatabase();

    public String getFieldsForDatabase();
}

