package com.example.Electric.Domain.Interfaces;

import com.example.Electric.Data.Entities.Room;
import javafx.collections.ObservableList;

public interface IRoomManager {
    ObservableList<Room> allRooms();

    Integer countDevices(Room room);
}
