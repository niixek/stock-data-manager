import javax.swing.*;
import java.awt.*;


public class Gui extends JFrame{
    private JLabel label;
    private JLabel imgLabel;
    private JButton button;
    private JTextField textfield;
    private ImageIcon image1;

    public Gui() {
        setLayout(new FlowLayout());

        image1 = new ImageIcon(getClass().getResource("image1.png"));

        imgLabel = new JLabel(image1);
        add(imgLabel);

        label = new JLabel("Label");
        add(label);

        textfield = new JTextField(15);
        add(textfield);

        button = new JButton("Button");
        add(button);

    }

    public static void main(String[] args){
        Gui window = new Gui();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(500,500);
        window.setVisible(true);
        window.setTitle("Stock Data Manager");
        window.pack();
    }
}
