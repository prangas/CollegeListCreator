package com.company;

public class Student {
    private double gpa;
    private int act;
    private int sat;
    private String name;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSat() {
        return sat;
    }

    public void setSat(int sat) {
        this.sat = sat;
    }

    public int getAct() {
        return act;
    }

    public void setAct(int act) {
        this.act = act;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    private String state;

    public Student(int id,  double gpa, int act, int sat, String name, String state){
        this.id = id;
        this.name = name;
        this.gpa = gpa;
        this.act = act;
        this.sat = sat;
        this.state = state;
    }

    @Override
    public String toString() {
        return name;
    }
    public Student (){

    }
}
