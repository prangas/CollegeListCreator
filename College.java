package com.company;

public class College implements Comparable<College>{
    public int id;
    private int rank;
    private String name;
    private String city;
    private String state;
    private String publicPrivate;
    private int undergradPopulation;
    private int totalPopulation;
    private int netPrice;
    private int averageAid;
    private int totalCost;
    private int salary;
    private int acceptanceRate;
    private int satLower;
    private int satUpper;
    private int actLower;
    private int actUpper;
    private String website;

    public String getState() {
        return state;
    }
    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }



    public int getRank() {
        return rank;
    }


    public String getWebsite() {
        return website;
    }



    public int getActUpper() {
        return actUpper;
    }



    public int getActLower() {
        return actLower;
    }



    public int getSatUpper() {
        return satUpper;
    }



    public int getSatLower() {
        return satLower;
    }



    public int getAcceptanceRate() {
        return acceptanceRate;
    }



    public int getSalary() {
        return salary;
    }


    public int getTotalCost() {
        return totalCost;
    }



    public int getAverageAid() {
        return averageAid;
    }



    public int getNetPrice() {
        return netPrice;
    }



    public int getTotalPopulation() {
        return totalPopulation;
    }



    public int getUndergradPopulation() {
        return undergradPopulation;
    }



    public String getPublicPrivate() {
        return publicPrivate;
    }



    public int getId() {
        return id;
    }

    public College (int id, int rank, String name, String city, String state, String publicPrivate, int undergradPopulation, int totalPopulation, int netPrice, int averageAid, int totalCost, int salary, int acceptanceRate, int satLower, int satUpper, int actLower, int actUpper, String website){
        this.id = id;
        this.rank = rank;
        this.name = name;
        this.city = city;
        this.state = state;
        this.publicPrivate = publicPrivate;
        this.undergradPopulation = undergradPopulation;
        this.totalPopulation = totalPopulation;
        this.totalCost = totalCost;
        this.website = website;
        this.averageAid = averageAid;
        this.salary = salary;
        this.netPrice = netPrice;
        this.acceptanceRate = acceptanceRate;
        this.satLower = satLower;
        this.satUpper = satUpper;
        this.actLower = actLower;
        this.actUpper = actUpper;
    }
    public College(){

    }

    @Override
    public int compareTo(College o) {
        return Integer.compare(o.getRank(), this.getRank());
    }
}
