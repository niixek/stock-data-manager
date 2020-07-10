import com.mongodb.client.*;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.mongodb.client.model.Filters.eq;


public class RegisterGui implements ActionListener{
    private JFrame frame;
    private JTextField userText;
    private JTextField passText;
    private JLabel correct;
    private MongoCollection<Document> usernames;

    //The constructor takes in a MongoDB collection and assigns the "usernames" field to the collection
    public RegisterGui(MongoCollection<Document> collection) {
        usernames = collection;
    }

    /*
       createNewUser() is the main logic behind the Register GUI. It takes in a login GUI to reopen the
       login window passed into it after successful registration.
    */
    public void createNewUser(LoginGui login) {
        /*
            attemptLogin allows logging in from pressing enter from either text field
            as well as clicking the login button.
        */
        Action attemptRegister = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = passText.getText();
                boolean registered = true;
                //System.out.println("Username: " + username);
                //System.out.println("Password: " + password);
                if (username.isEmpty() && password.isEmpty()) {
                    correct.setText("Please enter a username and password.");
                }
                else if (username.isEmpty()) {
                    correct.setText("Please enter a username.");
                }
                else if (password.isEmpty()) {
                    correct.setText("Please enter a password.");
                }
                /*
                    Checks if the username exists in the database, if not, register the username.
                    After the username is inserted into the database, close the register window and reopen the
                    login window.
                 */
                else {
                    correct.setText("");
                    for (Document document : usernames.find(eq("username", username))) {
                        if (document != null) {
                            registered = false;
                            break;
                        }
                    }
                    if (registered) {
                        Document newUser = new Document("username", username).append("password", password);
                        usernames.insertOne(newUser);
                        frame.dispose();
                        login.getFrame().setVisible(true);
                    }
                    else {
                        correct.setText("Username already exists.");
                    }
                }
            }
        };
        JPanel panel = new JPanel();
        panel.setBackground(new Color(52,52,52));

        frame = new JFrame();
        frame.setSize(400,350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.add(panel);

        panel.setLayout(null);

        JLabel registerLabel = new JLabel("Register");
        registerLabel.setBounds((frame.getWidth()/2)-50, (frame.getHeight()/4)-20, 100, 60);
        registerLabel.setFont(new Font("Montserrat", Font.PLAIN, 40));
        registerLabel.setForeground(Color.WHITE);
        panel.add(registerLabel);

        userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        userText.addActionListener(attemptRegister);
        panel.add(userText);



        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(10, 50, 80, 25);
        panel.add(passLabel);

        passText = new JPasswordField(20);
        passText.setBounds(100,50,165,25);
        passText.addActionListener(attemptRegister);
        panel.add(passText);

        JButton register = new JButton("Register");
        register.setBounds(10, 80, 90, 25);
        register.addActionListener(attemptRegister);
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
