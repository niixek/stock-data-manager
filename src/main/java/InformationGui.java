import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InformationGui implements ActionListener {
    private JFrame infoFrame;
    private JTextField stockText;
    private MongoCollection<Document> usernames;
    private String username;

    public InformationGui(MongoCollection<Document> collection, String user) {
        usernames = collection;
        username = user;
    }


    public void enterInfo() {
        JPanel panel = new JPanel();

        infoFrame = new JFrame();
        infoFrame.setSize(800,400);
        infoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        infoFrame.setTitle("Stock Information");
        infoFrame.add(panel);

        panel.setLayout(null);

        JLabel stockLabel = new JLabel("Stock Name");
        stockLabel.setBounds(10, 20, 80, 25);
        panel.add(stockLabel);

        stockText = new JTextField(20);
        stockText.setBounds(100,20,165,25);
        panel.add(stockText);

        /*
        JButton login = new JButton("Login");
        login.setBounds(10, 80, 80, 25);
        login.addActionListener(attemptLogin);
        panel.add(login);

        JButton register = new JButton("Register");
        register.setBounds(100, 80, 90, 25);
        register.addActionListener(e -> {
            RegisterGui rg = new RegisterGui(usernames);
            rg.createNewUser(this);
            loginFrame.setVisible(false);
        });
        panel.add(register);

        correct = new JLabel("");
        correct.setBounds(10,110,300,25);
        panel.add(correct);

         */

        infoFrame.setLocationRelativeTo(null);
        infoFrame.setVisible(true);
    }








    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
