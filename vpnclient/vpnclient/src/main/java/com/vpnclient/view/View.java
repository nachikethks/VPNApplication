package com.vpnclient.view;

import com.vpnclient.controller.VPNClient;
import com.vpnclient.controller.VPNConnection;
import com.vpnclient.controller.VPNProtocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class View extends JFrame {

    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private VPNConnection c;
    private VPNProtocol p;

    public View(VPNProtocol p, VPNConnection c) {
        super("Chat Room");

        this.c = c;
        this.p = p;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout());


        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        add(chatScrollPane, BorderLayout.CENTER);

        messageField = new JTextField();
        bottomPanel.add(messageField, BorderLayout.CENTER);

        sendButton = new JButton("Send");
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
        sendButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String message = messageField.getText();
                if (!message.isEmpty()) {
                    // Append message to text pane
                    chatArea.setText(chatArea.getText() + "\n" + "Client(You): " + message);
                    try{
                    c.send(p, message);
                    }
                    catch (Exception f) {
                        System.out.println(f);
                    }
                    // Clear text field
                    messageField.setText("");
                }
            }
        });
    }
    public void addTextMessage(String message) {
        if (!message.equals("")){
            chatArea.setText(chatArea.getText() + "\n" + "Webserver: " + message);
        }
    }
}