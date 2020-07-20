import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Hashtable;

public class InformationGui implements ActionListener {
    private JFrame infoFrame;
    private JTextField stockText;
    private JTextField quantText;
    private JTextField priceText;
    private JComboBox<String> month;
    private JComboBox<String> day;
    private JComboBox<String> year;
    private Hashtable<String, String[]> daysMap = new Hashtable<>();
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
        month.setBounds(40,120,115,35);
        month.setFont(new Font("Montserrat", Font.PLAIN, 15));
        month.setBackground(background);
        month.setForeground(Color.WHITE);
        month.setUI(new BasicComboBoxUI());

        day = new JComboBox<>(days);
        day.setBounds(170,120,60,35);
        day.setFont(new Font("Montserrat", Font.PLAIN, 15));
        day.setBackground(background);
        day.setForeground(Color.WHITE);
        day.setUI(new BasicComboBoxUI());

        year = new JComboBox<>(years);
        year.setBounds(250,120,80,35);
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

        stockText = new JTextField(20);
        stockText.setBounds(40,170,300,35);
        stockText.setFont(new Font("Montserrat", Font.PLAIN, 15));
        stockText.setBackground(background);
        stockText.setForeground(Color.WHITE);
        stockText.setBorder(border);
        panel.add(stockText);

        quantText = new JTextField(20);
        quantText.setBounds(40,220,300,35);
        quantText.setFont(new Font("Montserrat", Font.PLAIN, 15));
        quantText.setBackground(background);
        quantText.setForeground(Color.WHITE);
        quantText.setBorder(border);
        panel.add(quantText);

        priceText = new JTextField(20);
        priceText.setBounds(40,270,300,35);
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

        TextPrompt pricePrompt = new TextPrompt("Initial Price ($)", priceText);
        pricePrompt.changeAlpha(.6f);
        pricePrompt.setShow(TextPrompt.Show.FOCUS_LOST);

        month.addActionListener(dateListener);
        panel.add(month);
        panel.add(day);
        panel.add(year);

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
