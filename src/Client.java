import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class Client {

    private Socket c;
    private DataInputStream input;
    private DataOutputStream output;

    private JTable tableVideos, tableMessages;
    private DefaultTableModel tableVideosModel, tableMessagesModel;
    private DefaultTableColumnModel tableVideosColumnModel, tableMessagesColumnModel;

    private HashMap<String, addVideoDialog> addVideoDialogs;


    public Client(String ip, int port, GUI gui) throws IOException {
        c = new Socket(ip, port);
        input = new DataInputStream(c.getInputStream());
        output = new DataOutputStream(c.getOutputStream());
        tableVideos = gui.getTableVideos();
        tableVideosModel = (DefaultTableModel) tableVideos.getModel();
        tableVideosColumnModel = (DefaultTableColumnModel) tableVideos.getColumnModel();
        tableMessages = gui.getTableMessages();
        tableMessagesModel = (DefaultTableModel) tableMessages.getModel();
        tableMessagesColumnModel = (DefaultTableColumnModel) tableMessages.getColumnModel();
        addVideoDialogs = new HashMap<>();

        listenerLoop();
    }

    public void sendMessage(String msg) {
        System.out.println("[OUT] " + msg);
        try {
            output.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenerLoop() {
        Thread t = new Thread(() -> {
            while (c.isConnected()) {
                try {
                    String i = input.readUTF();
                    String[] args = i.split(";/");
                    System.out.println("[IN] " + i);
                    switch (args[0]) {
                        case "+status": {
                            tableVideosModel.setColumnCount(0);
                            tableVideosModel.setRowCount(0);

                            tableVideosModel.addColumn("video title");
                            tableVideosModel.addColumn("video changed");
                            tableVideosModel.addColumn("channel name");
                            tableVideosModel.addColumn("last checked");
                            tableVideosModel.addColumn("audio changed");
                            tableVideosModel.addColumn("description changed");
                            tableVideosModel.addColumn("tags changed");
                            tableVideosModel.addColumn("thumbnail changed");
                            tableVideosModel.addColumn("title changed");

                            tableVideos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                            tableVideosColumnModel.getColumn(0).setPreferredWidth(100);
                            tableVideosColumnModel.getColumn(1).setPreferredWidth(100);
                            tableVideosColumnModel.getColumn(2).setPreferredWidth(100);
                            tableVideosColumnModel.getColumn(3).setPreferredWidth(10);
                            tableVideosColumnModel.getColumn(4).setPreferredWidth(10);
                            tableVideosColumnModel.getColumn(5).setPreferredWidth(10);
                            tableVideosColumnModel.getColumn(6).setPreferredWidth(10);
                            tableVideosColumnModel.getColumn(7).setPreferredWidth(10);
                            tableVideosColumnModel.getColumn(8).setPreferredWidth(10);

                            for (int j = 1; j < args.length; j++) {
                                String[] collumns = args[j].split(";");
                                tableVideosModel.addRow(collumns);
                            }
                            break;
                        }
                        case "+messages": {
                            tableMessagesModel.setColumnCount(0);
                            tableMessagesModel.setRowCount(0);

                            tableMessagesModel.addColumn("time");
                            tableMessagesModel.addColumn("message");
                            tableMessagesModel.addColumn("read");

                            tableMessages.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                            tableMessagesColumnModel.getColumn(0).setPreferredWidth(100);
                            tableMessagesColumnModel.getColumn(1).setPreferredWidth(100);
                            tableMessagesColumnModel.getColumn(2).setPreferredWidth(10);
                            break;
                        }
                        case "+add": {
                            String videoId = args[1];
                            addVideoDialog d = addVideoDialogs.get(videoId);
                            d.getP().setDone(2);
                            break;
                        }
                        case "-add": {
                            String videoId = args[1];
                            addVideoDialog d = addVideoDialogs.get(videoId);
                            d.getP().setError(2, args[2]);
                            break;
                        }
                        default: {
                            throw new UnknownInputException(i);
                        }
                    }

                } catch (IOException | UnknownInputException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public void addVideoDialogs(String id, addVideoDialog dialog) {
        addVideoDialogs.put(id, dialog);
    }

}
