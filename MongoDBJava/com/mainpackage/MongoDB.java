package com.mainpackage;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.*;

/* Svi fajlovi na pocetku moraju da imaju package com.mainpackage ili u kom god paketu da se nalaze. */
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDB {
    public final static int MONGO_PORT = 27017;

    MongoClient mongoClient;
    MongoDatabase mongoDB;
    ArrayList kopijaKolekcije = null;

    public MongoDB(MongoClient mongoClient, MongoDatabase mongoDB) {
        this.mongoClient = mongoClient;
        this.mongoDB = mongoDB;
    }

    public <T> void printCollection(MongoCollection<T> mongoCollection) {
        FindIterable<T> findIterable = mongoCollection.find();
        findIterable.forEach((Block<T>) System.out::println);
    }

    /* Umesto prethodne 3 metoda moze da se koristi jedna. */
    public <T extends IdInterface> void increaseId(MongoCollection<T> mongoCollection, int pidIncrease) {
        FindIterable<T> findIterable = mongoCollection.find();
        for (T objekat : findIterable) {
            int pid = Integer.parseInt(objekat.getPid());
            Bson query = Filters.eq("pid", String.valueOf(pid));
            Bson update = Updates.set("pid", String.valueOf(pid + pidIncrease));
            mongoCollection.updateOne(query, update);
        }
    }


    public static void main(String[] args) {
        MongoDB main = new MongoDB(null, null);

        final String DB_NAME = "vezba1";
        MongoClient mgClient = new MongoClient("localhost:27017", MONGO_PORT);
        mgClient.dropDatabase(DB_NAME);
        mgClient.close();

        MongoCollection<Osoba> mongoCollection = main.connectToMongoCollectionOsoba(DB_NAME, "prva kolekcija osoba");
        Osoba o1 = new Osoba("0", 50, "Ivan", true);
        mongoCollection.insertOne(o1);

        ArrayList<Osoba> listaOsoba1 = new ArrayList<>();
        listaOsoba1.add(new Osoba("1", 81, "Milojko", true));
        listaOsoba1.add(new Osoba("2", 19, "Ivana", false));
        listaOsoba1.add(new Osoba("3", 10, "Jovana", false));
        listaOsoba1.add(new Osoba("4", 18, "Jovan", true));
        listaOsoba1.add(new Osoba("5", 61, "Marija", false));

        mongoCollection.insertMany(listaOsoba1);
        main.printCollection(mongoCollection);

        Bson query1 = Filters.eq("ime", "Milojko");
        Bson update1 = Updates.set("godine", 91);
        mongoCollection.updateOne(query1, update1);

        System.out.println("Posle menjanja godina Milojku.");
        main.printCollection(mongoCollection);

        final int ID_INCREASE = 10;
        //  main.increaseAllId(mongoCollection, ID_INCREASE);
        main.increaseId(mongoCollection, ID_INCREASE);
        System.out.println("Posle povecanja id-a za " + ID_INCREASE);
        main.printCollection(mongoCollection);

        main.muskiPreko50Godina(mongoCollection);
        System.out.println("Muski preko 50 godina imaju druge bodove.");
        main.printCollection(mongoCollection);

        main.zenaIliPreko50(mongoCollection);
        System.out.println("Zena, kao i osobe preko 50 godina.");
        main.printCollection(mongoCollection);

        System.out.println("Imena baza i kolekcija. Kolekcije baze " + DB_NAME + ": ");
        MongoIterable<String> iterable = main.mongoDB.listCollectionNames();
        iterable.forEach((Block<String>) System.out::println);

        System.out.println("Imena baza: ");
        for (String s : main.mongoClient.getDatabaseNames()) {
            System.out.println("baza: " + s);
        }

        //Ovako se brise kolekcija
        //db.getCollection("imeKolekcijeKojuZelimDaObrisem.").drop();

        //Ovako se brise baza podataka.
        // mongoClient.dropDatabase("imeBazeKojuZelimDaObrisem.");

        //Ovo je jos jedan nacin da se kreira kolekcija.
        /*db.createCollection("testCollection",
                new CreateCollectionOptions().capped(true).sizeInBytes(0x100000));
        */

    }

    /* Filters.gte znaci vece ili jednako, lte znaci manje ili jednako, le manje, ge vece. */
    public void zenaIliPreko50(MongoCollection<Osoba> mongoCollection) {
        Bson query = Filters.or(Filters.eq("polMuski", false), Filters.gte("godine", 50));
        query = Filters.and(query, Filters.eq("bod", 0));
        Bson update = Updates.set("bod", 20);
        mongoCollection.updateMany(query, update);
    }

    public void muskiPreko50Godina(MongoCollection<Osoba> mongoCollection) {
        Bson query = Filters.and(Filters.eq("polMuski", true), Filters.gte("godine", 50), Filters.eq("bod", 0));
        Bson update = Updates.set("bod", 10);
        mongoCollection.updateMany(query, update);
    }


    public MongoCollection connectToMongoCollectionOsoba(String database, String collection){

        this.mongoClient = new MongoClient("localhost", MONGO_PORT);
        this.mongoDB = this.mongoClient.getDatabase(database);
        MongoCollection<Osoba> mongoCollection = this.mongoDB.getCollection(collection, Osoba.class);

        CodecRegistry codecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );
        mongoCollection = mongoCollection.withCodecRegistry(codecRegistry);
        return mongoCollection;
    }

}
