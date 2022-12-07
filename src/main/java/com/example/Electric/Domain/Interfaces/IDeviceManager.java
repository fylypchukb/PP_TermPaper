package com.example.Electric.Domain.Interfaces;

import com.example.Electric.Data.Entities.Device;
import javafx.collections.ObservableList;

public interface IDeviceManager {
    ObservableList<Device> allDevices();
}
