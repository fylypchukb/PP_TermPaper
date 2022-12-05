package com.example.Knights.Data.Entities.Ammunition;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;

@Entity
@Table(name = "helm")
public class Helm extends Ammunition{

    @Column(nullable=false)
    private String name = "NoName";
    @Column(nullable=false)
    private  boolean closeHelm;
    @Column(nullable=false)
    private int canTakeDamage;
    @Column(nullable=false)
    private String size;

    public Helm(String name,int canTakeDamage, String size, boolean closeHelm, double price, double weight) {
        super(price, weight);
        this.name=name;
        this.canTakeDamage = canTakeDamage;
        this.size = size;
        this.closeHelm = closeHelm;
    }

    public Helm() {

    }

    public String getName() {
        return name;
    }

    public int getCanTakeDamage() {
        return canTakeDamage;
    }

    public String getSize() {
        return size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCanTakeDamage(int canTakeDamage) {
        this.canTakeDamage = canTakeDamage;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isCloseHelm() {
        return closeHelm;
    }

    public void setCloseHelm(boolean closeHelm) {
        this.closeHelm = closeHelm;
    }



    @Override
    public String toString() {
        String noname = "";
        if (!name.equals("NoName")) {
            noname = "Name: " + name + ". ";
        }
        String helmType = closeHelm ? "close helm" : "open helm";

        return "\n" + "#Helm." + noname + "It`s a " + helmType + ". It can take "
                + canTakeDamage + " damage. Size: " + size + "." + super.toString();

    }
}
