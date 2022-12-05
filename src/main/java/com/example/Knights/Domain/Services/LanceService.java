package com.example.Knights.Domain.Services;

import com.example.Knights.Data.Entities.Ammunition.Lance;
import com.example.Knights.Domain.Response.BaseResponse;
import com.example.Knights.Domain.Repositories.IGenericRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LanceService extends AmmunitionService<Lance> {
    private final IGenericRepository<Lance> lanceRepository;

    public LanceService(IGenericRepository<Lance> repository) {
        super(repository);
        this.lanceRepository=repository;
    }

    @Override
    public ResponseEntity<BaseResponse> addAmmunition(Object ammunition) {
        try {
            Lance lance=(Lance) ammunition;
            lanceRepository.save(new Lance(lance.getName(),lance.getLength(), lance.getDamage(),lance.getPrice(), lance.getWeight()));
            return new ResponseEntity<>(new BaseResponse("Ammunition Lance was added"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse("Ammunition Lance was not added"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<BaseResponse> updateAmmunition(Object ammunition,Long id) {
        try {
            Lance lanceViewModel=(Lance)ammunition;
            var lance=lanceRepository.findById(id);

            if(lance.isEmpty())
            {
                return new ResponseEntity<>(new BaseResponse("Ammunition Lance was not found"), HttpStatus.NOT_FOUND);
            }
            Lance updateLance=lance.get();

            updateLance.setName(lanceViewModel.getName());
            updateLance.setDamage(lanceViewModel.getDamage());
            updateLance.setLength(lanceViewModel.getLength());
            updateLance.setPrice(lanceViewModel.getPrice());
            updateLance.setWeight(lanceViewModel.getWeight());

            lanceRepository.save(updateLance);

            return new ResponseEntity<>(new BaseResponse("Ammunition Lance was updated"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse("Ammunition Lance was not updated"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
