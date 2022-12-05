package com.example.Electric.Domain.Services;

import com.example.Electric.Data.Entities.Ammunition.Armor;
import com.example.Electric.Domain.Response.BaseResponse;
import com.example.Electric.Domain.Repositories.IGenericRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ArmorService extends AmmunitionService<Armor> {


    private final IGenericRepository<Armor> armorRepository;

    public ArmorService(IGenericRepository<Armor> armorRepository) {
        super(armorRepository);
        this.armorRepository = armorRepository;
    }
    @Override
    public ResponseEntity<BaseResponse> addAmmunition(Object ammunition) {
        try {
            Armor armor=(Armor) ammunition;
            armorRepository.save(new Armor(armor.getName(), armor.getCanTakeDamage(), armor.getSize(), armor.isCeremonial(), armor.getPrice(), armor.getWeight()));
            return new ResponseEntity<>(new BaseResponse("Ammunition Armor was added"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse("Ammunition Armor was not added"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<BaseResponse> updateAmmunition(Object ammunition,Long id) {
        try {
            Armor armorViewModel=(Armor)ammunition;
            var armor=armorRepository.findById(id);

            if(armor.isEmpty())
            {
                return new ResponseEntity<>(new BaseResponse("Ammunition Armor was not found"), HttpStatus.NOT_FOUND);
            }
            Armor updateArmor=armor.get();

            updateArmor.setName(armorViewModel.getName());
            updateArmor.setSize(armorViewModel.getSize());
            updateArmor.setCeremonial(armorViewModel.isCeremonial());
            updateArmor.setCanTakeDamage(armorViewModel.getCanTakeDamage());
            updateArmor.setPrice(armorViewModel.getPrice());
            updateArmor.setWeight(armorViewModel.getWeight());

            armorRepository.save(updateArmor);

            return new ResponseEntity<>(new BaseResponse("Ammunition Armor was updated"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse("Ammunition Armor was not updated"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

