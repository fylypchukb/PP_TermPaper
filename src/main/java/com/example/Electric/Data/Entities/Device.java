package com.example.Electric.Data.Entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Devices")
public class Device {

    public Device(Integer deviceId, String deviceName, Float electricPowerDefault, Boolean isActive, Integer roomID) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.electricPowerDefault = electricPowerDefault;
        this.isActive = isActive;
        this.roomID = roomID;
        this.room = room;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deviceId")
    private Integer deviceId;
    @Column(name = "deviceName",nullable = false)
    private String deviceName;
    @Column(name = "electricPowerDefault", nullable = false)
    private Float electricPowerDefault;     //todo: дійсні числа у Entity
    @Column(name = "isActive", nullable = false)
    private Boolean isActive;
    @Column(name = "roomID",nullable = false)
    private Integer roomID;
    @ManyToOne(targetEntity = Room.class)
    @JoinColumn(name = "roomID", nullable = false, insertable = false, updatable = false)
    private Room room;
}
