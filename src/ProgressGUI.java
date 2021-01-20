import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

public class ProgressGUI {
    private JPanel panel;
    private JButton buttonCancel;
    private JPanel panelInfo;
    private JLabel labelTitle;

    private JLabel[] jobsIcon;
    private GridBagConstraints c;

    private final ImageIcon ICON_SUCCESS = new ImageIcon("success.png");
    private final ImageIcon ICON_ERROR = new ImageIcon("error.png");
    private final ImageIcon ICON_LOADING = new ImageIcon("loading.gif");

    public ProgressGUI(String title, String[] jobs) {
        JFrame frame = new JFrame("Progress");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        c = new GridBagConstraints();
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
        buttonCancel.addActionListener(e -> frame.dispose());
        frame.setVisible(true);
    }

    public void setDone(int i) {
        jobsIcon[i].setIcon(ICON_SUCCESS);
    }

    public void setError(int i, String msg) {
        jobsIcon[i].setIcon(ICON_ERROR);
        JOptionPane.showMessageDialog(panel, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
