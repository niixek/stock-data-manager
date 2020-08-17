import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/*
    This is the main class that runs the entire Stock Data Manager application
 */
public class StockDataManager {
    private static MongoCollection<Document> collection;

    /*
        mongoConnect attempts to connect to the mongoDB database that is requested, in this case
        the SDMLoginDB, and return the data that is stored in it.
        returns a MongoCollection<Document>, which is used to return data stored in the database
     */
    public static MongoCollection<Document> mongoConnect() {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("SDMLoginDB");

        return database.getCollection("Usernames");
    }

    public static void main (String[] args) {
        collection = mongoConnect();

        //Run starting with the login GUI
        LoginGui lg = new LoginGui(collection);
        lg.login();

        //Run starting with register GUI and login GUI upon closing
        //LoginGui lg2 = new LoginGui(collection);
        //RegisterGui rg = new RegisterGui(collection);
        //rg.createNewUser(lg2);

        //Run starting with the information GUI and a username (not working)
        //InformationGui ig = new InformationGui(collection, "testname");
        //ig.enterInfo();

        //Run starting with the welcome GUI and a username (not working)
        //WelcomeGui wg = new WelcomeGui(collection, "testname");
        //wg.welcome();

        //Run starting with the stock GUI and a username (not working)
        //StockGui sg = new StockGui(collection, "testname");
        //sg.viewStock();
    }
}
