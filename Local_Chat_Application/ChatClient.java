import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

public class ChatClient {

    private DatagramSocket socket;
    private JTextArea textArea;
    private JTextField textField;
    private JButton sendButton;
    private InetAddress serverAddress;
    private int serverPort = 1234;

    public ChatClient() {
        try {
            socket = new DatagramSocket(9876);
            serverAddress = InetAddress.getByName("localhost"); // Change if server is on a different machine
            createGUI();
            startListening();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createGUI() {
        JFrame frame = new JFrame("Chat Client");
        textArea = new JTextArea(20, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textField = new JTextField(30);
        sendButton = new JButton("Send");

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        frame.add(panel, BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void sendMessage() {
        try {
            String message = textField.getText();
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, serverPort);
            socket.send(packet);
            textArea.append("Client: " + message + "\n");
            textField.setText("");

            if (message.trim().equalsIgnoreCase("stop")) {
                textArea.append("Chat stopped by client.\n");
                socket.close();
                System.exit(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startListening() {
        Thread listenThread = new Thread(() -> {
            try {
                while (true) {
                    byte[] data = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(data, data.length);
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength());
                    textArea.append("Server: " + message + "\n");

                    if (message.trim().equalsIgnoreCase("stop")) {
                        textArea.append("Chat stopped by server.\n");
                        socket.close();
                        System.exit(0);
                    }
                }
            } catch (Exception e) {
                if (!socket.isClosed()) {
                    e.printStackTrace();
                }
            }
        });
        listenThread.start();
    }

    public static void main(String[] args) {
        new ChatClient();
    }
}
