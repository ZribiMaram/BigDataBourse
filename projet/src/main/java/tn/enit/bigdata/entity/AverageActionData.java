package tn.enit.bigdata.entity;


import java.io.Serializable;


public class AverageActionData implements Serializable {

    private String id;
    private double averagePrix;

    // Default constructor
    public AverageActionData() {
    }

    // Constructor with parameters
    public AverageActionData(String id, double averagePrix) {
        this.id = id;
        this.averagePrix = averagePrix;
    }

    // Getters
    public String getId() {
        return id;
    }

    public double getAveragePrix() {
        return averagePrix;
    }

    // Setter for averagePrix
    public void setAveragePrix(double averagePrix) {
        this.averagePrix = averagePrix;
    }

    // toString method for displaying the data
    @Override
    public String toString() {
        return "AverageActionData{" +
                "id='" + id + '\'' +
                ", averagePrix=" + averagePrix +
                '}';
    }
}
