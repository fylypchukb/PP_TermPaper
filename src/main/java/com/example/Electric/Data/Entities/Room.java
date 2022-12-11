package com.example.Electric.Data.Entities;

import com.example.Electric.Domain.Services.RoomElectric;
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

    public Room(String roomName) {
        this.roomName = roomName;
    }

    public Room() {

    }

    public Integer getDevicesCount(){
        return devices.size();
    }

    public Float getPowerConsumption(){
        return RoomElectric.roomConsumption(devices);
    }
}
