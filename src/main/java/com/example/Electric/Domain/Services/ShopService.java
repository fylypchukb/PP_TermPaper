package com.example.Electric.Domain.Services;

import com.example.Electric.Data.Entities.Ammunition.Ammunition;
import com.example.Electric.Data.Entities.Knight.Knight;
import com.example.Electric.Domain.Response.BaseResponse;
import com.example.Electric.Domain.Interfaces.IShopService;
import com.example.Electric.Domain.Repositories.IGenericRepository;
import com.example.Electric.Domain.Repositories.IKnightRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ShopService implements IShopService {
    private final IGenericRepository<Ammunition> ammunitionRepository;
    private final IKnightRepository knightRepository;

    public ShopService(IGenericRepository<Ammunition> ammunitionRepository, IKnightRepository knightRepository) {
        this.ammunitionRepository = ammunitionRepository;
        this.knightRepository = knightRepository;
    }

    @Override
    public ResponseEntity<BaseResponse> buyAmmunition(Long ammunitionId, Long knightId) {
        Optional<Knight> optionalKnight = knightRepository.findById(knightId);
        if (optionalKnight.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse("Knight was not found"), HttpStatus.NOT_FOUND);
        }
        Knight knight = optionalKnight.get();

        Optional<Ammunition> optionalAmmunition = ammunitionRepository.findById(ammunitionId);
        if (optionalAmmunition.isEmpty()) {
            return new ResponseEntity<>(new BaseResponse("Ammunition was not found"), HttpStatus.NOT_FOUND);
        }
        Ammunition ammunition = optionalAmmunition.get();
        try {
            knight.getAmmunitions().add(ammunition);
            knightRepository.save(knight);
            return new ResponseEntity<>(new BaseResponse("Ammunition was bought"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse("Ammunition was not bought"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ObservableList<Ammunition>> allAmmunition() {

        ObservableList<Ammunition> ammunition= FXCollections.observableArrayList();
        var amm=ammunitionRepository.findAll();
        amm.forEach(ammunition::add);
        return new ResponseEntity<>(ammunition, HttpStatus.OK);
    }

}
