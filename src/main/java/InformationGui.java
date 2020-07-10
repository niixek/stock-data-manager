import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InformationGui implements ActionListener {
    private JFrame infoFrame;
    private JTextField dateText;
    private JTextField stockText;
    private MongoCollection<Document> usernames;
    private String username;

    public InformationGui(MongoCollection<Document> collection, String user) {
        usernames = collection;
        username = user;
    }


    public void enterInfo() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(52,52,52));

        infoFrame = new JFrame();
        infoFrame.setSize(400,600);
        infoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        infoFrame.setUndecorated(true);
        infoFrame.add(panel);

        panel.setLayout(null);

        JLabel infoLabel = new JLabel("Enter Stock Information");
        infoLabel.setBounds(40, 30, 325, 60);
        infoLabel.setFont(new Font("Montserrat", Font.PLAIN, 30));
        infoLabel.setForeground(Color.WHITE);
        panel.add(infoLabel);

        JLabel dateLabel = new JLabel("Start Date");
        dateLabel.setBounds(10, 20, 80, 25);
        dateLabel.setFont(new Font("Montserrat", Font.PLAIN, 14));
        panel.add(dateLabel);

        dateText = new JTextField(20);
        dateText.setBounds(40,100,300,35);
        dateText.setFont(new Font("Montserrat", Font.PLAIN, 14));
        //dateText.addActionListener(attemptLogin);
        dateText.setBorder(BorderFactory.createEmptyBorder());
        panel.add(dateText);


        /*



        dateText = new JTextField(20);
        dateText.setBounds(100,20,165,25);
        panel.add(dateText);

        JLabel stockLabel = new JLabel("Stock Name");
        stockLabel.setBounds(10, 60, 80, 25);
        panel.add(stockLabel);

        stockText = new JTextField(20);
        stockText.setBounds(100,60,165,25);
        panel.add(stockText);

         */

        infoFrame.setLocationRelativeTo(null);
        infoFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
