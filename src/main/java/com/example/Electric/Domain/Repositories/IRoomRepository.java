package com.example.Electric.Domain.Repositories;

import com.example.Electric.Data.Entities.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;

@Repository
public interface IRoomRepository extends CrudRepository<Room, Integer> {
}
