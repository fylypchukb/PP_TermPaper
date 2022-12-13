package com.example.Electric.Domain.Services;

import com.example.Electric.Data.Entities.Room;
import com.example.Electric.Domain.Interfaces.IRoomManager;
import com.example.Electric.Domain.Repositories.IRoomRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoomManager implements IRoomManager {
    private final IRoomRepository roomRepository;

    public RoomManager(IRoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public ObservableList<Room> allRooms() {
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        var dev = roomRepository.findAll();
        dev.forEach(rooms::add);
        return rooms;
    }

    @Override
    public Room getByName(String name) {
        ObservableList<Room> rooms = allRooms();

        for (var item : rooms) {
            if (item.getRoomName().compareTo(name) == 0)
                return item;
        }

        return null;
    }

    @Override
    public void addRoom(Room room) {
        roomRepository.save(room);
    }

    @Override
    public void deleteRooms(ObservableList<Room> rooms) {
        roomRepository.deleteAll(rooms);
    }

    @Override
    public void updateRoomName(Room room, String name) {
        var repOpt = roomRepository.findById(room.getRoom_Id());

        Room fromRep;
        if (repOpt.isPresent()) {
            fromRep = repOpt.get();
            fromRep.setRoomName(name);
        }
    }
}
