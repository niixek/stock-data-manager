import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;
import org.bson.Document;


import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGui implements ActionListener {
    private JFrame loginFrame;
    private JTextField userText;
    private JTextField passText;
    private JLabel correct;
    private MongoCollection<Document> usernames;
    private Color background = new Color(33,33,33);
    private MatteBorder border = BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE);

    //The constructor takes in a MongoDB collection and assigns the "usernames" field to the collection
    public LoginGui(MongoCollection<Document> collection) {
        usernames = collection;
    }

    /*
        getFrame() is a getter method for the current LoginGui frame.
        returns the current LoginGui JFrame
     */
    public JFrame getFrame() {
        return loginFrame;
    }

    /*
        login() is the main logic behind the Login GUI, it calls on the Register GUI if a new
        username wants to be created.
     */
    public void login() {
        /*
            attemptLogin allows logging in from pressing enter from either text field
            as well as clicking the login button.
        */
        Action attemptLogin = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = passText.getText();
                Document data = null;
                boolean loggedIn = false;
                //System.out.println("Username: " + username);
                //System.out.println("Password: " + password);
                if (username.isEmpty() && password.isEmpty()) {
                    correct.setText("Please enter login credentials.");
                }
                else if (username.isEmpty()) {
                    correct.setText("Please enter a username.");
                }
                else if (password.isEmpty()) {
                    correct.setText("Please enter a password.");
                }
                //Checks if the username exists in the database, if so, close the window.
                else {
                    correct.setText("");
                    for (Document document : usernames.find(eq("username", username))) {
                        if (document.get("password").equals(password)) {
                            loggedIn = true;
                            data = document;
                            System.out.println(data);
                        }
                    }
                    if (loggedIn) {
                        if ((Integer)data.get("stockNum") == 0) {
                            loginFrame.dispose();
                            InformationGui ig = new InformationGui(usernames, data);
                            ig.enterInfo();
                        }
                        else {
                            loginFrame.dispose();
                            WelcomeGui wg = new WelcomeGui(usernames, data);
                            wg.welcome();
                        }

                    }
                    else {
                        correct.setText("Incorrect username/password.");
                    }
                }
            }
        };

        JPanel panel = new JPanel();
        panel.setBackground(background);

        loginFrame = new JFrame();
        loginFrame.setSize(400,350);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setUndecorated(true);
        loginFrame.add(panel);

        panel.setLayout(null);

        JLabel logLabel = new JLabel("Login");
        logLabel.setBounds((loginFrame.getWidth()/2)-50, (loginFrame.getHeight()/4)-20, 100, 60);
        logLabel.setFont(new Font("Montserrat", Font.PLAIN, 40));
        logLabel.setForeground(Color.WHITE);
        panel.add(logLabel);

        userText = new JTextField(20);
        userText.setBounds(50,150,300,35);
        userText.setFont(new Font("Montserrat", Font.PLAIN, 15));
        userText.addActionListener(attemptLogin);
        userText.setBackground(background);
        userText.setForeground(Color.WHITE);
        userText.setBorder(border);
        panel.add(userText);

        //Allows for "ghost text" to disappear and reappear for username and password fields
        TextPrompt userPrompt = new TextPrompt("Username", userText);
        userPrompt.changeAlpha(.6f);
        userPrompt.setShow(TextPrompt.Show.FOCUS_LOST);

        passText = new JPasswordField(20);
        passText.setBounds(50,200,300,35);
        passText.setFont(new Font("Montserrat", Font.PLAIN, 15));
        passText.addActionListener(attemptLogin);
        passText.setBackground(background);
        passText.setForeground(Color.WHITE);
        passText.setBorder(border);
        panel.add(passText);

        TextPrompt passPrompt = new TextPrompt("Password", passText);
        passPrompt.changeAlpha(.6f);
        passPrompt.setShow(TextPrompt.Show.FOCUS_LOST);

        JButton login = new JButton("Login");
        login.setFont(new Font("Montserrat", Font.BOLD, 14));
        login.setBounds(50, 250, 300, 30);
        login.setForeground(Color.WHITE);
        login.setBackground(new Color(197,76,76));
        login.setBorder(BorderFactory.createEmptyBorder());
        login.setFocusPainted(false);
        login.addActionListener(attemptLogin);
        panel.add(login);

        /*
            This button opens up a Register window and sends the current login instance and collection
            to be used. Hides the current login GUI.
         */
        JButton register = new JButton("Sign Up");
        register.setBounds((loginFrame.getWidth()/2)-40, 290, 80, 30);
        register.setFont(new Font("Montserrat", Font.BOLD, 14));
        register.setForeground(Color.WHITE);
        register.setBackground(background);
        register.setBorder(BorderFactory.createEmptyBorder());
        register.setFocusPainted(false);
        register.addActionListener(e -> {
            RegisterGui rg = new RegisterGui(usernames);
            rg.createNewUser(this);
            loginFrame.setVisible(false);
        });
        panel.add(register);

        //Clicking the "X" in the gui should close out the login window
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
                System.exit(0);
            }
        });
        panel.add(quit);

        correct = new JLabel("");
        correct.setFont(new Font("Montserrat", Font.BOLD, 14));
        correct.setForeground(Color.WHITE);
        correct.setBounds(50,125,300,25);
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
