import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GUI {
    private JPanel panel;
    private JTextField textFieldIp;
    private JTextField textFieldPort;
    private JTable tableVideos;
    private JTable tableMessages;
    private JButton buttonAddVideo;
    private JButton buttonAdvanced;
    private JButton buttonConnect;
    private JButton buttonRefresh;

    private Client client;

    public GUI() {
        buttonConnect.addActionListener(e -> {
            try {
                client = new Client(textFieldIp.getText(), Integer.parseInt(textFieldPort.getText()), GUI.this);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        tableVideos.setModel(new DefaultTableModel());
        buttonRefresh.addActionListener(e -> {
            client.sendMessage("?status");
            client.sendMessage("?messages");
        });
        buttonAddVideo.addActionListener(e -> {
            new addVideoDialog(client);
        });
    }

    public JTable getTableVideos() {
        return tableVideos;
    }

    public JTable getTableMessages() {
        return tableMessages;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new GUI().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        frame.setVisible(true);
    }


}
