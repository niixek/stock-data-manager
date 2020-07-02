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
    private JFrame loginFrame;
    private JTextField userText;
    private JTextField passText;
    private JLabel correct;
    private MongoCollection<Document> usernames;

    public JFrame getFrame() {
        return loginFrame;
    }

    public static MongoCollection<Document> mongoConnect() {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("SDMLoginDB");

        return database.getCollection("Usernames");
    }

    public void login() {
        usernames = mongoConnect();
        Action attemptLogin = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = passText.getText();
                boolean loggedIn = false;
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
                            loggedIn = true;
                        }
                    }
                    if (loggedIn) {
                        loginFrame.dispose();
                    }
                    else {
                        correct.setText("Incorrect username/password.");
                    }
                }
            }
        };

        JPanel panel = new JPanel();

        loginFrame = new JFrame();
        loginFrame.setSize(400,200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setTitle("Login");
        loginFrame.add(panel);

        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        userText.addActionListener(attemptLogin);
        panel.add(userText);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(10, 50, 80, 25);
        panel.add(passLabel);

        passText = new JPasswordField(20);
        passText.setBounds(100,50,165,25);
        passText.addActionListener(attemptLogin);
        panel.add(passText);

        JButton login = new JButton("Login");
        login.setBounds(10, 80, 80, 25);
        login.addActionListener(attemptLogin);
        panel.add(login);

        JButton register = new JButton("Register");
        register.setBounds(100, 80, 90, 25);
        register.addActionListener(e -> {
            RegisterGui rg = new RegisterGui();
            rg.createNewUser(this);
            loginFrame.setVisible(false);
        });
        panel.add(register);

        correct = new JLabel("");
        correct.setBounds(10,110,300,25);
        panel.add(correct);

        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        String username = userText.getText();
        String password = passText.getText();
        boolean loggedIn = false;
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
                    loggedIn = true;
                }
            }
            if (loggedIn) {
                frame.dispose();
            }
            else {
                correct.setText("Incorrect username/password.");
            }
        }

         */
    }
}
