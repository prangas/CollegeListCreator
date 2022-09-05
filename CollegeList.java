package com.company;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;

public class CollegeList {

    private JPanel Panel1 = new JPanel(new GridBagLayout());
    private Connection con;
    ArrayList<College> reaches = new ArrayList<>();
    ArrayList<College> targets = new ArrayList<>();
    ArrayList<College> safeties = new ArrayList<>();

    public JPanel getPanel1() {
        return Panel1;
    }

    public CollegeList(Set<College> collegeList, String name){
        Panel1.setBackground(Color.getHSBColor(0.6f, .25f, .9f));
        GridBagConstraints g = new GridBagConstraints();
        g.gridy = 0;
        g.weighty = 0.5;
        Font myFont1 = new Font("Tahoma 11 Plain", Font.BOLD, 25);
        JLabel title = new JLabel("College List");
        title.setFont(myFont1);
        Student newStudent = new Student();
        newStudent.setName(name);
        Set<Student> newSet = new HashSet<>();
        newSet = getStudents();
        Iterator itr1 = newSet.iterator();
        while (itr1.hasNext()){
            Student x = (Student) itr1.next();
            if (x.getName().equals(name)){
                newStudent.setGpa(x.getGpa());
                if (x.getAct()>0){
                    newStudent.setAct(x.getAct());
                }
                else if (x.getSat()>0){
                    newStudent.setSat(x.getSat());
                }
                break;
            }
        }


        Panel1.add(title, g);

        DefaultListModel<String> collegeM = new DefaultListModel<>();
        JList<String> collegeL = new JList<>();
        collegeL.setModel(collegeM);

        Iterator itr = collegeList.iterator();
        while (itr.hasNext()){
            College x = (College) itr.next();
            if (x.getName().contains("Azusa")){
                itr.remove();
            }
            else {
                int accRate = 0;
                if (x.getAcceptanceRate() > 0) {
                    accRate = x.getAcceptanceRate();
                }
                if (newStudent.getGpa() >= 3.8 && accRate > 0) {
                    accRate += 5;
                }
                if (newStudent.getAct() >= 34 && accRate > 0) {
                    accRate += 5;
                } else if (newStudent.getSat() > 1500 && accRate > 0) {
                    accRate += 5;
                }
                if (newStudent.getGpa() < 3.5 && newStudent.getGpa() > 3.0 && accRate > 0) {
                    accRate -= 5;
                }
                if (newStudent.getAct() < 30 && newStudent.getAct() > 0 && accRate > 0) {
                    accRate -= 5;
                } else if (newStudent.getSat() < 1350 && newStudent.getSat() > 0 && accRate > 0) {
                    accRate -= 5;
                }
                if (newStudent.getGpa() < 3.0 && accRate > 0) {
                    accRate -= 15;
                }
                if (newStudent.getAct() < 25 && newStudent.getAct() > 0 && accRate > 0) {
                    accRate -= 15;
                } else if (newStudent.getSat() < 1200 && newStudent.getSat() > 0 && accRate > 0) {
                    accRate -= 15;
                }
                if (accRate > 70) {
                    safeties.add(x);
                } else if (accRate < 70 && accRate > 30) {
                    targets.add(x);
                } else if (accRate > 0) {
                    reaches.add(x);
                } else {
                    itr.remove();
                }
            }
        }

        if (safeties.size()>8){

            Set<College> tree = new TreeSet<>(safeties);
            Iterator iter = tree.iterator();
            while (tree.size()>8){
                College x = (College) iter.next();
                iter.remove();
            }
            System.out.println(tree.size());
            safeties.clear();
            Iterator c = tree.iterator();
            while (c.hasNext()){
                College x = (College) c.next();
                safeties.add(x);
            }

        }
        if (targets.size()>9){
            Set<College> tree = new TreeSet<>(targets);
            Stack<College> xList = new Stack<>();
            Iterator iter = tree.iterator();
            while (tree.size()>8){
                College x = (College) iter.next();
                iter.remove();
            }
            System.out.println(xList.size());
            targets.clear();
            Iterator c = tree.iterator();
            while (c.hasNext()){
                College x = (College) c.next();
                targets.add(x);
            }
        }
        if (reaches.size()>8){
            Set<College> tree = new TreeSet<>(reaches);
            Stack<College> xList = new Stack<>();
            Iterator iter = tree.iterator();
            while (tree.size()>8){
                College x = (College) iter.next();
                iter.remove();
            }
            System.out.println(xList.size());
            reaches.clear();
            Iterator c = tree.iterator();
            while (c.hasNext()){
                College x = (College) c.next();
                reaches.add(x);
            }
        }

            for (int j = 0; j< reaches.size(); j++){
                collegeM.add(j, reaches.get(j).getName());
            }
            for (int k = 0; k< targets.size(); k++){
                collegeM.add(collegeM.size(), targets.get(k).getName());
            }
            for (int l = 0; l< safeties.size(); l++){
                collegeM.add(collegeM.size(), safeties.get(l).getName());
            }
//        }
        g.gridy = 1;
        Panel1.add(collegeL, g);
        System.out.println(collegeM);
        Font myFont2 = new Font("Tahoma 11 Plain", Font.PLAIN, 18);
        collegeL.setFont(myFont2);
        collegeL.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
                Component x = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                for (int i = 0; i<reaches.size(); i++) {
                    if (reaches.get(i).getName().equals(value)) {
                        x.setBackground(Color.getHSBColor(0, 0.5f, 1));
                    }
                }
                for (int i = 0; i<targets.size(); i++) {
                    if (targets.get(i).getName().equals(value)) {
                        x.setBackground(Color.yellow);
                    }
                }
                for (int i = 0; i<safeties.size(); i++){
                    if (safeties.get(i).getName().equals(value)) {
                        x.setBackground(Color.green);
                    }
                }
                return x;
            }
        });
        collegeL.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                for (int i = 0; i<collegeL.getModel().getSize(); i++) {
                    String x = collegeM.getElementAt(i);
                    if (x.equals(collegeL.getSelectedValue())){
                        JFrame frame = new JFrame("College Info");
                        frame.setContentPane(new CollegeInfo(x, safeties, targets, reaches, name, collegeList).getPanel1());
                        frame.setSize(1200, 800);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setLocation(200, 100);
                        frame.setVisible(true);
                    }

                }
            }
        });
        JButton students = new JButton("Back to Students");
        students.setPreferredSize(new Dimension(200, 50));
        students.setFont(myFont2);
        students.setBackground(Color.getHSBColor(1, 0.7f, 0.7f));
        students.setForeground(Color.white);
        Panel1.add(students);
        students.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Student Info");
                frame.setContentPane(new StudentInfo().getPanel1());
                frame.setSize(1200, 800);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocation(200, 100);
                frame.setVisible(true);
            }
        });
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

}
