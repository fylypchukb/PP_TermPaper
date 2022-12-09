package com.example.Electric.Domain.Interfaces;

import com.example.Electric.Data.Entities.Device;
import com.example.Electric.Data.Entities.Room;
import javafx.collections.ObservableList;

import java.util.List;

public interface IElectricPower {
    Float generalConsumption(ObservableList<Device> list);

}
