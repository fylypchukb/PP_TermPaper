package com.example.Knights.Data.Entities.Ammunition;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "ammunition")
public abstract class Ammunition {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long ammunitionId;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private double weight;
    public Ammunition(double price, double weight) {
        this.price = price;
        this.weight = weight;
    }

    public Ammunition() {

    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public long getammunitionId() {
        return ammunitionId;
    }

    public void setammunitionId(long id) {
        this.ammunitionId = id;
    }
    @Override
    public String toString() {
        return "Cost: " + price + ". Weight: " + weight + ".";
    }



}
