package com.example.Knights.Data.Entities.Ammunition;

import javax.persistence.*;

@Entity
@Table(name = "armor")
public class Armor extends Ammunition {
    @Column(nullable=false)
    private String name = "NoName";
    @Column(nullable=false)
    private boolean ceremonial;
    @Column(nullable=false)
    private int canTakeDamage;
    @Column(nullable=false)
    private String size;

    public Armor(String name,int canTakeDamage, String size, boolean ceremonial,double price, double weight) {
        super(price, weight);
        this.name=name;
        this.canTakeDamage = canTakeDamage;
        this.size = size;
        this.ceremonial = ceremonial;
    }

    public Armor() {

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

    public boolean isCeremonial() {
        return ceremonial;
    }

    public void setCeremonial(boolean ceremonial) {
        this.ceremonial = ceremonial;
    }


    @Override
    public String toString() {
        String noname = "";
        if (!name.equals("NoName")) {
            noname = "Name: " + name + ". ";
        }
        String armorType = ceremonial ? "ceremonial" : "normal";
        return "\n" + "#Armor." + noname + "It`s a " + armorType + ". It can take " + canTakeDamage
                + " damage. Size: " + size + ". " + super.toString();

    }
}
