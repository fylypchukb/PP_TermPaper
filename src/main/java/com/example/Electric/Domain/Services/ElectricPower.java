package com.example.Electric.Domain.Services;

import com.example.Electric.Data.Entities.Device;
import com.example.Electric.Domain.Interfaces.IElectricPower;
import com.example.Electric.Domain.Repositories.IDeviceRepository;
import javafx.collections.FXCollections;
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
    public Float GeneralConsumption() {
        ObservableList<Device> list = FXCollections.observableArrayList();
        var rep = repository.findAll();
        rep.forEach(list::add);

        Float toReturn = 0f;
        for (var device : list) {
            toReturn += device.getElectricPower();
        }

        return toReturn;
    }
}
