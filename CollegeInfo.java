package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

public class CollegeInfo {
    JPanel Panel1 = new JPanel(new GridBagLayout());

    public JPanel getPanel1() {
        return Panel1;
    }
    public CollegeInfo(String college, ArrayList<College> safeties, ArrayList<College> targets, ArrayList<College> reaches, String name, Set<College> collegeL){
        Panel1.setBackground(Color.getHSBColor(0.6f, .25f, .9f));
        ArrayList<College> collegeList = new ArrayList<>();
        for (int i = 0; i<safeties.size(); i++){
            collegeList.add(safeties.get(i));
        }
        for (int i = 0; i<targets.size(); i++){
            collegeList.add(targets.get(i));
        }
        for (int i = 0; i<reaches.size(); i++){
            collegeList.add(reaches.get(i));
        }
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = 0;
        g.weightx = 0;
        g.weighty = 1;
        g.gridheight = 1;
        g.gridwidth =1;
        g.fill = GridBagConstraints.NONE;
        JLabel title = new JLabel(college);
        Font myFont1 = new Font("Tahoma 11 Plain", Font.BOLD, 25);
        Font myFont2 = new Font("Tahoma 11 Plain", Font.PLAIN, 18);
        title.setFont(myFont1);
        Panel1.add(title, g);
        College x = new College();
        for (int i = 0; i< collegeList.size(); i++){
            if (collegeList.get(i).getName().equals(college)){
                x = collegeList.get(i);
            }
        }
        JLabel city = new JLabel(  "City: " + x.getCity());
        g.gridy = 1;
        city.setFont(myFont2);
        Panel1.add(city, g);
        JLabel state = new JLabel(  "State: " + x.getState());
        g.gridy = 2;
        state.setFont(myFont2);
        Panel1.add(state, g);
        JLabel publicPrivate = new JLabel(  "Public / Private: " + x.getPublicPrivate());
        g.gridy = 3;
        publicPrivate.setFont(myFont2);
        Panel1.add(publicPrivate, g);
        JLabel population = new JLabel(  "Student Population: " + x.getTotalPopulation());
        g.gridy = 4;
        population.setFont(myFont2);
        Panel1.add(population, g);
        JLabel cost = new JLabel(  "Annual Cost: $" + x.getTotalCost());
        g.gridy = 5;
        cost.setFont(myFont2);
        Panel1.add(cost, g);
        JLabel satRange = new JLabel(  "SAT Range: " + x.getSatLower() + " - " + x.getSatUpper());
        g.gridy = 6;
        satRange.setFont(myFont2);
        Panel1.add(satRange, g);
        JLabel actRange = new JLabel(  "ACT Range: " + x.getActLower() + " - " + x.getActUpper());
        g.gridy = 7;
        actRange.setFont(myFont2);
        Panel1.add(actRange, g);
        JLabel acceptance = new JLabel(  "Acceptance Rate: " + x.getAcceptanceRate() + "%");
        g.gridy = 8;
        acceptance.setFont(myFont2);
        Panel1.add(acceptance, g);
        g.gridx = 5;
        g.gridy = 0;
        JButton back = new JButton("Back");
        back.setBackground(Color.getHSBColor(1, 0.7f, 0.7f));
        back.setForeground(Color.white);
        back.setPreferredSize(new Dimension(100,50));
        back.setFont(myFont2);
        Panel1.add(back, g);

        back.setFocusPainted(false);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Student Info");
                frame.setContentPane(new CollegeList(collegeL, name).getPanel1());
                frame.setSize(1200, 800);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocation(200, 100);
                frame.setVisible(true);
            }
        });
    }
}

