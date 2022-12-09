package com.example.Electric.Data.Entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Devices")
public class Device {

    public Device(String deviceName, Float electricPowerDefault, Integer roomID) {
        this.deviceName = deviceName;
        this.electricPowerDefault = electricPowerDefault;
        this.roomID = roomID;
        isActive = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Integer device_id;
    @Column(name = "device_name", nullable = false)
    private String deviceName;
    @Column(name = "electric_power_default", nullable = false)
    private Float electricPowerDefault;     //todo: дійсні числа у Entity
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    @Column(name = "roomID", nullable = false)
    private Integer roomID;
    @ManyToOne(targetEntity = Room.class)
    @JoinColumn(name = "roomID", nullable = false, insertable = false, updatable = false)
    private Room room;

    public Device() {

    }

    public String getStatus() {
        return isActive ? "Active" : "Disabled";
    }

    public String getRoomName() {
        return room.getRoomName();
    }

    public Float getElectricPower() {
        return isActive ? electricPowerDefault : 0;
    }
}
