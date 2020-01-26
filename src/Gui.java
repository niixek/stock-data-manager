import javax.swing.*;
import java.awt.*;


public class Gui extends JFrame{
    private JLabel label;
    private JButton button;
    private JTextField textfield;

    public Gui(){
        setLayout(new FlowLayout());

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
    }
}
