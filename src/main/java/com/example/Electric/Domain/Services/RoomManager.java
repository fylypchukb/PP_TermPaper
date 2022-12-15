package com.example.Electric.Domain.Services;

import com.example.Electric.Data.Entities.Room;
import com.example.Electric.Domain.Interfaces.IRoomManager;
import com.example.Electric.Domain.Repositories.IRoomRepository;
import com.example.Electric.WebApi.Controllers.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoomManager implements IRoomManager {
    private final IRoomRepository roomRepository;
    Logger logger = LoggerFactory.getLogger(RoomManager.class);

    public RoomManager(IRoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public ObservableList<Room> allRooms() {
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        var dev = roomRepository.findAll();
        dev.forEach(rooms::add);

        logger.info("Returned all rooms from DB");

        return rooms;
    }

    @Override
    public Room getByName(String name) {
        ObservableList<Room> rooms = allRooms();

        for (var item : rooms) {
            if (item.getRoomName().compareTo(name) == 0) {
                logger.info("Returned room " + item.getRoom_Id() + ". Request- " + name);
                return item;
            }
        }

        return null;
    }

    @Override
    public void addRoom(Room room) {
        roomRepository.save(room);
        logger.info("Added room" + room.getRoom_Id());
    }

    @Override
    public void deleteRooms(ObservableList<Room> rooms) {
        roomRepository.deleteAll(rooms);
        logger.info("Deleted rooms");
    }

    @Override
    public void updateRoomName(Room room, String name) {
        var repOpt = roomRepository.findById(room.getRoom_Id());

        Room fromRep;
        if (repOpt.isPresent()) {
            fromRep = repOpt.get();
            fromRep.setRoomName(name);
            logger.info("Updated room name " + fromRep.getRoom_Id() + " - " + fromRep.getRoomName());
        }
    }
}
