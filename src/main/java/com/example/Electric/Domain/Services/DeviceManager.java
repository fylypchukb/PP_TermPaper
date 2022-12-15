package com.example.Electric.Domain.Services;

import com.example.Electric.Data.Entities.Device;
import com.example.Electric.Data.Entities.Room;
import com.example.Electric.Domain.Interfaces.IDeviceManager;
import com.example.Electric.Domain.Repositories.IDeviceRepository;
import com.example.Electric.WebApi.Controllers.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Transactional
public class DeviceManager implements IDeviceManager {
    private final IDeviceRepository deviceRepository;
    Logger logger = LoggerFactory.getLogger(DeviceManager.class);

    public DeviceManager(IDeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public ObservableList<Device> allDevices() {
        ObservableList<Device> devices = FXCollections.observableArrayList();
        var dev = deviceRepository.findAll();
        dev.forEach(devices::add);

        logger.info("Returned all devices");

        return devices;
    }

    @Override
    public void switchDevice(Device device) {
        var repOpt = deviceRepository.findById(device.getDevice_id());

        Device fromRep;
        if (repOpt.isPresent()){
            fromRep = repOpt.get();
            fromRep.setIsActive(!device.getIsActive());
            logger.info("Switched " + device.getDeviceName() + "to " + device.getStatus());
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

        logger.info("Filtered devices by status. Request: " + status);

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

        logger.info("Filter devices by room. Room:" + room.getRoomName());

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
            logger.info("Renamed " + fromRep.getDevice_id() + " to \"" + newName + "\"");
        }
    }

    @Override
    public void deleteDevices(ObservableList<Device> devices) {
        deviceRepository.deleteAll(devices);

        logger.info("Deleted devices");
    }
}
