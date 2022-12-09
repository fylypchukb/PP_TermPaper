package com.example.Electric.Domain.Interfaces;

import com.example.Electric.Data.Entities.Device;
import javafx.collections.ObservableList;

public interface IDeviceManager {
    ObservableList<Device> allDevices();

    void switchDevice(Device device);

    ObservableList<Device> filteredStatusDevices(ObservableList<Device> devices, Boolean status);
}
