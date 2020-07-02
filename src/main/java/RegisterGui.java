import com.mongodb.client.*;
import org.bson.Document;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.mongodb.client.model.Filters.eq;


public class RegisterGui implements ActionListener{
    private JFrame frame;
    private JTextField userText;
    private JTextField passText;
    private JLabel correct;
    private MongoCollection<Document> usernames;

    public MongoCollection<Document> mongoConnect() {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("SDMLoginDB");

        return database.getCollection("Usernames");
    }

    public void createNewUser() {
        usernames = mongoConnect();
        Action attemptLogin = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = passText.getText();
                boolean registered = false;
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);
                if (username.isEmpty() && password.isEmpty()) {
                    correct.setText("Please enter login credentials.");
                }
                else if (username.isEmpty()) {
                    correct.setText("Please enter a username.");
                }
                else if (password.isEmpty()) {
                    correct.setText("Please enter a password.");
                }
                else {
                    correct.setText("");
                    for (Document document : usernames.find(eq("username", username))) {
                        if (document.get("password").equals(password)) {
                            registered = true;
                        }
                    }
                    if (registered) {
                        frame.dispose();
                    }
                    else {
                        correct.setText("Incorrect username/password.");
                    }
                }
            }
        };
        JPanel panel = new JPanel();

        frame = new JFrame();
        frame.setSize(400,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Register a New Account");
        frame.add(panel);

        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(10, 50, 80, 25);
        panel.add(passLabel);

        passText = new JPasswordField(20);
        passText.setBounds(100,50,165,25);
        panel.add(passText);

        JButton register = new JButton("Register");
        register.setBounds(10, 80, 90, 25);
        register.addActionListener(attemptLogin);
        panel.add(register);

        correct = new JLabel("");
        correct.setBounds(10,110,300,25);
        panel.add(correct);


        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
