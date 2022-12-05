package com.example.Electric.Data.Entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="Rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomId;

    @Column(nullable = false)
    private String roomName;

    // Todo: OneToMany
}
