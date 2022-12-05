package com.example.Knights.Data.Entities.Ammunition;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;

@Entity
@Table(name = "shield")
public class Shield extends Ammunition{

    @Column(nullable=false)
    private String name = "NoName";
    @Column(nullable=false)
    private int canTakeDamage;
    @Column(nullable=false)
    private String size;

    public Shield(String name,int canTakeDamage, String size, double price, double weight) {
        super(price, weight);
        this.name=name;
        this.canTakeDamage = canTakeDamage;
        this.size = size;
    }

    public Shield() {

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



    @Override
    public String toString() {
        String noname = "";
        if (!name.equals("NoName")) {
            noname = "Name: " + name + ". ";
        }
        return "\n" + "#Shield." + noname + "It can take " + canTakeDamage
                + " damage. Size: " + size + ". " + super.toString();

    }
}
