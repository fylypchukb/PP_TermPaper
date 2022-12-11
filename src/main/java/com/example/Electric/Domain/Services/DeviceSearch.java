package com.example.Electric.Domain.Services;

import com.example.Electric.Data.Entities.Device;
import com.example.Electric.Domain.Interfaces.IDeviceSearch;
import javafx.collections.FXCollections;
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

    @Override
    public ObservableList<Device> searchByName(ObservableList<Device> list, String searchInput) {
        searchInput = searchInput.trim();

        ObservableList<Device> toReturn = FXCollections.observableArrayList();
        for (var item :
                list) {
            if (item.getDeviceName().toLowerCase().contains(searchInput.toLowerCase())) {
                toReturn.add(item);
            }
        }

        return toReturn;
    }
}
