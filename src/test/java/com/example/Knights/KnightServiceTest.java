package com.example.Knights;

import com.example.Electric.Data.Entities.Ammunition.Ammunition;
import com.example.Electric.Data.Entities.Ammunition.Helm;
import com.example.Electric.Data.Entities.Ammunition.Shield;
import com.example.Electric.Data.Entities.Knight.Knight;
import com.example.Electric.Domain.Response.RestException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class KnightServiceTest {
    @Mock
    private IKnightRepository knightRepository;
    @InjectMocks
    private KnightService knightService;

    @Test
    public void KnightAmmunitionKnightNotFound(){
        Long id= 0L;

        given(knightRepository.findById(id)).willReturn(Optional.empty());

        assertThrows(RestException.class,()->knightService.knightAmmunition(id));
    }

    @Test
    public void KnightAmmunitionSuccess(){
        Long id= 0L;
        Knight knight=new Knight("Duke","Ron",23,123);
        ObservableList<Ammunition> amm= FXCollections.observableArrayList();
        ResponseEntity<ObservableList<Ammunition>> response=new ResponseEntity<>(amm, HttpStatus.OK);
        given(knightRepository.findById(id)).willReturn(Optional.of(knight));

        try{
            final ResponseEntity<ObservableList<Ammunition>> ammunition=knightService.knightAmmunition(id);
            assertEquals(response,ammunition);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void KnightAmmunitionByWeightSuccess(){
        Long id= 0L;
        Knight knight=new Knight("Duke","Ron",23,123);
        List<Ammunition> amm=new ArrayList<>();
        Helm helm=new Helm("helm",123,"S",true,123.1,30);
        Shield shield=new Shield("shield",123,"S",100,50);
        amm.add(helm);
        amm.add(shield);
        knight.setAmmunitions(amm);

        given(knightRepository.findById(id)).willReturn(Optional.of(knight));

        try{
            final ResponseEntity<ObservableList<Ammunition>> ammunition=knightService.knightAmmunitionByWeight(id);
           assertEquals(amm.get(0),ammunition.getBody().get(0));
            assertEquals(amm.get(1),ammunition.getBody().get(1));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void KnightAmmunitionByPriceSuccess(){
        Long id= 0L;
        Knight knight=new Knight("Duke","Ron",23,123);
        List<Ammunition> amm=new ArrayList<>();
        Helm helm=new Helm("helm",123,"S",true,123.1,30);
        Shield shield=new Shield("shield",123,"S",100,50);
        amm.add(helm);
        amm.add(shield);
        knight.setAmmunitions(amm);

        given(knightRepository.findById(id)).willReturn(Optional.of(knight));

        try{
            final ResponseEntity<ObservableList<Ammunition>> ammunition=knightService.knightAmmunitionByCost(id);
            assertEquals(amm.get(1),ammunition.getBody().get(1));
            assertEquals(amm.get(0),ammunition.getBody().get(0));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void KnightAmmunitionByPriceRangeSuccess(){
        Long id= 0L;
        Knight knight=new Knight("Duke","Ron",23,123);
        List<Ammunition> amm=new ArrayList<>();
        Helm helm=new Helm("helm",123,"S",true,123.1,30);
        Shield shield=new Shield("shield",123,"S",100,50);
        amm.add(helm);
        amm.add(shield);
        knight.setAmmunitions(amm);

        given(knightRepository.findById(id)).willReturn(Optional.of(knight));

        try{
            final ResponseEntity<ObservableList<Ammunition>> ammunition=knightService.findAmmunitionByCostRange(id,90,105);
            assertEquals(Objects.requireNonNull(ammunition.getBody()).size(),1);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
