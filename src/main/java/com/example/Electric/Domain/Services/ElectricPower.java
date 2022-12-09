package com.example.Electric.Domain.Services;

import com.example.Electric.Data.Entities.Device;
import com.example.Electric.Domain.Interfaces.IElectricPower;
import com.example.Electric.Domain.Repositories.IDeviceRepository;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ElectricPower implements IElectricPower {
    private IDeviceRepository repository;

    public ElectricPower(IDeviceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Float generalConsumption(ObservableList<Device> list) {
        Float toReturn = 0f;
        for (var device : list) {
            toReturn += device.getElectricPower();
        }

        return toReturn;
    }


}
