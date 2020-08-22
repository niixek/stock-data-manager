import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Filters.eq;

public class StockGui implements ActionListener {
    private JFrame frame;
    private MongoCollection<Document> usernames;
    private JComboBox<String> select;
    private Document data;
    private JButton conbutton;
    private String selectedStock;

    private JFrame viewFrame;
    private Color background = new Color(33,33,33);
    private MatteBorder border = BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE);

    public StockGui(MongoCollection<Document> collection, Document userData) {
        usernames = collection;
        data = userData;
    }

    public void selectStock() {
        JPanel panel = new JPanel();
        panel.setBackground(background);

        panel.setLayout(null);

        frame = new JFrame();
        frame.setSize(400,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.add(panel);

        JLabel text = new JLabel("Select a Stock");
        text.setBounds(40,30,320,35);
        text.setFont(new Font("Montserrat", Font.PLAIN, 40));
        text.setForeground(Color.WHITE);
        panel.add(text);

        String[] stocks = getStocks();
        select = new JComboBox<>(stocks);
        select.setUI(new BasicComboBoxUI());
        select.setBounds(40,80,320,35);
        select.setFont(new Font("Montserrat", Font.PLAIN, 16));
        select.setBackground(background);
        select.setForeground(Color.WHITE);
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

        conbutton = new JButton("Continue");
        conbutton.setFont(new Font("Montserrat", Font.BOLD, 14));
        conbutton.setBounds(40, 140, 320, 30);
        conbutton.setForeground(Color.WHITE);
        conbutton.setBackground(new Color(197,76,76));
        conbutton.setBorder(BorderFactory.createEmptyBorder());
        conbutton.setFocusPainted(false);
        conbutton.addActionListener(this);
        panel.add(conbutton);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public String[] getStocks() {
        String[] stockList = new String[data.getInteger("stockNum")];
        for(int i = 1; i <= data.getInteger("stockNum"); i++) {
            Document stock = (Document) data.get("stock" + i);
            String name = stock.getString("stockName");
            stockList[i-1] = name;
        }
        return stockList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        selectedStock = (String)select.getSelectedItem();
        frame.dispose();
        viewStock(selectedStock);
    }

    public void viewStock(String selectedStock) {
        JPanel panel = new JPanel();
        panel.setBackground(background);

        panel.setLayout(null);

        viewFrame = new JFrame();
        viewFrame.setSize(400,475);
        viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewFrame.setUndecorated(true);
        viewFrame.setLocationRelativeTo(null);
        viewFrame.setVisible(true);
        viewFrame.add(panel);

        JLabel text = new JLabel(selectedStock);
        text.setBounds(40,30,320,35);
        text.setFont(new Font("Montserrat", Font.PLAIN, 40));
        text.setForeground(Color.WHITE);
        panel.add(text);

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

        viewFrame.setLocationRelativeTo(null);
        viewFrame.setVisible(true);
    }
}
