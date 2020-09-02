import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeGui implements ActionListener {
    private JFrame welcomeFrame;
    private MongoCollection<Document> usernames;
    private Document data;
    private Color background = new Color(33,33,33);

    public WelcomeGui(MongoCollection<Document> collection, Document userData) {
        usernames = collection;
        data = userData;
    }

    public void welcome() {
        JPanel panel = new JPanel();
        panel.setBackground(background);

        panel.setLayout(null);

        welcomeFrame = new JFrame();
        welcomeFrame.setSize(800,500);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setUndecorated(true);
        welcomeFrame.add(panel);

        String welcomeString = "Welcome, " + data.get("username") + ".";
        JLabel bigWelcome = new JLabel(welcomeString);
        bigWelcome.setBounds(50,50,800,75);
        bigWelcome.setFont(new Font("Montserrat", Font.PLAIN, 60));
        bigWelcome.setForeground(Color.WHITE);
        panel.add(bigWelcome);

        ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/main/java/squidward money.gif").getImage().getScaledInstance(350, 262, Image.SCALE_DEFAULT));
        JLabel image = new JLabel(imageIcon);
        image.setBounds(50, 150, 350, 262);
        panel.add(image);

        JButton newStock = new JButton("Enter a New Stock");
        newStock.setFont(new Font("Montserrat", Font.BOLD, 14));
        newStock.setBounds(450, 175, 300, 30);
        newStock.setForeground(Color.WHITE);
        newStock.setBackground(new Color(197,76,76));
        newStock.setBorder(BorderFactory.createEmptyBorder());
        newStock.setFocusPainted(false);
        newStock.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InformationGui ig = new InformationGui(usernames, data);
                ig.enterInfo();
                welcomeFrame.dispose();
            }
        });
        panel.add(newStock);

        JButton oldStock = new JButton("View an Existing Stock");
        oldStock.setFont(new Font("Montserrat", Font.BOLD, 14));
        oldStock.setBounds(450, 225, 300, 30);
        oldStock.setForeground(Color.WHITE);
        oldStock.setBackground(new Color(197,76,76));
        oldStock.setBorder(BorderFactory.createEmptyBorder());
        oldStock.setFocusPainted(false);
        oldStock.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StockGui sg = new StockGui(usernames, data);
                sg.selectStock();
                welcomeFrame.dispose();
            }
        });
        panel.add(oldStock);

        JButton addInfo = new JButton("Enter Stock Data");
        addInfo.setFont(new Font("Montserrat", Font.BOLD, 14));
        addInfo.setBounds(450, 275, 300, 30);
        addInfo.setForeground(Color.WHITE);
        addInfo.setBackground(new Color(197,76,76));
        addInfo.setBorder(BorderFactory.createEmptyBorder());
        addInfo.setFocusPainted(false);
        addInfo.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EnterDataGui edg = new EnterDataGui(usernames, data);
                edg.enterData();
                welcomeFrame.dispose();
            }
        });
        panel.add(addInfo);

        JButton profile = new JButton("Edit Your Profile");
        profile.setFont(new Font("Montserrat", Font.BOLD, 14));
        profile.setBounds(450, 325, 300, 30);
        profile.setForeground(Color.WHITE);
        profile.setBackground(new Color(197,76,76));
        profile.setBorder(BorderFactory.createEmptyBorder());
        profile.setFocusPainted(false);
        profile.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProfileGui pg = new ProfileGui(usernames, data);
                pg.editProfile();
                welcomeFrame.dispose();
            }
        });
        panel.add(profile);

        JButton quit = new JButton("x");
        quit.setFont(new Font("Montserrat", Font.BOLD, 18));
        quit.setBounds(750, 20, 30, 20);
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

        welcomeFrame.setLocationRelativeTo(null);
        welcomeFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
