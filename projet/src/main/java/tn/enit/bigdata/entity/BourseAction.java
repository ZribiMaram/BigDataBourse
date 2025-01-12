package tn.enit.bigdata.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BourseAction implements Serializable {

    private String id;
    private String actionNom;
    private double prix;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "MST")
    private Date timestamp;

    public BourseAction() {

    }

    public BourseAction(String id, String actionNom, double prix, Date date) {
        super();
        this.id = id;
        this.actionNom = actionNom;
        this.prix = prix;
        this.timestamp = date;
    }
    public BourseAction(String id, String actionNom, double prix) {
        super();
        this.id = id;
        this.actionNom = actionNom;
        this.prix = prix;
     
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActionNom() {
        return actionNom;
    }

    public void setActionNom(String actionNom) {
        this.actionNom = actionNom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date date) {
        this.timestamp = date;
    }
}
