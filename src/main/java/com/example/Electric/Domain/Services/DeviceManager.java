package com.example.Electric.Domain.Services;

import com.example.Electric.Data.Entities.Device;
import com.example.Electric.Data.Entities.Room;
import com.example.Electric.Domain.Interfaces.IDeviceManager;
import com.example.Electric.Domain.Repositories.IDeviceRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Transactional
public class DeviceManager implements IDeviceManager {
    private final IDeviceRepository deviceRepository;

    public DeviceManager(IDeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public ObservableList<Device> allDevices() {
        ObservableList<Device> devices = FXCollections.observableArrayList();
        var dev = deviceRepository.findAll();
        dev.forEach(devices::add);
        return devices;
    }

    @Override
    public void switchDevice(Device device) {
        var repOpt = deviceRepository.findById(device.getDevice_id());

        Device fromRep;
        if (repOpt.isPresent()){
            fromRep = repOpt.get();
            fromRep.setIsActive(!device.getIsActive());
        }
    }

    @Override
    public ObservableList<Device> filterStatusDevices(ObservableList<Device> devices, Boolean status) {
        for (int i = 0; i < devices.size(); i++) {
            if (devices.get(i).getIsActive() != status) {
                devices.remove(i);
                i--;
            }
        }

        return devices;
    }

    @Override
    public ObservableList<Device> filterRoomDevices(ObservableList<Device> devices, Room room) {
        for (int i = 0; i < devices.size(); i++){
            if (!Objects.equals(devices.get(i).getRoomID(), room.getRoom_Id())){
                devices.remove(i);
                i--;
            }
        }

        return devices;
    }

    @Override
    public void addDevice(Device device) {
        deviceRepository.save(device);
    }

    @Override
    public void updateDeviceName(Device device, String newName) {
        var repOpt = deviceRepository.findById(device.getDevice_id());

        Device fromRep;
        if(repOpt.isPresent()){
            fromRep = repOpt.get();
            fromRep.setDeviceName(newName);
        }
    }

    @Override
    public void deleteDevices(ObservableList<Device> devices) {
        deviceRepository.deleteAll(devices);
    }
}
