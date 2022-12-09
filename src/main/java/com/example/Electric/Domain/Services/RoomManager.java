package com.example.Electric.Domain.Services;

import com.example.Electric.Data.Entities.Device;
import com.example.Electric.Data.Entities.Room;
import com.example.Electric.Domain.Interfaces.IRoomManager;
import com.example.Electric.Domain.Repositories.IDeviceRepository;
import com.example.Electric.Domain.Repositories.IRoomRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Transactional
public class RoomManager implements IRoomManager {
    private final IRoomRepository roomRepository;
    private final IDeviceRepository deviceRepository;

    public RoomManager(IRoomRepository roomRepository, IDeviceRepository deviceRepository) {
        this.roomRepository = roomRepository;
        this.deviceRepository = deviceRepository;
    }

    @Override
    public ObservableList<Room> allRooms() {
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        var dev = roomRepository.findAll();
        dev.forEach(rooms::add);
        return rooms;
    }

    @Override
    public Integer countDevices(Room room) {
        ObservableList<Device> devices = FXCollections.observableArrayList();
        var dev = deviceRepository.findAll();
        dev.forEach(devices::add);

        Integer count = 0;
        for (var item : devices) {
            if (Objects.equals(item.getRoomID(), room.getRoom_Id()))
                count++;
        }

        return count;
    }
}