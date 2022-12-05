package com.example.Electric.Data.Entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer deviceId;
    @Column(nullable = false)
    private String deviceName;
    @Column(nullable = false)
    private Float electricPowerDefault;     //todo: дійсні числа у Entity
    @Column(nullable = false)
    private Boolean isActive;
    @Column(nullable = false)
    private Integer roomID;

    // todo: manytoone
}
