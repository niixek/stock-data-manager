import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class RegisterGui implements ActionListener{
    private static JLabel userLabel;
    private static JTextField userText;
    private static JLabel passLabel;
    private static JTextField passText;
    private static JButton login;
    private static JLabel correct;

    public void createNewUser() {
        JPanel panel = new JPanel();

        JFrame frame = new JFrame();
        frame.setSize(400,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Login");
        frame.add(panel);

        panel.setLayout(null);

        userLabel = new JLabel("Username");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        passLabel = new JLabel("Password");
        passLabel.setBounds(10, 50, 80, 25);
        panel.add(passLabel);

        passText = new JPasswordField(20);
        passText.setBounds(100,50,165,25);
        panel.add(passText);

        login = new JButton("Login");
        login.setBounds(10, 80, 80, 25);
        login.addActionListener(new LoginGui());
        panel.add(login);

        correct = new JLabel("");
        correct.setBounds(10,110,300,25);
        panel.add(correct);


        frame.setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
