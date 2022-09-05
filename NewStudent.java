package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;

public class NewStudent {
    private JPanel Panel1 = new JPanel(new GridBagLayout());
    private JLabel college;
    private JLabel nameLabel = new JLabel("Name: ");
    private JTextField nameText = new JTextField(10);
    private JLabel gpaLabel = new JLabel("GPA: ");
    private JTextField gpaText= new JTextField(10);
    private JLabel actLabel= new JLabel("ACT: ");
    private JTextField actText= new JTextField(10);
    private JLabel satLabel = new JLabel("SAT: ");
    private JTextField satText= new JTextField(10);
    private JButton addStudentButton = new JButton("Add Student");
    private JLabel stateLabel = new JLabel("State: ");
    private JTextField stateText  = new JTextField(10);
    private Connection con;

    Map<Integer, String> rankings = new LinkedHashMap<>();

    public JLabel getCollege() {
        return college;
    }

    public JPanel getPanel1() {
        return Panel1;
    }

    public Connection connect() {
        String url = "jdbc:postgresql://localhost:5432/CollegeDB";
        String user = "postgres";
        String password = "Rafanadal18";
        try {
            Connection c = DriverManager.getConnection(url, user, password);
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT VERSION()");
            if (rs.next()) {
                System.out.println(rs.getString(1));
            }
            return c;
        } catch (SQLException ex) {
            System.out.println("SQL Driver error");
            return null;
        }
    }


    public NewStudent() {

        Set<Integer> ids = new TreeSet<>();
        int max = 0;

        Panel1.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        JButton students = new JButton("Back");


        g.gridx = 0;
        g.gridy = 0;
        g.weightx = 0;
        g.weighty = 1;
        g.gridheight = 1;
        g.gridwidth =1;
        g.fill = GridBagConstraints.NONE;

        Panel1.add(nameLabel, g);
        g.gridy = 1;
        Panel1.add(gpaLabel, g);
        g.gridy = 2;
        Panel1.add(actLabel, g);
        g.gridy = 3;
        Panel1.add(satLabel, g);
        g.gridy = 4;
        Panel1.add(stateLabel, g);
        g.gridx = 1;
        g.gridy = 0;
        Panel1.add(nameText, g);
        g.gridy = 1;
        Panel1.add(gpaText, g);
        g.gridy = 2;
        Panel1.add(actText, g);
        g.gridy = 3;
        Panel1.add(satText, g);
        g.gridy = 4;
        Panel1.add(stateText, g);
        g.gridx = 5;
        g.gridy = 0;
        g.weighty = 1;
        g.weightx = 0;
        Panel1.add(students, g);
        g.gridy = 0;
        g.gridy = 5;
        Panel1.add(addStudentButton, g);
        JLabel error = new JLabel();
        Panel1.add(error);
        error.setForeground(Color.RED);
        error.setVisible(false);
        students.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Student Info");  // create an instance, title in ""
                frame.setContentPane(new StudentInfo().getPanel1());  // adds the panel
                frame.setSize(1200, 800);         // set it’s size
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocation(200, 100);     // where to place it
                frame.setVisible(true);
            }
        });
        Panel1.setBackground(Color.getHSBColor(0.6f, .25f, .9f));
        con = connect();
        try {
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            ResultSet rs = st.executeQuery("select * from public.\"Colleges\";");
            ResultSet rs2 = st2.executeQuery("select * from public.\"Students\";");
            while(rs2.next()) {   // moves to the first row
                ids.add(rs2.getInt("ID"));
            }
            while (rs.next()){
                rankings.put(rs.getInt("RANK"), rs.getString("NAME"));
            }
            Iterator itr = ids.iterator();
            while (itr.hasNext()){
                int temp = Integer.parseInt(String.valueOf(itr.next()));
                if (temp>max){
                    max = temp;
                    itr.remove();
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQL error");
        }
        int finalMax = max;
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name;
                name = nameText.getText();
                double gpa = 0;
                if (!gpaText.getText().equals("")) {
                    gpa = Double.parseDouble(gpaText.getText());
                }
                int act = 0;
                if (!actText.getText().trim().equals("")){
                    act = Integer.parseInt(actText.getText());
                }
                int sat = 0;
                if (!satText.getText().trim().equals("")){
                    sat = Integer.parseInt(satText.getText());
                }
                String state;
                state = stateText.getText();
                Student Prithvi = new Student();
                if (!nameText.getText().equals("") && !gpaText.getText().equals("") && !stateText.getText().equals("")) {
                    Prithvi = new Student(finalMax + 1, gpa, act, sat, name, state);
                    try {
//                    con = connect();
                        String query = "INSERT INTO \"Students\""+ "(\"ID\", \"GPA\", \"ACT\", \"SAT\", \"NAME\", \"STATE\") VALUES" + "(?,?,?,?,?,?);";
                        PreparedStatement ps = null;
                        ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                        ps.setInt(1, finalMax+1);
                        ps.setDouble(2, Double.parseDouble(gpaText.getText()));
                        ps.setInt(3, 0);
                        ps.setInt(4, 0);
                        if (Prithvi.getAct()>0){
                            ps.setInt(3, Integer.parseInt(actText.getText()));
                        }
                        if (Prithvi.getSat()>0){
                            ps.setInt(4, Integer.parseInt(satText.getText()));
                        }
                        ps.setString(5, nameText.getText());
                        ps.setString(6, stateText.getText());
                        ps.executeUpdate();
                        ps.close();

                        System.out.println("done");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else {
                    error.setVisible(true);
                    error.setText("Missing Information");
                }
                System.out.println(ids.size());


            }
        });
    }

}
