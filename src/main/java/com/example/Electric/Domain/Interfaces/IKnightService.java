package com.example.Electric.Domain.Interfaces;


import com.example.Electric.Data.Entities.Ammunition.Ammunition;
import com.example.Electric.Data.Entities.Knight.Knight;
import com.example.Electric.Domain.Response.BaseResponse;
import com.example.Electric.Domain.Response.RestException;
import javafx.collections.ObservableList;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IKnightService {
     ResponseEntity<BaseResponse> addKnight(Knight knightViewModel);

     ResponseEntity<BaseResponse> updateKnight(Knight knightViewModel, Long id);

     ResponseEntity<BaseResponse> deleteKnight(Long id);

     ResponseEntity<ObservableList<Ammunition>> knightAmmunition(Long id) throws RestException;

     ResponseEntity<ObservableList<Ammunition>> knightAmmunitionByWeight(Long id) throws RestException;

     ResponseEntity<ObservableList<Ammunition>> knightAmmunitionByCost(Long id) throws RestException;

     ResponseEntity<ObservableList<Ammunition>> findAmmunitionByCostRange(Long id,int lLim,int rLim) throws RestException;

     ResponseEntity<ObservableList<Knight>> getAllKnights();
}
