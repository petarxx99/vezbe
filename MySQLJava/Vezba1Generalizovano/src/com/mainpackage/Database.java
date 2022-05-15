package com.mainpackage;
import java.sql.*;
import java.util.*;
import java.lang.reflect.*;

/*
SELECT count(*)
FROM information_schema.columns
WHERE table_name = 'Your_table_name';
Ovako se dobija broj kolona.
*/

public class Database {
    private String imeBaze;
    private String user;
    private String password;
    private String address;
    private int port;
    private String stringForConnection;


    private static HashMap<String, String> javaSQLtipovi = new HashMap<>(){
        {
            put("Array", "ARRAY");
            put("long", "BIGINT");
            put("Long", "BIGINT");
            put("byte", "TINYINT");
            put("Byte", "TINYINT");
            put("double", "DOUBLE");
            put("Double", "DOUBLE");
            put("float", "REAL");
            put("Float", "REAL");
            put("int", "INTEGER");
            put("Integer", "INTEGER");
            put("short", "SMALLINT");
            put("Short", "SMALLINT");
            put("byte[]", "BINARY");
            put("Byte[]", "BINARY");
            put("boolean", "BOOLEAN");
            put("String", "varchar(40)");
            put("Date", "DATE");
            put("LocalDate", "DATE");
            put("BigDecimal", "DECIMAL");
            put("Time", "TIME");
            put("Timestamp", "TIMESTAMP");
            put("LocalDateTime", "TIMESTAMP");
            put("Instant", "TIMESTAMP");

        }
    };

    public Database(String imeBaze, String user, String password, String address, int port){
        Timestamp t;
        this.imeBaze = imeBaze;
        this.user = user;
        this.password = password;
        this.address = address;
        this.port = port;
        stringForConnection = String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s", address, port, imeBaze, user, password);
    }

