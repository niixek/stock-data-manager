import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InformationGui implements ActionListener {
    private JFrame infoFrame;
    private JTextField dateText;
    private JTextField stockText;
    private JComboBox<String> month;
    private MongoCollection<Document> usernames;
    private String username;
    private Color background = new Color(33,33,33);
    private MatteBorder border = BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE);

    public InformationGui(MongoCollection<Document> collection, String user) {
        usernames = collection;
        username = user;
    }


    public void enterInfo() {
        JPanel panel = new JPanel();
        panel.setBackground(background);

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
        dateLabel.setBounds(40, 90, 80, 25);
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setFont(new Font("Montserrat", Font.PLAIN, 16));
        panel.add(dateLabel);

        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
                            "October", "November", "December"};

        month = new JComboBox<>(months);
        month.setBounds(40,115,300,30);
        month.setFont(new Font("Montserrat", Font.PLAIN, 14));
        month.setBackground(Color.WHITE);
        month.setBorder(border);
        panel.add(month);



        dateText = new JTextField(20);
        dateText.setBounds(40,160,300,35);
        dateText.setFont(new Font("Montserrat", Font.PLAIN, 14));
        //dateText.addActionListener(attemptLogin);
        dateText.setBackground(background);
        dateText.setForeground(Color.WHITE);
        dateText.setBorder(border);
        panel.add(dateText);

        //Clicking the "X" in the gui should close out the info window
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
