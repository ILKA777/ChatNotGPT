package ru.ilka.chat.client;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import ru.ilka.chat.common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientUI extends JDialog implements ConnectionListener {
    private Connection c;
    private JPanel contentPane;
    private JButton buttonSend;
    private JTextArea textArea1;
    private JTextField a127001TextField;
    private JButton connectButton;
    private JTextField textField1;


    public ClientUI() {
        setContentPane(contentPane);
        setModal(true);
        setState(false);

        buttonSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String content = textField1.getText().trim();
                    Message msg = new MessageImplementation
                            (InetAddress.getByName("localhost").getHostName(), content, Message.CONTENT_TYPE);
                    c.send(msg);
                } catch (UnknownHostException ex) {
                    ex.printStackTrace();
                }
            }
        });

        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String host = a127001TextField.getText().trim();
                    Socket socket = new Socket(InetAddress.getByName(host), Connection.PORT);
                    c = new ConnectionImplementation(socket, ClientUI.this);
                    connectionCreated(c);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public static void main(String[] args) {
        ClientUI dialog = new ClientUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    @Override
    public void connectionCreated(Connection c) {
        System.out.println("Client connection was created");
        setState(true);
        this.c = c;
    }

    @Override
    public void connectionClosed(Connection c) {
        System.out.println("Client connection was closed");
    }

    @Override
    public void connectionException(Connection c, Exception ex) {
        ex.printStackTrace();

    }

    @Override
    public void recievedContent(Message msg) {
        textArea1.append("\n");
        textArea1.append(msg.toString());
    }

    private void setState(boolean isConnected) {
        textArea1.setEnabled(isConnected);
        textField1.setEnabled(isConnected);
        buttonSend.setEnabled(isConnected);
        connectButton.setEnabled(!isConnected);
        a127001TextField.setEnabled(!isConnected);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonSend = new JButton();
        buttonSend.setText("Send");
        panel2.add(buttonSend, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        panel1.add(textField1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        textArea1 = new JTextArea();
        textArea1.setText("");
        panel3.add(textArea1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        a127001TextField = new JTextField();
        a127001TextField.setText("127.0.0.1");
        panel3.add(a127001TextField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        connectButton = new JButton();
        connectButton.setText("Connect");
        panel3.add(connectButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}