    public boolean execute(String sql){
        try(Connection connection = DriverManager.getConnection(stringForConnection)){
            Statement st = connection.createStatement();
            return st.execute(sql);
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public int getLastId(String table, String column){
        try(Connection connection = DriverManager.getConnection(stringForConnection)){
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT " + column + " FROM " + table + ";");
            int lastId = 0;
            while(rs.next()){
                lastId = rs.getInt(column);
            }
            return lastId;
        } catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    public int getLastId(String table){
        return getLastId(table, "id");
    }


/* id int primary key auto_increment, ime varchar(20) itd. BEZ ZAGRADA
*  CREATE TABLE imeTabele (koloneZaTabelu());
* */
   public static String koloneZaTabelu(Class klasa){
        Field[] fields = klasa.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        sb.append("id int primary key auto_increment");
        for(int i=1; i<fields.length; i++){
            sb.append(", ");
            String ime = fields[i].getName();
            String sqlTip = izJavaTipaUSQL(fields[i].getType().getSimpleName());
            sb.append(ime + " " + sqlTip);
        }

        return sb.toString();
    }


    public <T extends Makeable> String koloneZaTabelu2(T objekat){
        String fieldsForDB = objekat.getFieldsForDatabase();
        return "id int primary key auto_increment, " + fieldsForDB;
    }

    private static String izJavaTipaUSQL(String javaTip){
        return javaSQLtipovi.get(javaTip);
    }

    public <T extends Makeable> boolean createTableBasedOnObject(T objekat){
        Class klasaObjekta = objekat.getClass();
        String imeTabele = klasaObjekta.getSimpleName().toLowerCase();
        String kolone = koloneZaTabelu2(objekat);
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s.%s (%s);", imeBaze, imeTabele, kolone);
        System.out.println("SQL naredba: " + sql);
        try(Connection connection = DriverManager.getConnection(stringForConnection)){
            Statement st = connection.createStatement();
            return st.execute(sql);
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }



    public boolean createTable(String imeTabele, String kolone){
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s.%s (%s);", imeBaze, imeTabele, kolone);
        try (Connection connection =  DriverManager.getConnection(stringForConnection);){
            Statement statement = connection.createStatement();
            return statement.execute(sql);
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean create(String sql){
        try (Connection connection =  DriverManager.getConnection(stringForConnection);){
            Statement statement = connection.createStatement();
            return statement.execute(sql);
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public int insert(String sql){
        try (Connection connection =  DriverManager.getConnection(stringForConnection);){
            Statement statement = connection.createStatement();
           final int ROWS_AFFECTED = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
           if (ROWS_AFFECTED < 0) return -1;
           ResultSet rs = statement.getGeneratedKeys();
           rs.next(); // Moram uvek da napisem next pre koriscenja.
           final int INSERTED_ID = rs.getInt(1);
           return INSERTED_ID;
        }catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    public int updateAlter(String sql){
        try (Connection connection =  DriverManager.getConnection(stringForConnection);){
            Statement statement = connection.createStatement();
            final int ROWS_AFFECTED = statement.executeUpdate(sql);
            return ROWS_AFFECTED;
        }catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<String> query(String sql, int numOfColumns){
        try (Connection connection =  DriverManager.getConnection(stringForConnection);){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            ArrayList<String> output = new ArrayList<>();

            while(rs.next()){
                StringBuilder s = new StringBuilder();
                for(int i=1; i<=numOfColumns; i++){
                    s.append(rs.getString(i));
                    s.append("\n");
                }
                output.add(s.toString());
            }

            return output;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }



    public int insertPerson(String table, Person person){
        try(Connection connection = DriverManager.getConnection(stringForConnection)){
            PreparedStatement st = connection.prepareStatement("INSERT INTO " + imeBaze + "." + table + "VALUES (?, ?, ?, ?)");
            st.setString(0, String.valueOf(person.getPid()));
            st.setString(1, String.valueOf(person.getIme()));
            st.setString(2, String.valueOf(person.getPrezime()));
            st.setString(3, String.valueOf(person.getGodine()));

            int a = st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next(); // Moram uvek next();
            return rs.getInt(1); // id
        } catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    public boolean insertPerson(Person p){
        int lastId = getLastId("person");
        try(Connection connection = DriverManager.getConnection(stringForConnection)){
            PreparedStatement st = connection.prepareStatement("INSERT INTO person VALUES (?,?,?,?);");
            st.setString(1, String.valueOf(lastId + 1));
            st.setString(2, String.valueOf(p.getIme()));
            st.setString(3, String.valueOf(p.getPrezime()));
            st.setString(4, String.valueOf(p.getGodine()));
            return st.execute();
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Person> queryPerson(String sql){
        try(Connection connection = DriverManager.getConnection(stringForConnection)){
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ArrayList<Person> output = new ArrayList<>();
            while(rs.next()){
                output.add(new Person(rs.getInt(1), rs.getString("ime"), rs.getString("prezime"), rs.getInt("godine")));
            }

            return output;
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public <T extends Makeable> ArrayList<T> queryObjectTable(Class<T> klasa){
        ArrayList<T> output = new ArrayList<>();

        String imeKlase = klasa.getSimpleName().toLowerCase();
        String sql = String.format("SELECT * FROM %s.%s;", imeBaze, imeKlase);

        try(Connection connection = DriverManager.getConnection(stringForConnection)){
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){

                T noviObjekat = klasa.newInstance();
                ArrayList argumentiZaKonstruktor = new ArrayList();
                int brojKolona = noviObjekat.getDatabaseConstructorParameterCount();

                for(int i=1; i<=brojKolona; i++){
                    argumentiZaKonstruktor.add(rs.getObject(i));
                }

                noviObjekat.initObject(argumentiZaKonstruktor);
                output.add(noviObjekat);
            }

            return output;
        } catch(SQLException | InstantiationException | IllegalAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    private boolean isCharOrString(Object o){
        if (o.getClass().getSimpleName().equals("String")) return true;
        if(o.getClass().getSimpleName().equals("Character")) return true;
        if(o.getClass().getSimpleName().equals("char")) return true;

        return false;
    }

    public String getInsertSQL(int id, ArrayList podaci, String imeTabele){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(imeBaze);
        sb.append(".");
        sb.append(imeTabele);
        sb.append(" VALUES (");
        sb.append(id);

        for(int i=1; i<podaci.size(); i++){
            sb.append(", ");
            if(isCharOrString(podaci.get(i))){
                sb.append("'");
                sb.append(podaci.get(i));
                sb.append("'");
            } else {
                sb.append(podaci.get(i));
            }
        }
        sb.append(");");

        return sb.toString();
    }

    public <T extends Makeable> int insertObject(T objekat){
        final String IME_TABELE = objekat.getClass().getSimpleName().toLowerCase();
        createTableBasedOnObject(objekat);
        final int ID = getLastId(IME_TABELE) + 1;

        String sql = getInsertSQL(ID, objekat.getInfoForDatabase(), IME_TABELE);
        try(Connection connection = DriverManager.getConnection(stringForConnection)){
            Statement st = connection.createStatement();
            return st.executeUpdate(sql);
        } catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }



}
