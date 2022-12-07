package com.example.Electric.Domain.Repositories;

import com.example.Electric.Data.Entities.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDeviceRepository extends CrudRepository<Device, Integer> {
}
