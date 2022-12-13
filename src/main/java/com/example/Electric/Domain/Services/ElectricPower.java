package com.example.Electric.Domain.Services;

import com.example.Electric.Data.Entities.Device;
import com.example.Electric.Domain.Interfaces.IElectricPower;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ElectricPower implements IElectricPower {
    @Override
    public Float generalConsumption(ObservableList<Device> list) {
        Float toReturn = 0f;
        for (var device : list) {
            toReturn += device.getElectricPower();
        }

        return toReturn;
    }


}
