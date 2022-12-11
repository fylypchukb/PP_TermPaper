package com.example.Electric.Domain.Interfaces;

import com.example.Electric.Data.Entities.Device;
import com.example.Electric.Data.Entities.Room;
import javafx.collections.ObservableList;

public interface IDeviceManager {
    ObservableList<Device> allDevices();
    void switchDevice(Device device);
    ObservableList<Device> filterStatusDevices(ObservableList<Device> devices, Boolean status);
    ObservableList<Device> filterRoomDevices(ObservableList<Device> devices, Room room);
    void addDevice(Device device);
    void updateDeviceName(Device device, String newName);
    void deleteDevices(ObservableList<Device> devices);
}
