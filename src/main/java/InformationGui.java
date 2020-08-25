import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.eq;

public class InformationGui implements ActionListener {
    private JFrame infoFrame;
    private JTextField stockText;
    private JTextField quantText;
    private JTextField priceText;
    private JButton conbutton;
    private JButton confirm;
    private JLabel correct;
    private JLabel totText;
    private JComboBox<String> month;
    private JComboBox<String> day;
    private JComboBox<String> year;
    private Hashtable<String, String[]> daysMap = new Hashtable<>();
    private MongoCollection<Document> usernames;
    private Document data;
    private Color background = new Color(33,33,33);
    private MatteBorder border = BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE);

    public InformationGui(MongoCollection<Document> collection, Document userData) {
        usernames = collection;
        data = userData;
    }

    public void enterInfo() {
        JPanel panel = new JPanel();
        panel.setBackground(background);

        infoFrame = new JFrame();
        infoFrame.setSize(400,475);
        infoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        infoFrame.setUndecorated(true);
        infoFrame.add(panel);

        panel.setLayout(null);

        JLabel infoLabel = new JLabel("Enter Stock Information");
        infoLabel.setBounds(40, 30, 325, 60);
        infoLabel.setFont(new Font("Montserrat", Font.PLAIN, 30));
        infoLabel.setForeground(Color.WHITE);
        panel.add(infoLabel);

        JLabel dateLabel = new JLabel("Start Date:");
        dateLabel.setBounds(40, 90, 80, 30);
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setFont(new Font("Montserrat", Font.PLAIN, 16));
        panel.add(dateLabel);

        //months and days are used for the JComboBoxes to select a date
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
                            "October", "November", "December"};
        String[] days = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
                         "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

        String[] years = {"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017",
                "2018", "2019", "2020"};

        /*
            This creates 3 subarrays for the possible number of days for each corresponding month. They are put
            into a Hashtable, which is later used to obtain the correct number of days for the selected month.
         */
        String[] thirty = Arrays.stream(days, 0, 30).toArray(String[]::new);
        daysMap.put(months[3], thirty);
        daysMap.put(months[5], thirty);
        daysMap.put(months[8], thirty);
        daysMap.put(months[10], thirty);

        String[] twentyNine = Arrays.stream(days, 0, 29).toArray(String[]::new);
        daysMap.put(months[1], twentyNine);

        String[] thirtyOne = Arrays.stream(days, 0, 31).toArray(String[]::new);
        daysMap.put(months[0], thirtyOne);
        daysMap.put(months[2], thirtyOne);
        daysMap.put(months[4], thirtyOne);
        daysMap.put(months[6], thirtyOne);
        daysMap.put(months[7], thirtyOne);
        daysMap.put(months[9], thirtyOne);
        daysMap.put(months[11], thirtyOne);

        month = new JComboBox<>(months);
        month.setBounds(40,125,115,35);
        month.setFont(new Font("Montserrat", Font.PLAIN, 15));
        month.setBackground(background);
        month.setForeground(Color.WHITE);
        month.setUI(new BasicComboBoxUI());

        day = new JComboBox<>(days);
        day.setBounds(170,125,60,35);
        day.setFont(new Font("Montserrat", Font.PLAIN, 15));
        day.setBackground(background);
        day.setForeground(Color.WHITE);
        day.setUI(new BasicComboBoxUI());

        year = new JComboBox<>(years);
        year.setBounds(250,125,80,35);
        year.setFont(new Font("Montserrat", Font.PLAIN, 15));
        year.setBackground(background);
        year.setForeground(Color.WHITE);
        year.setUI(new BasicComboBoxUI());

        //dateListener allows the day JComboBox to change the range of possible number of days based on the selected month
        Action dateListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choice = (String) month.getSelectedItem();
                assert choice != null;
                Object obj = daysMap.get(choice);

                day.setModel(new DefaultComboBoxModel<>((String[])obj));
            }
        };

        month.addActionListener(dateListener);
        panel.add(month);
        panel.add(day);
        panel.add(year);


        stockText = new JTextField(20);
        stockText.setBounds(40,170,320,35);
        stockText.setFont(new Font("Montserrat", Font.PLAIN, 15));
        stockText.setBackground(background);
        stockText.setForeground(Color.WHITE);
        stockText.setBorder(border);
        panel.add(stockText);

        quantText = new JTextField(20);
        //Code gotten from online, makes it so that only numbers can be typed
        ((AbstractDocument)quantText.getDocument()).setDocumentFilter(new DocumentFilter(){
            Pattern regEx = Pattern.compile("\\d*");

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                Matcher matcher = regEx.matcher(text);
                if(!matcher.matches()){
                    return;
                }
                super.replace(fb, offset, length, text, attrs);
            }
        });
        quantText.setBounds(40,220,320,35);
        quantText.setFont(new Font("Montserrat", Font.PLAIN, 15));
        quantText.setBackground(background);
        quantText.setForeground(Color.WHITE);
        quantText.setBorder(border);
        panel.add(quantText);

        JLabel sign1 = new JLabel("$");
        sign1.setBounds(40,270,20,35);
        sign1.setFont(new Font("Montserrat", Font.PLAIN, 18));
        sign1.setForeground(Color.WHITE);
        panel.add(sign1);

        priceText = new JTextField(20);
        priceText.setBounds(60,270,300,35);
        priceText.setFont(new Font("Montserrat", Font.PLAIN, 15));
        priceText.setBackground(background);
        priceText.setForeground(Color.WHITE);
        priceText.setBorder(border);
        panel.add(priceText);

        //Allows for "ghost text" to disappear and reappear for username and password fields
        TextPrompt stockPrompt = new TextPrompt("Stock Name", stockText);
        stockPrompt.changeAlpha(.6f);
        stockPrompt.setShow(TextPrompt.Show.FOCUS_LOST);

        TextPrompt quantPrompt = new TextPrompt("Quantity", quantText);
        quantPrompt.changeAlpha(.6f);
        quantPrompt.setShow(TextPrompt.Show.FOCUS_LOST);

        TextPrompt pricePrompt = new TextPrompt("Initial Stock Price (ex: 123.45)", priceText);
        pricePrompt.changeAlpha(.6f);
        pricePrompt.setShow(TextPrompt.Show.FOCUS_LOST);

        JLabel sign2 = new JLabel("Your total cost is:");
        sign2.setBounds(40,310,200,35);
        sign2.setFont(new Font("Montserrat", Font.PLAIN, 18));
        sign2.setForeground(Color.WHITE);
        panel.add(sign2);

        conbutton = new JButton("Continue");
        conbutton.setFont(new Font("Montserrat", Font.BOLD, 14));
        conbutton.setBounds(40, 410, 320, 30);
        conbutton.setForeground(Color.WHITE);
        conbutton.setBackground(new Color(197,76,76));
        conbutton.setBorder(BorderFactory.createEmptyBorder());
        conbutton.setFocusPainted(false);
        conbutton.addActionListener(this);
        panel.add(conbutton);

        confirm = new JButton("Confirm");
        confirm.setFont(new Font("Montserrat", Font.BOLD, 14));
        confirm.setBounds(40, 410, 320, 30);
        confirm.setForeground(Color.WHITE);
        confirm.setBackground(new Color(197,76,76));
        confirm.setBorder(BorderFactory.createEmptyBorder());
        confirm.setFocusPainted(false);
        confirm.setVisible(false);
        panel.add(confirm);

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

        correct = new JLabel("");
        correct.setFont(new Font("Montserrat", Font.BOLD, 14));
        correct.setForeground(Color.WHITE);
        correct.setBounds(40,380,300,25);
        panel.add(correct);

        totText = new JLabel("Press continue to view...", SwingConstants.CENTER);
        totText.setFont(new Font("Montserrat", Font.PLAIN, 14));
        totText.setForeground(Color.GRAY);
        totText.setBounds(40,342,300,25);
        panel.add(totText);

        infoFrame.setLocationRelativeTo(null);
        infoFrame.setVisible(true);
    }

    public void confirming(Document toUpdate, String cost) {
        totText.setForeground(Color.WHITE);
        totText.setFont(new Font("Montserrat", Font.BOLD, 18));
        totText.setText("$ " + cost);
        conbutton.setVisible(false);
        confirm.setVisible(true);
        correct.setText("Press confirm to continue.");
        confirm.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numStock = data.getInteger("stockNum") + 1;
                usernames.updateOne(data, toUpdate);
                Document stockInc = new Document("stockNum", numStock);
                Document update = new Document("$set", stockInc);
                usernames.updateOne(data, update);
                infoFrame.dispose();
                updateData(data);
                WelcomeGui wg = new WelcomeGui(usernames, data);
                wg.welcome();
            }
        });
    }

    public void updateData(Document oldDoc) {
        for (Document document : usernames.find(eq("username", oldDoc.get("username")))) {
            data = document;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int yearInt = Integer.parseInt((String) Objects.requireNonNull(year.getSelectedItem()));
        int monthInt = month.getSelectedIndex() + 1;
        int dayInt = day.getSelectedIndex() + 1;
        LocalDate date = LocalDate.of(yearInt, monthInt, dayInt);
        String stock = stockText.getText().trim();
        int quantity = -1;
        String price = priceText.getText();

        double priceConverted;

        Pattern regex = Pattern.compile("^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*\\.[0-9]{2}$");
        Matcher matcher = regex.matcher(price);

        try {
            quantity = Integer.parseInt(quantText.getText());
        } catch (NumberFormatException ignored) {
        }
        if (stock.length() == 0) {
            correct.setText("Please enter a stock name.");
        }
        else if (quantity < 0) {
            correct.setText("Please enter a quantity.");
        }
        else if (!(matcher.matches())) {
            correct.setText("Please enter currency correctly.");
        }
        else {
            correct.setText("");

            priceConverted = Double.parseDouble(price);
            double totalCost = priceConverted * quantity;
            DecimalFormat df = new DecimalFormat("##.00");
            int numStock = data.getInteger("stockNum") + 1;
            String stockNum = "stock" + numStock;

            Document stockData = new Document("startDate", date).append("stockName", stock).append("quantity", quantity).append("price", df.format(priceConverted)).append("total", df.format(totalCost));
            Document group = new Document(stockNum, stockData);
            Document update = new Document("$set", group);
            confirming(update, df.format(totalCost));
        }
    }
}
