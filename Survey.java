package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;

public class Survey {
    private JPanel Panel1;
    Set<College> colleges = new LinkedHashSet<>();
    Set<College> collegeList = new LinkedHashSet<>();

    public JPanel getPanel1() {
        return Panel1;
    }

    Queue<String> questions = new LinkedList<>();
    String question1 = "What city do you reside in?";
    String question2 = "How close to home do you want to be?";
    String question3 = "What type of weather do you prefer?";
    String question4 = "Do you prefer small, medium, or larger schools?";
    String question5 = "Do you require significant amounts of financial aid?";
    String question6 = "Do you prefer a campus in the city or in a college town?";
    String question7 = "Are you open to attending a primarily tech-focused university?";
    String question8 = "Please select the maximum price for total cost you are willing to pay.";
    String question9 = "Rank the following factors from most important to least important.";

    private Connection con;



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
    public Survey(String name) {
        Panel1.setBackground(Color.getHSBColor(0.6f, .25f, .9f));
        ArrayList<String> answers1 = new ArrayList<>();
        ArrayList<String> answers2 = new ArrayList<>();
        ArrayList<String> answers3 = new ArrayList<>();
        ArrayList<String> answers4 = new ArrayList<>();
        ArrayList<String> answers5 = new ArrayList<>();
        ArrayList<String> answers6 = new ArrayList<>();
        ArrayList<String> answers7 = new ArrayList<>();
        ArrayList<String> answers8 = new ArrayList<>();
        ArrayList<City> cities = new ArrayList<>();

        questions.add(question1);
        questions.add(question2);
        questions.add(question4);
        questions.add(question5);
        questions.add(question6);
        questions.add(question7);
        questions.add(question8);
        questions.add(question9);
        answers1.add("100 miles");
        answers1.add("500 miles");
        answers1.add("1000 miles");
        answers1.add("2500 miles");
        answers1.add("Any distance is fine");
        answers2.add("Sunny");
        answers2.add("Rainy");
        answers2.add("Snowy");
        answers2.add("Any weather is fine");
        answers3.add("Small");
        answers3.add("Medium");
        answers3.add("Large");
        answers3.add("All are fine");
        answers4.add("Yes");
        answers4.add("No");
        answers5.add("City");
        answers5.add("College Town");
        answers5.add("Either is fine");
        answers6.add("Yes");
        answers6.add("No");
        answers7.add("$20,000");
        answers7.add("$40,000");
        answers7.add("$60,000");
        answers7.add("Any price is fine");
        answers8.add("Proximity to home");
        answers8.add("School size");
        answers8.add("Financial Aid");
        answers8.add("Campus Location (City vs. College Town)");
        answers8.add("Total Cost");
        JLabel questionLabel = new JLabel();
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = 0;
        g.weightx = 0;
        questionLabel.setText("<html>"+ "Are you ready to fill out the survey?" + "</html");
        Font myFont1 = new Font("Tahoma 11 Plain", Font.BOLD, 25);
        questionLabel.setFont(myFont1);
        Panel1.add(questionLabel, g);
        JButton beginButton = new JButton();
        beginButton.setVisible(true);
        beginButton.setText("Begin");
        beginButton.setFont(myFont1);
        JLabel errorLabel = new JLabel("Label");
        errorLabel.setForeground(Color.red);
        errorLabel.setVisible(false);
        g.gridx = 4;
        Panel1.add(beginButton, g);
        JComboBox select = new JComboBox();
        JTextField cityText = new JTextField();
        cityText.setPreferredSize(new Dimension(100, 30));
        JButton listButton = new JButton("See List");
        Panel1.add(listButton);
        listButton.setVisible(false);
        City home = new City("", 0, 0);
        beginButton.setBackground(Color.getHSBColor(1, 0.7f, 0.7f));
        beginButton.setForeground(Color.white);
        beginButton.setFocusPainted(false);
        beginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = "";
                beginButton.setText("Next");
                if (questionLabel.getText().contains("ready")) {
                    questionLabel.setText(questions.remove());
                    g.gridx = 5;
                    Panel1.add(cityText, g);
                } else if (questionLabel.getText().contains("reside")) {
                    city = cityText.getText();
                    home.setCity(city);

                    System.out.println(city);


                    double latitude2 = 0;
                    double longitude2 = 0;

                    try {
                        con = connect();
                        Statement st = con.createStatement();
                        ResultSet r = st.executeQuery("select latitude from public.\"Locations\" where city = '" + city + "';");
                        if (r.next()) {
                            String latitude = r.getString("latitude");
                            latitude2 = Double.parseDouble(latitude);
                        }


                        r = st.executeQuery("select longitude from public.\"Locations\" where city = '" + city + "';");
                        if (r.next()) {
                            errorLabel.setVisible(false);
                            String longitude = r.getString("longitude");
                            longitude2 = Double.parseDouble(longitude);
                            home.setCity(city);
                            System.out.println(city);
                            home.setLatitude(latitude2);
                            home.setLongitude(longitude2);
                            r = st.executeQuery("select * from public.\"Locations\";");
                            while (r.next()) {
                                String cityName = r.getString("city");
                                String stateName = r.getString("state");
                                double latitude = r.getDouble("latitude");
                                double longituder = r.getDouble("longitude");
                                int population = r.getInt("population");
                                City destination = new City(cityName, stateName, latitude, longituder, population);
                                cities.add(destination);


                            }
                            questionLabel.setText(questions.remove());
                            Panel1.add(select);
                            Panel1.add(beginButton);
                            cityText.setVisible(false);


                        }
                        else if (!r.next() && !cityText.getText().equals("")){
                            g.gridy = 1;
                            Panel1.add(errorLabel, g);
                            errorLabel.setText("We could not find that city. Please enter another city.");
                            errorLabel.setVisible(true);

                        }
                        else if (cityText.getText().equals("")){
                            g.gridy = 1;
                            Panel1.add(errorLabel, g);
                            errorLabel.setText("Please enter a city to proceed.");
                            errorLabel.setVisible(true);
                        }

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    if (!errorLabel.isVisible()) {
                        for (int i = 0; i < answers1.size(); i++) {
                            select.addItem(answers1.get(i));
                        }
                    }
                }


            else if (questionLabel.getText().contains("close to home")){

                    try {
                        colleges = getColleges();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    Iterator itr = colleges.iterator();
                    if (!String.valueOf(select.getSelectedItem()).substring(0,String.valueOf(select.getSelectedItem()).length()-5).contains("Any")) {
                        double prefDistance = Double.parseDouble(String.valueOf(select.getSelectedItem()).substring(0, String.valueOf(select.getSelectedItem()).length() - 5));
                        while (itr.hasNext()) {
                            College x = (College) itr.next();
                            for (int i = 0; i < cities.size(); i++) {
                                if (cities.get(i).getCity().equals(x.getCity()) && cities.get(i).getState().equals(x.getState())) {
                                    if (!(getDistance(home.getLatitude(), home.getLongitude(), cities.get(i).getLatitude(), cities.get(i).getLongitude()) > prefDistance)) {
                                        collegeList.add(x);
                                        break;
                                    }
                                }
                            }

                        }
                    }
                    questionLabel.setText(questions.remove());
                    select.removeAllItems();
                    for (int i = 0; i<answers3.size(); i++){
                        select.addItem(answers3.get(i));
                    }

                }
                else if (questionLabel.getText().contains("small")){
                    if (select.getSelectedItem().toString().equals("Small")){
                        Iterator itr = collegeList.iterator();
                        int size = collegeList.size();
                        for (int i = 0; i<size; i++){
                            College x = (College) itr.next();
                            if (x.getTotalPopulation()>8000){
                                itr.remove();
                            }
                        }
                    }
                    else if (select.getSelectedItem().toString().equals("Medium")){
                        Iterator itr = collegeList.iterator();
                        int size = collegeList.size();
                        for (int i = 0; i<size; i++){
                            College x = (College) itr.next();
                            if (x.getTotalPopulation()<8000 || x.getTotalPopulation()>20000){
                                itr.remove();
                            }
                        }
                    }
                    else if (select.getSelectedItem().toString().equals("Large")){
                        Iterator itr = collegeList.iterator();
                        int size = collegeList.size();
                        for (int i = 0; i<size; i++){
                            College x = (College) itr.next();
                            if (x.getTotalPopulation()<20000){
                                itr.remove();
                            }
                        }
                    }
                    questionLabel.setText("<html>" + questions.remove() + "</html>");
                    select.removeAllItems();
                    for (int i = 0; i< answers4.size(); i++){
                        select.addItem(answers4.get(i));
                    }
                }
                else if (questionLabel.getText().contains("financial aid")){
                    if (select.getSelectedItem().toString().equals("Yes")){
                        Iterator itr = collegeList.iterator();
                        while (itr.hasNext()){
                            College x = (College) itr.next();
                            if ((x.getAverageAid()<30000)){
                                itr.remove();
                            }
                        }
                    }
                    questionLabel.setText("<html>" + questions.remove() + "</html>");
                    select.removeAllItems();
                    for (int i = 0; i< answers5.size(); i++){
                        select.addItem(answers5.get(i));
                    }
                    System.out.println(colleges.size());
                }

                else if (questionLabel.getText().contains("campus")){
                    if (select.getSelectedItem().toString().equals("City")){
                        Iterator itr = collegeList.iterator();
                        while (itr.hasNext()){
                            College x = (College) itr.next();
                            for (int i = 0; i<cities.size(); i++) {
                                if ((cities.get(i).getCity().equals(x.getCity()) && cities.get(i).getState().equals(x.getState())
                                        && (cities.get(i).getPopulation()<100000))) {
                                    itr.remove();
                                }
                            }
                        }
                    }
                    else if (select.getSelectedItem().toString().equals("College Town")){
                        Iterator itr = collegeList.iterator();
                        while (itr.hasNext()){
                            College x = (College) itr.next();
                            for (int i = 0; i<cities.size(); i++) {
                                if ((cities.get(i).getCity().equals(x.getCity()) && cities.get(i).getState().equals(x.getState())
                                        && (cities.get(i).getPopulation()>300000))) {
                                    itr.remove();
                                }
                            }
                        }
                    }
                    System.out.println(collegeList.size());
                    questionLabel.setText(questions.remove());
                    select.removeAllItems();
                    for (int i = 0; i< answers6.size(); i++){
                        select.addItem(answers6.get(i));
                    }
                }
                else if (questionLabel.getText().contains("tech")){
                    Iterator itr = collegeList.iterator();
                    int size = collegeList.size();
                    for (int i = 0; i<size; i++){
                        College x = (College) itr.next();
                        if (select.getSelectedItem().toString().equals("No") && x.getName().contains("tech")){
                            itr.remove();
                        }
                    }
                    System.out.println(collegeList.size());
                    questionLabel.setText(questions.remove());
                    select.removeAllItems();
                    for (int i = 0; i< answers7.size(); i++){
                        select.addItem(answers7.get(i));
                    }
                }
                else if (questionLabel.getText().contains("total cost")) {
//                    home.setCity(city);
                    for (int i = 0; i < cities.size(); i++) {
                        if (home.getCity().equals(cities.get(i).getCity())) {
                            home.setState(cities.get(i).getState());
                            break;
                        }
                    }
                    if (select.getSelectedItem().toString().equals("$20,000")) {
                        Iterator itr = collegeList.iterator();
                        int size = collegeList.size();
                        int coa = 0;
                        for (int i = 0; i < size; i++) {
                            College x = (College) itr.next();
                            if (home.getState().equals(x.getState()) && x.getPublicPrivate().equals("Public")) {
                                coa = x.getTotalCost() / 2;
                                if (coa > 20000) {
                                    itr.remove();
                                }
                            } else {
                                coa = x.getTotalCost();
                                if (coa > 20000) {
                                    itr.remove();
                                }
                            }
                        }
                    } else if (select.getSelectedItem().toString().equals("$40,000")) {
                        Iterator itr = collegeList.iterator();
                        int size = collegeList.size();
                        int coa = 0;
                        for (int i = 0; i < size; i++) {
                            College x = (College) itr.next();
                            if (home.getState().equals(x.getState()) && x.getPublicPrivate().equals("Public")) {
                                coa = x.getTotalCost() / 2;
                                if (coa > 40000) {
                                    itr.remove();
                                }
                            } else {
                                coa = x.getTotalCost();
                                if (coa > 40000) {
                                    itr.remove();
                                }
                            }
                        }
                    } else if (select.getSelectedItem().toString().equals("$60,000")) {
                        Iterator itr = collegeList.iterator();
                        int size = collegeList.size();
                        int coa = 0;
                        for (int i = 0; i < size; i++) {
                            College x = (College) itr.next();
                            if (home.getState().equals(x.getState()) && x.getPublicPrivate().equals("Public")) {
                                coa = x.getTotalCost() / 2;
                                if (coa > 60000) {
                                    itr.remove();
                                }
                            } else {
                                coa = x.getTotalCost();
                                if (coa > 60000) {
                                    itr.remove();
                                }
                            }
                        }

                    }
                    listButton.setVisible(true);
                    beginButton.setVisible(false);


                }


            }
        });
        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("College List");  // create an instance, title in ""
                frame.setContentPane(new CollegeList(collegeList, name).getPanel1());  // adds the panel
                frame.setSize(1200, 800);         // set it’s size
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocation(200, 100);     // where to place it
                frame.setVisible(true);
            }
        });
    }
    public Set<College> getColleges () throws SQLException {
        con = connect();
        Set<College> colleges = new HashSet<>();
        Statement st = con.createStatement();
        ResultSet r = st.executeQuery("select * from public.\"Colleges\";");
        while (r.next()) {
            int id = r.getInt("ID");
            int rank = r.getInt("Rank");
            String name = r.getString("Name");
            String city = r.getString("City");
            String state = r.getString("State");
            String publicPrivate = r.getString("Public/Private");
            int undergradPopulation = r.getInt("Undergraduate Population");
            int totalPopulation = r.getInt("Student Population");
            int netPrice = r.getInt("Net Price");
            int averageAid = r.getInt("Average Grant Aid");
            int totalCost = r.getInt("Total Annual Cost");
            int salary = r.getInt("Alumni Salary");
            int acceptanceRate = r.getInt("Acceptance Rate");
            int satLower = r.getInt("SAT Lower");
            int satUpper = r.getInt("SAT Upper");
            int actLower = r.getInt("ACT Lower");
            int actUpper = r.getInt("ACT Upper");
            String website = r.getString("Website");
            College newCollege = new College (id, rank, name, city, state, publicPrivate, undergradPopulation, totalPopulation, netPrice, averageAid, totalCost, salary, acceptanceRate, satLower, satUpper, actLower, actUpper, website);
            colleges.add(newCollege);
        }
        return colleges;
    }
    public double getDistance(double lat1, double lng1, double lat2, double lng2){
        double x = lng1-lng2;
        double distance = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) *
                Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(x));
        distance = Math.acos(distance);
        distance = Math.toDegrees(distance);
        distance = distance * 60 * 1.1515;
        return distance;
    }

}
