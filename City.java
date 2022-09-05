package com.company;

public class City {
    private String city;
    private String state;
    private double latitude;
    private double longitude;
    private int population;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
    public City(String city, double latitude, double longitude){
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public City(String city, String state, double latitude, double longitude, int population){
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.state = state;
        this.population = population;
    }
}
