package com.example.Electric.Domain.Services;

import com.example.Electric.Data.Entities.Device;
import com.example.Electric.Domain.Interfaces.IDeviceSearch;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DeviceSearch implements IDeviceSearch {

    @Override
    public ObservableList<Device> rangeDevicesSearch(ObservableList<Device> devices, Float powerFrom, Float powerTo) {
        for (int i = 0; i < devices.size(); i++) {
            if (devices.get(i).getElectricPowerDefault() < powerFrom || devices.get(i).getElectricPowerDefault() > powerTo) {
                devices.remove(i);
                i--;
            }
        }

        return devices;
    }
}
