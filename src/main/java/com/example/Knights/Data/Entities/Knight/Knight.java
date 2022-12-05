package com.example.Knights.Data.Entities.Knight;

import com.example.Knights.Data.Entities.Ammunition.Ammunition;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="knights")
public class Knight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private int hitPoints;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "tblKnightAmmunition",
            joinColumns = {@JoinColumn(name = "Knight_ID", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "Ammunition_ID", referencedColumnName = "ammunitionId")})
    private List<Ammunition> ammunitions;


    public Knight(String title, String name, int age, int hitPoints) {
        this.title = title;
        this.name = name;
        this.age = age;
        this.hitPoints = hitPoints;
        this.ammunitions=new ArrayList<Ammunition>();
    }

    public Knight() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public List<Ammunition> getAmmunitions() {
        return ammunitions;
    }

    public void setAmmunitions(List<Ammunition> ammunitions) {
        this.ammunitions = ammunitions;
    }

    @Override
    public String toString() {
        return "Name: "+name +", Age: "+age+", Title: "+title+", Health: "+hitPoints;
    }
}
