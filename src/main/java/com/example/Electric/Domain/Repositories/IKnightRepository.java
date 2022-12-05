package com.example.Electric.Domain.Repositories;

import com.example.Electric.Data.Entities.Knight.Knight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IKnightRepository extends CrudRepository<Knight,Long> {
}
