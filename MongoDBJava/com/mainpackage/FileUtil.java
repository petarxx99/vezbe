package com.mainpackage;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {

    public static void upisiUFajl(String imeFajla, String staDaUpisem){
        try(FileOutputStream fis = new FileOutputStream(imeFajla)){
            fis.write(staDaUpisem.getBytes());
            fis.flush();
            fis.close();
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }




    public static String citajFajl(String imeFajla){
        try(FileReader fr = new FileReader(imeFajla)){

            StringBuilder sb = new StringBuilder();
            int nextChar;
            while((nextChar = fr.read()) > 0){
                char c = (char) nextChar;
                sb.append((Character) c);
            }

            fr.close();
            return sb.toString();
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static String citajFajl2(String imeFajla){
        try(FileReader fr = new FileReader(imeFajla)){
            StringBuilder sb = new StringBuilder();
            char[] buffer = new char[256];
            int length;
            final int OFFSET = 0;
            while((length = fr.read(buffer, OFFSET, buffer.length)) > 0){
                String s = new String(buffer, OFFSET, length);
                sb.append(s);
            }

            fr.close(); //Da budem siguran da se izvrsilo preuzimanje podataka iz fajla.
            return sb.toString();
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

}
