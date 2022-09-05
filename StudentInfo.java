package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashSet;


public class StudentInfo {

    private JTabbedPane tabs;
    private JPanel Panel1;
    private JPanel tabPanel;
    private Connection con;
    private HashSet<Student> students;
    String name = "";

    public JPanel getPanel1() {
        return Panel1;
    }
    public StudentInfo() {
        Panel1.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 1;
        GridBagLayout x = new GridBagLayout();
        g.weightx = 0;
        g.weighty = 0;
        g.gridx = 1;
        g.gridy = 0;

        students = getStudents();
        Font myFont1 = new Font("Tahoma 11 Plain", Font.BOLD, 25);
        Font myFont2 = new Font("Tahoma 11 Plain", Font.PLAIN, 18);
        JButton addButton = new JButton("Add Student");
        addButton.setFont(myFont2);
        addButton.setFocusPainted(false);
        g.gridy = 1;
        Panel1.add(addButton, g);
        Panel1.setBackground(Color.getHSBColor(0.6f, .25f, .9f));
        addButton.setBackground(Color.getHSBColor(1, 0.7f, 0.7f));
        addButton.setForeground(Color.white);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Add Student");
                frame.setContentPane(new NewStudent().getPanel1());
                frame.setSize(1200, 800);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocation(200, 100);
                frame.setVisible(true);
            }
        });
        for (Student student : students) {
            JPanel panelx = new JPanel(){
                @Override
                public Dimension getPreferredSize(){
                    return tabPanel.getPreferredSize();
                }
            };
            panelx.setLayout(new BoxLayout(panelx, BoxLayout.Y_AXIS));
            tabs.addTab(student.getName(), panelx);
            for (int i = 0; i<4; i++){
                panelx.add(new JLabel("\n"));
            }
            panelx.setBackground(Color.lightGray);
            JLabel gpaLabel = new JLabel();

            gpaLabel.setFont(myFont1);
            gpaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            gpaLabel.setText("GPA");
            panelx.add(gpaLabel);
            JLabel spacer2 = new JLabel(" ");
            panelx.add(spacer2);
            JLabel gpaLabel2 = new JLabel();
            gpaLabel2.setFont(myFont2);
            gpaLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
            gpaLabel2.setText(String.valueOf(student.getGpa()));
            panelx.add(gpaLabel2);
            JLabel spacer3 = new JLabel(" ");
            panelx.add(spacer3);

            JLabel actLabel = new JLabel();
            actLabel.setFont(myFont1);
            actLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            if (student.getAct()>0){
                actLabel.setText("ACT");
            }
            else {
                actLabel.setText("SAT");
            }
            panelx.add(actLabel);
            JLabel spacer4 = new JLabel(" ");
            panelx.add(spacer4);
            JLabel actLabel2 = new JLabel();
            actLabel2.setFont(myFont2);
            actLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
            if (student.getAct()>0){
                actLabel2.setText(String.valueOf(student.getAct()));
            }
            else {
                actLabel2.setText(String.valueOf(student.getSat()));
            }
            panelx.add(actLabel2);

            JLabel spacer5 = new JLabel(" ");
            panelx.add(spacer5);
            JLabel stateLabel = new JLabel();
            stateLabel.setFont(myFont1);
            stateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            stateLabel.setText("State");
            panelx.add(stateLabel);
            JLabel spacer6 = new JLabel(" ");
            panelx.add(spacer6);
            JLabel stateLabel2 = new JLabel();
            stateLabel2.setFont(myFont2);
            stateLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
            stateLabel2.setText(String.valueOf(student.getState()));
            panelx.add(stateLabel2);

            for (int i = 0; i<4; i++){
                panelx.add(new JLabel("\n"));
            }
            JButton removeButton = new JButton();
            removeButton.setBackground(Color.getHSBColor(1, 0.7f, 0.7f));
            removeButton.setForeground(Color.white);
            removeButton.setPreferredSize(new Dimension(100,50));
            removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            removeButton.setText("Remove Student");
            removeButton.setFont(myFont2);
            panelx.add(removeButton);
            JButton survey = new JButton("Take Survey");
            survey.setAlignmentX(Component.CENTER_ALIGNMENT);
            survey.setFont(myFont2);
            survey.setPreferredSize(new Dimension(100,50));
            survey.setBackground(Color.getHSBColor(1, 0.7f, 0.7f));
            survey.setForeground(Color.white);
            panelx.add(survey);
            survey.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    name = tabs.getTitleAt(tabs.getSelectedIndex());
                    JFrame frame = new JFrame("Survey");  // create an instance, title in ""
                    frame.setContentPane(new Survey(name).getPanel1());  // adds the panel
                    frame.setSize(1200, 800);         // set it’s size
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setLocation(200, 100);     // where to place it
                    frame.setVisible(true);
                }
            });

            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        con = connect();
                        String q = "DELETE FROM \"Students\"" + "WHERE \"NAME\" = ?;";
                        PreparedStatement p = con.prepareStatement(q);
                        p.setString(1, tabs.getTitleAt(tabs.getSelectedIndex()));
                        p.executeUpdate();
                        JFrame frame = new JFrame("Student Info");  // create an instance, title in ""
                        frame.setContentPane(new StudentInfo().getPanel1());  // adds the panel
                        frame.setSize(1200, 800);         // set it’s size
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setLocation(200, 100);     // where to place it
                        frame.setVisible(true);

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            });


        }
        tabs.remove(0);
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
    public HashSet<Student> getStudents(){
        con = connect();
        HashSet<Student> students = new HashSet<>();
        try{
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery("select * from public.\"Students\";");
            while (r.next()){
                int id = r.getInt("ID");
                double gpa = r.getDouble("GPA");
                int act = r.getInt("ACT");
                int sat = r.getInt("SAT");
                String name = r.getString("NAME");
                String state = r.getString("STATE");
                Student newS = new Student(id, gpa, act, sat, name, state);
                students.add(newS);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return students;
    }
}
