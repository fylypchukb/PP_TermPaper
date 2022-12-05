package com.example.Electric.Domain.Services;

import com.example.Electric.Data.Entities.Ammunition.Sword;
import com.example.Electric.Domain.Response.BaseResponse;
import com.example.Electric.Domain.Repositories.IGenericRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SwordService extends AmmunitionService<Sword> {

    private final IGenericRepository<Sword> swordRepository;

    public SwordService(IGenericRepository<Sword> repository) {
        super(repository);
        this.swordRepository=repository;
    }

    @Override
    public ResponseEntity<BaseResponse> addAmmunition(Object ammunition) {
        try {
            Sword sword=(Sword) ammunition;
            swordRepository.save(new Sword(sword.getName(), sword.isTwoHanded(),sword.getDamage(),sword.getPrice(), sword.getWeight()));
            return new ResponseEntity<>(new BaseResponse("Ammunition Sword was added"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse("Ammunition Sword was not added"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<BaseResponse> updateAmmunition(Object ammunition,Long id) {
        try {
            Sword swordViewModel=(Sword)ammunition;
            var sword=swordRepository.findById(id);

            if(sword.isEmpty())
            {
                return new ResponseEntity<>(new BaseResponse("Ammunition Sword was not found"), HttpStatus.NOT_FOUND);
            }
            Sword updateSword=sword.get();

            updateSword.setName(swordViewModel.getName());
            updateSword.setDamage(swordViewModel.getDamage());
            updateSword.setTwoHanded(swordViewModel.isTwoHanded());
            updateSword.setPrice(swordViewModel.getPrice());
            updateSword.setWeight(swordViewModel.getWeight());

            swordRepository.save(updateSword);

            return new ResponseEntity<>(new BaseResponse("Ammunition Sword was updated"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse("Ammunition Sword was not updated"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
