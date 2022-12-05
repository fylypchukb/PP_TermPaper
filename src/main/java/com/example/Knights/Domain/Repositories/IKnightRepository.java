package com.example.Knights.Domain.Repositories;

import com.example.Knights.Data.Entities.Knight.Knight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IKnightRepository extends CrudRepository<Knight,Long> {
}
