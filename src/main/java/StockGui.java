import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StockGui implements ActionListener {
    private JFrame frame;
    private MongoCollection<Document> usernames;
    private JComboBox<String> select;
    private String username;
    private Color background = new Color(33,33,33);
    private MatteBorder border = BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE);

    public StockGui(MongoCollection<Document> collection, String user) {
        usernames = collection;
        username = user;
    }

    public void viewStock() {
        JPanel panel = new JPanel();
        panel.setBackground(background);

        panel.setLayout(null);

        frame = new JFrame();
        frame.setSize(400,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.add(panel);

        JLabel text = new JLabel("Select a Stock", SwingConstants.CENTER);
        text.setBounds(40,30,320,35);
        text.setFont(new Font("Montserrat", Font.PLAIN, 40));
        text.setForeground(Color.WHITE);
        panel.add(text);

        select = new JComboBox<>();
        select.setBounds(40,125,115,35);
        select.setFont(new Font("Montserrat", Font.PLAIN, 15));
        select.setBackground(background);
        select.setForeground(Color.WHITE);
        select.setUI(new BasicComboBoxUI());
        panel.add(select);

        JButton quit = new JButton("x");
        quit.setFont(new Font("Montserrat", Font.BOLD, 18));
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

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
