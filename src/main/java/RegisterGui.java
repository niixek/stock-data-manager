import com.mongodb.client.*;
import org.bson.Document;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.mongodb.client.model.Filters.eq;


public class RegisterGui implements ActionListener{
    private JFrame frame;
    private JTextField user;
    private JTextField pass;
    private JLabel check;
    private MongoCollection<Document> usernames;

    public MongoCollection<Document> mongoConnect() {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("SDMLoginDB");

        return database.getCollection("Usernames");
    }

    public void createNewUser() {
        usernames = mongoConnect();
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

        user = new JTextField(20);
        user.setBounds(100,20,165,25);
        panel.add(user);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(10, 50, 80, 25);
        panel.add(passLabel);

        pass = new JPasswordField(20);
        pass.setBounds(100,50,165,25);
        panel.add(pass);

        JButton login = new JButton("Register");
        login.setBounds(10, 80, 90, 25);
        login.addActionListener(new LoginGui());
        panel.add(login);

        check = new JLabel("");
        check.setBounds(10,110,300,25);
        panel.add(check);


        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String username = user.getText();
        String password = pass.getText();
        boolean createUser = false;

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        /*
        if (username.isEmpty() && password.isEmpty()) {
            check.setText("Please enter a username and password.");
        }
        else if (username.isEmpty()) {
            check.setText("Please enter a username.");
        }
        else if (password.isEmpty()) {
            check.setText("Please enter a password.");
        }
        else {
            check.setText("");
            for (Document document : usernames.find(eq("username", username))) {
                if (document.isEmpty()) {
                    createUser = true;
                    break;
                }
            }
            if (createUser) {
                frame.dispose();
            }
            else {
                check.setText("That username is already taken, please try another.");
            }
        }

         */
    }
}
