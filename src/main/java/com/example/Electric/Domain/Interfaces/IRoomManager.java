package com.example.Electric.Domain.Interfaces;

import com.example.Electric.Data.Entities.Room;
import javafx.collections.ObservableList;

public interface IRoomManager {
    ObservableList<Room> allRooms();
    Integer countDevices(Room room);
    Room getByName(String name);
    void addRoom(Room room);
    void deleteRooms(ObservableList<Room> rooms);
    void updateRoomName(Room room, String name);
}
