package com.example.Electric.Data.Entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="Rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Integer room_Id;

    @Column(name = "room_name",nullable = false)
    private String roomName;

    @OneToMany(targetEntity = Device.class, mappedBy = "room")
    private List<Device> devices;

    public Integer getDevicesCount(){
        return devices.size();
    }
}
