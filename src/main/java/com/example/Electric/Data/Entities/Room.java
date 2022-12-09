package com.example.Electric.Data.Entities;

import com.example.Electric.Domain.Services.roomElectric;
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

    @OneToMany(fetch = FetchType.EAGER ,targetEntity = Device.class, mappedBy = "room")
    private List<Device> devices;

    public Integer getDevicesCount(){
        return devices.size();
    }

    public Float getPowerConsumption(){
        return roomElectric.roomConsumption(devices);
    }
}
