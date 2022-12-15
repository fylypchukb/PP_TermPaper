package com.example.Electric.Domain.Services;

import com.example.Electric.Data.Entities.Device;
import com.example.Electric.Domain.Interfaces.IElectricPower;
import com.example.Electric.WebApi.Controllers.MainController;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ElectricPower implements IElectricPower {
    Logger logger = LoggerFactory.getLogger(ElectricPower.class);

    @Override
    public Float generalConsumption(ObservableList<Device> list) {
        Float toReturn = 0f;
        for (var device : list) {
            toReturn += device.getElectricPower();
        }

        logger.info("Calcualted consumption - " + toReturn);

        return toReturn;
    }


}
