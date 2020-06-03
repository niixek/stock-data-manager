import javax.swing.*;
import java.awt.*;

/*
public class Gui extends JFrame{
    public Gui() {
        setLayout(new FlowLayout());

        ImageIcon image1 = new ImageIcon(getClass().getResource("image1.png"));

        JLabel imgLabel = new JLabel(image1);
        imgLabel.setToolTipText("Welcome to the Stock Data Manager");
        imgLabel.setSize(50,50);
        add(imgLabel);

        JLabel label = new JLabel("Username");
        label.setBounds(10,20,80,25);
        add(label);

        JTextField textfield = new JTextField(15);
        add(textfield);

        JButton button = new JButton("Button");
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

 */

public class Gui {
    public static void main (String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }
}
