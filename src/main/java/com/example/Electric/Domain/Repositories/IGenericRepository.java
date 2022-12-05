package com.example.Electric.Domain.Repositories;

import com.example.Electric.Data.Entities.Ammunition.Ammunition;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGenericRepository<T extends Ammunition>extends CrudRepository<T,Long>, JpaSpecificationExecutor<T> {

}
