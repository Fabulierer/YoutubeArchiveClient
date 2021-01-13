import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class addVideoDialog {
    private JPanel panel;
    private JComboBox<String> comboBox1;
    private JButton buttonCancel;
    private JButton buttonSubmit;
    private JTextField textField1;

    public addVideoDialog(Client c) {
        JFrame frame = new JFrame("Add video");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        frame.setVisible(true);
        comboBox1.addItem("add video");
        comboBox1.addItem("add playlist");
        comboBox1.addItem("add channel");
        buttonCancel.addActionListener(e -> {
            frame.setVisible(false);
        });
        buttonSubmit.addActionListener(e -> {

        });
    }

}
