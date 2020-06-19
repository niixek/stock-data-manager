import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import org.bson.Document;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
public class Gui extends JFrame{
    public Gui() {
        setLayout(new FlowLayout());

        ImageIcon image1 = new ImageIcon(getClass().getResource("image1.png"));

        JLabel imgLabel = new JLabel(image1);
        imgLabel.setToolTipText("Welcome to the Stock Data Manager");
        imgLabel.setSize(50,50);
        add(imgLabel);

        JLabel label = new JLabel("Username");
        label.setBounds(10,20,80,25);
        add(label);

        JTextField textfield = new JTextField(15);
        add(textfield);

        JButton button = new JButton("Button");
        add(button);

    }

    public static void main(String[] args){
        Gui window = new Gui();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(500,500);
        window.setVisible(true);
        window.setTitle("Stock Data Manager");
        window.pack();
    }
}

 */

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

        /*
        Document test = new Document("username", "testname").append("password", "abc123!");
        collection.insertOne(test);
        */
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
        /*
        if (username.equals("test") && password.equals("abc")) {
            correct.setText("Logged in Successfully!");
        }
        else {
            correct.setText("Incorrect username/password!");
        }
        */
        System.out.println("Username: " + username);
        System.out.println(usernames.find(eq("username", username)));
    }
}
