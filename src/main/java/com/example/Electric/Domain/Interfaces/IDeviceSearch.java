package com.example.Electric.Domain.Interfaces;

import com.example.Electric.Data.Entities.Device;
import javafx.collections.ObservableList;

public interface IDeviceSearch {
    ObservableList<Device> rangeDevicesSearch (ObservableList<Device> devices, Float powerFrom, Float powerTo);
    ObservableList<Device> searchByName (ObservableList<Device> list, String searchInput);
}
