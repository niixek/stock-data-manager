import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class StockDataManager {
    private static MongoCollection<Document> collection;

    public static MongoCollection<Document> mongoConnect() {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("SDMLoginDB");

        return database.getCollection("Usernames");
    }

    public static void main (String[] args) {
        collection = mongoConnect();
        LoginGui lg = new LoginGui(collection);
        lg.login();
    }
}
