package Entities;

import java.util.ArrayList;
import java.util.TreeSet;

public class Computer {
    private int id;
    private ArrayList malwares;
    private ArrayList users;
    private double totalRating;

    public Computer(){}

    public Computer(int id, ArrayList malwares, ArrayList users, double totalRating) {
        this.malwares = malwares;
        this.users = users;
        this.totalRating = totalRating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList getMalwares() {
        return malwares;
    }

    public void setMalwares(ArrayList malwares) {
        this.malwares = malwares;
    }

    public ArrayList getUsers() {
        return users;
    }

    public void setUsers(ArrayList users) {
        this.users = users;
    }

    public double getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(double totalRating) {
        this.totalRating = totalRating;
    }

}
