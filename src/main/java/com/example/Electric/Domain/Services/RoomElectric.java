package com.example.Electric.Domain.Services;

import com.example.Electric.Data.Entities.Device;

import java.util.List;

public class RoomElectric {
    public static Float roomConsumption(List<Device> list) {
        Float toReturn = 0f;

        for (var item : list)
            toReturn += item.getElectricPower();

        return toReturn;
    }
}
