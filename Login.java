package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    JPanel Panel1 = new JPanel(new GridBagLayout());

    public JPanel getPanel1() {
        return Panel1;
    }

    public Login(){
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = 0;
        g.weighty = 0;
        JLabel user = new JLabel("Username: ");
        Panel1.add(user, g);
        g.gridy = 1;
        JLabel pass = new JLabel("Password: ");
        Panel1.add(pass, g);
        g.gridx = 1;
        g.gridy = 0;
        JTextField userText = new JTextField();
        userText.setPreferredSize(new Dimension(100,20));
        Panel1.add(userText, g);
        g.gridy = 1;
        JTextField passText = new JTextField();
        passText.setPreferredSize(new Dimension(100,20));
        Panel1.add(passText, g);
        g.gridy = 2;
        JButton login = new JButton("Sign in");
        Panel1.add(login, g);
        g.gridx = 1;
        g.gridy = 3;
        JLabel error = new JLabel();
        error.setForeground(Color.RED);
        Panel1.add(error, g);
        error.setVisible(false);
        Panel1.setBackground(Color.getHSBColor(0.6f, .25f, .9f));
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userText.getText().equals("prithvi") && passText.getText().equals("ranga")) {
                    JFrame frame = new JFrame("Student Info");  // create an instance, title in ""
                    frame.setContentPane(new StudentInfo().getPanel1());  // adds the panel
                    frame.setSize(1200, 800);         // set it’s size
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setLocation(200, 100);     // where to place it
                    frame.setVisible(true);
                }
                else if (userText.getText().equals("") && !passText.getText().equals("")){
                    error.setVisible(true);
                    error.setText("Enter a username");
                }
                else if (!userText.getText().equals("") && passText.getText().equals("")){
                    error.setVisible(true);
                    error.setText("Enter a password");
                }
                else if (userText.getText().equals("") && passText.getText().equals("")){
                    error.setVisible(true);
                    error.setText("Enter a username and password");
                }
                else if (!userText.getText().equals("prithvi") && passText.getText().equals("ranga")){
                    error.setVisible(true);
                    error.setText("Incorrect username");
                }
                else if (!passText.getText().equals("ranga") && userText.getText().equals("prithvi")){
                    error.setVisible(true);
                    error.setText("Incorrect password");
                }
                else if (!passText.getText().equals("ranga") && !userText.getText().equals("prithvi")){
                    error.setVisible(true);
                    error.setText("Incorrect username and password");
                }

            }
        });
    }
}

