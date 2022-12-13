package com.example.Electric.Domain.Interfaces;

import com.example.Electric.Data.Entities.Device;
import javafx.collections.ObservableList;

public interface IElectricPower {
    Float generalConsumption(ObservableList<Device> list);

}
