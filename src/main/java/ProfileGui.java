import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileGui implements ActionListener {
    private JFrame infoFrame;
    private MongoCollection<Document> usernames;
    private Document data;
    private Color background = new Color(33,33,33);

    public ProfileGui(MongoCollection<Document> collection, Document userData) {
        usernames = collection;
        data = userData;
    }

    public void editProfile() {
        JPanel panel = new JPanel();
        panel.setBackground(background);

        infoFrame = new JFrame();
        infoFrame.setSize(400,475);
        infoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        infoFrame.setUndecorated(true);
        infoFrame.add(panel);

        panel.setLayout(null);

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

        infoFrame.setLocationRelativeTo(null);
        infoFrame.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
