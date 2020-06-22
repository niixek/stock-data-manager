import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import org.bson.Document;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGui implements ActionListener {
    private static JLabel userLabel;
    private static JTextField userText;
    private static JLabel passLabel;
    private static JTextField passText;
    private static JButton login;
    private static JLabel correct;
    private static MongoCollection<Document> usernames;

    public static MongoCollection<Document> mongoConnect() {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("SDMLoginDB");
        MongoCollection<Document> collection = database.getCollection("Usernames");

        return collection;
    }

    public static void main (String[] args) {
        usernames = mongoConnect();

        JPanel panel = new JPanel();

        JFrame frame = new JFrame();
        frame.setSize(400,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Login");
        frame.add(panel);

        panel.setLayout(null);

        userLabel = new JLabel("Username");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        passLabel = new JLabel("Password");
        passLabel.setBounds(10, 50, 80, 25);
        panel.add(passLabel);

        passText = new JPasswordField(20);
        passText.setBounds(100,50,165,25);
        panel.add(passText);

        login = new JButton("Login");
        login.setBounds(10, 80, 80, 25);
        login.addActionListener(new LoginGui());
        panel.add(login);

        correct = new JLabel("");
        correct.setBounds(10,110,300,25);
        panel.add(correct);


        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = userText.getText();
        String password = passText.getText();
        Block<Document> printBlock = document -> System.out.println(document.toJson());
        /*
        if (username.equals("test") && password.equals("abc")) {
            correct.setText("Logged in Successfully!");
        }
        else {
            correct.setText("Incorrect username/password!");
        }
        */
        System.out.println("Username: " + username);
        for (Document document : usernames.find(eq("username", username))) {
            /*if(document != null) {

            }

             */
            printBlock.apply(document);
        }
    }
}
