import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

public class ProgressGUI {
    private JPanel panel;
    private JButton buttonCancel;
    private JPanel panelInfo;
    private JLabel labelTitle;

    private JLabel[] jobsIcon;

    private final ImageIcon ICON_SUCCESS = new ImageIcon("./res/success.png");
    private final ImageIcon ICON_ERROR = new ImageIcon("./res/error.png");
    private final ImageIcon ICON_LOADING = new ImageIcon("./res/loading.gif");

    public ProgressGUI(String title, String[] jobs) {
        GridBagConstraints c = new GridBagConstraints();
        jobsIcon = new JLabel[jobs.length];
        labelTitle.setText("<html><span style='font-size:40px'> "+ title + "</span></html>");
        c.gridy = 0;
        for (int i = 0; i < jobs.length; i++) {
            c.gridx = 0;
            jobsIcon[i] = new JLabel(ICON_LOADING);
            panelInfo.add(jobsIcon[i], c);
            c.gridx = 1;
            panelInfo.add(new JLabel("<html><span style='font-size:20px'> "+ jobs[i] + "</span></html>"), c);
            c.gridy++;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Progress");
        frame.setContentPane(new ProgressGUI("Test", new String[]{"this", "is", "a", "test"}).panel);
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
