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
    private Color background = new Color(33,33,33);

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
        panel.setBackground(background);

        frame = new JFrame();
        frame.setSize(400,350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.add(panel);

        panel.setLayout(null);

        JLabel registerLabel = new JLabel("Sign Up");
        registerLabel.setBounds((frame.getWidth()/2)-75, (frame.getHeight()/4)-20, 150, 60);
        registerLabel.setFont(new Font("Montserrat", Font.PLAIN, 40));
        registerLabel.setForeground(Color.WHITE);
        panel.add(registerLabel);

        userText = new JTextField(20);
        userText.setBounds(50,150,300,35);
        userText.setFont(new Font("Montserrat", Font.PLAIN, 14));
        userText.addActionListener(attemptRegister);
        userText.setBorder(BorderFactory.createEmptyBorder());
        panel.add(userText);

        //Allows for "ghost text" to disappear and reappear for username and password fields
        TextPrompt userPrompt = new TextPrompt("   Username", userText);
        userPrompt.changeAlpha(.5f);
        userPrompt.setShow(TextPrompt.Show.FOCUS_LOST);

        passText = new JPasswordField(20);
        passText.setBounds(50,200,300,35);
        passText.setFont(new Font("Montserrat", Font.PLAIN, 14));
        passText.addActionListener(attemptRegister);
        passText.setBorder(BorderFactory.createEmptyBorder());
        panel.add(passText);

        TextPrompt passPrompt = new TextPrompt("   Password", passText);
        passPrompt.changeAlpha(.5f);
        passPrompt.setShow(TextPrompt.Show.FOCUS_LOST);

        JButton register = new JButton("Sign Up");
        register.setFont(new Font("Montserrat", Font.BOLD, 14));
        register.setBounds(50, 250, 300, 30);
        register.setForeground(Color.WHITE);
        register.setBackground(new Color(197,76,76));
        register.setBorder(BorderFactory.createEmptyBorder());
        register.setFocusPainted(false);
        register.addActionListener(attemptRegister);
        panel.add(register);

        correct = new JLabel("");
        correct.setFont(new Font("Montserrat", Font.PLAIN, 14));
        correct.setForeground(Color.WHITE);
        correct.setBounds(50,120,300,25);
        panel.add(correct);

        //This button closes the register window and reopens the login window
        JButton quit = new JButton("X");
        quit.setFont(new Font("Montserrat", Font.BOLD, 14));
        quit.setBounds(350, 20, 30, 20);
        quit.setForeground(Color.WHITE);
        quit.setBackground(background);
        quit.setBorder(BorderFactory.createEmptyBorder());
        quit.setFocusPainted(false);
        quit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                login.getFrame().setVisible(true);
            }
        });
        panel.add(quit);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
