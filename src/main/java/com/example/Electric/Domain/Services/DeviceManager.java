package com.example.Electric.Domain.Services;

import com.example.Electric.Data.Entities.Device;
import com.example.Electric.Domain.Interfaces.IDeviceManager;
import com.example.Electric.Domain.Repositories.IDeviceRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
        var found = deviceRepository.findById(device.getDevice_id()).get();

        found.setIsActive(!device.getIsActive());
    }

    @Override
    public ObservableList<Device> filteredStatusDevices(ObservableList<Device> devices, Boolean status) {
        for (int i = 0; i < devices.size(); i++) {
            if (devices.get(i).getIsActive() != status) {
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
        Device fromRep = deviceRepository.findById(device.getDevice_id()).get();

        fromRep.setDeviceName(newName);
    }
}
