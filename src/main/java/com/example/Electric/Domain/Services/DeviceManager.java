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
}
