package com.company;

import javax.swing.*;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new Login().getPanel1());
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(800, 400);
        frame.setVisible(true);

    }

}
