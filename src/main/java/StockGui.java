import com.mongodb.client.MongoCollection;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bson.Document;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;

public class StockGui implements ActionListener {
    private JFrame frame;
    private MongoCollection<Document> usernames;
    private JComboBox<String> select;
    private Document data;
    private String selectedStock;
    private String[] stocks;
    private ArrayList<Pair<Document, String>> stockDocs;

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

        getStocks();
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

        JButton conbutton = new JButton("Continue");
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

    public void getStocks() {
        ArrayList<Pair<Document, String>> stockList = new ArrayList<>();
        String[] stockListNames = new String[data.getInteger("stockNum")];
        for (int i = 1; i <= data.getInteger("stockNum"); i++) {
            Document stock = (Document) data.get("stock" + i);
            String name = stock.getString("stockName");
            Pair<Document, String> pair = new ImmutablePair<>(stock, name);
            stockList.add(pair);
            stockListNames[i-1] = name;
        }
        stocks = stockListNames;
        stockDocs = stockList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        selectedStock = (String)select.getSelectedItem();
        Document ssData = null;
        for (Pair<Document, String> stockDoc : stockDocs) {
            if (stockDoc.getRight().equals(selectedStock)) {
                ssData = stockDoc.getLeft();
            }
        }
        frame.dispose();
        assert ssData != null;
        viewStock(ssData);
    }

    public void viewStock(Document selectedStock) {
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

        JLabel stockName = new JLabel(selectedStock.getString("stockName"));
        stockName.setBounds(40,30,320,45);
        stockName.setFont(new Font("Montserrat", Font.BOLD, 40));
        stockName.setForeground(Color.WHITE);
        panel.add(stockName);

        JLabel startLabel = new JLabel("Start Date:");
        startLabel.setBounds(40,80,320,45);
        startLabel.setFont(new Font("Montserrat", Font.PLAIN, 24));
        startLabel.setForeground(Color.WHITE);
        panel.add(startLabel);

        LocalDate sDate = selectedStock.getDate("startDate").toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        JLabel start = new JLabel(sDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        start.setBounds(40,105,320,45);
        start.setFont(new Font("Montserrat", Font.PLAIN, 18));
        start.setForeground(Color.WHITE);
        panel.add(start);

        JLabel shares = new JLabel("You have " + selectedStock.getInteger("quantity") + " shares.");
        shares.setBounds(40,150,320,45);
        shares.setFont(new Font("Montserrat", Font.PLAIN, 20));
        shares.setForeground(Color.WHITE);
        panel.add(shares);

        JLabel initPriceLabel = new JLabel("The price you bought them at:");
        initPriceLabel.setBounds(40,175,320,45);
        initPriceLabel.setFont(new Font("Montserrat", Font.PLAIN, 20));
        initPriceLabel.setForeground(Color.WHITE);
        panel.add(initPriceLabel);

        JLabel initPrice = new JLabel("$" + selectedStock.getString("price"), SwingConstants.CENTER);
        initPrice.setBounds(40,205,320,45);
        initPrice.setFont(new Font("Montserrat", Font.BOLD, 20));
        initPrice.setForeground(Color.WHITE);
        panel.add(initPrice);

        JLabel initTotalPrice = new JLabel("Your starting total:");
        initTotalPrice.setBounds(40,250,320,45);
        initTotalPrice.setFont(new Font("Montserrat", Font.PLAIN, 20));
        initTotalPrice.setForeground(Color.WHITE);
        panel.add(initTotalPrice);

        JLabel initTotal = new JLabel("$" + selectedStock.getString("total"), SwingConstants.CENTER);
        initTotal.setBounds(40,275,320,45);
        initTotal.setFont(new Font("Montserrat", Font.BOLD, 20));
        initTotal.setForeground(Color.WHITE);
        panel.add(initTotal);

        JLabel lastUpdLabel = new JLabel("Last Updated:");
        lastUpdLabel.setBounds(40,310,320,45);
        lastUpdLabel.setFont(new Font("Montserrat", Font.PLAIN, 20));
        lastUpdLabel.setForeground(Color.WHITE);
        panel.add(lastUpdLabel);

        LocalDate lastUpdated = selectedStock.getDate("lastUpdated").toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        JLabel lastDateUpd = new JLabel(lastUpdated.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        lastDateUpd.setBounds(40,335,320,45);
        lastDateUpd.setFont(new Font("Montserrat", Font.PLAIN, 18));
        lastDateUpd.setForeground(Color.WHITE);
        panel.add(lastDateUpd);

        JLabel profit = new JLabel("Total Profit:");
        profit.setBounds(40,360,320,45);
        profit.setFont(new Font("Montserrat", Font.PLAIN, 20));
        profit.setForeground(Color.WHITE);
        panel.add(profit);

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
