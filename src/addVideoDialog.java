import javax.swing.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class addVideoDialog {
    private JPanel panel;
    private JComboBox<String> comboBox1;
    private JButton buttonCancel;
    private JButton buttonSubmit;
    private JTextField textField1;

    private ProgressGUI p;

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
        buttonCancel.addActionListener(e -> frame.setVisible(false));
        buttonSubmit.addActionListener(e -> {
            p = new ProgressGUI("Adding video...", new String[]{
                    "Get Details", "Save Dialog reference", "Add element"
            });
            String id;
            int sourcetype;
            int stage = 0;
            try {
                id = textField1.getText();
                sourcetype = comboBox1.getSelectedIndex();
                p.setDone(stage);

                stage = 1;
                c.addVideoDialogs(id, this);
                p.setDone(stage);

                stage = 2;
                String type;
                switch (sourcetype) {
                    case 0: {
                        type = "video";
                        break;
                    }
                    case 1: {
                        type = "playlist";
                        break;
                    }
                    case 2: {
                        type = "channel";
                        break;
                    }
                    default: {
                        throw new UnknownInputException("Don't know what to do with given source type: " + sourcetype);
                    }
                }
                c.sendMessage("?add;/" + type + ";/" + id);
            } catch (Exception ex) {
                ex.printStackTrace();
                p.setError(stage, ex.getMessage());
            }
        });
    }

    public ProgressGUI getP() {
        return p;
    }
}
