package com.example.Knights.Domain.Services;

import com.example.Knights.Data.Entities.Ammunition.Ammunition;
import com.example.Knights.Domain.Response.BaseResponse;
import com.example.Knights.Domain.Interfaces.IAmmunitionService;
import com.example.Knights.Domain.Repositories.IGenericRepository;
import org.springframework.core.GenericTypeResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public  class AmmunitionService<T extends Ammunition> implements IAmmunitionService<T> {
    private final IGenericRepository<T> repository;
    private final Class<T> genericType;
    public AmmunitionService(IGenericRepository<T> repository) {
        this.repository = repository;
        this.genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), AmmunitionService.class);
    }

    @Override
    public ResponseEntity<BaseResponse> addAmmunition(Object ammunition) {
        return null;
    }

    @Override
    public ResponseEntity<BaseResponse> updateAmmunition(Object ammunition, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<BaseResponse> deleteAmmunition(Long id) {
        try {

            if(!repository.existsById(id))
            {
                return new ResponseEntity<>(new BaseResponse("Ammunition "+this.genericType.getSimpleName() +" was not found"), HttpStatus.NOT_FOUND);
            }

            repository.deleteById(id);

            return new ResponseEntity<>(new BaseResponse("Ammunition was deleted"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse("Ammunition was not deleted"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
