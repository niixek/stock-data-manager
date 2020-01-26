import javax.swing.*;

public class Gui extends JFrame{
    public static void main(String args[]){
        Gui window = new Gui();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(500,500);
        window.setVisible(true);
        window.setTitle("Stock Data Manager");
    }
}
