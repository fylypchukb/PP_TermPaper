package com.example.Knights.Domain.CommandPattern;

import com.example.Knights.Data.Entities.Ammunition.Ammunition;
import com.example.Knights.Domain.Response.RestException;
import com.example.Knights.Domain.Services.KnightService;
import javafx.collections.ObservableList;
import org.springframework.http.ResponseEntity;


public class KnightAmmunitionByWeightCommand implements Command<ResponseEntity<ObservableList<Ammunition>>>{
    private final KnightService knightService;
    private final long knightId;

    public KnightAmmunitionByWeightCommand(KnightService knightService, long knightId) {
        this.knightService = knightService;
        this.knightId = knightId;
    }

    @Override
    public ResponseEntity<ObservableList<Ammunition>> execute()throws RestException {
        return knightService.knightAmmunitionByWeight(knightId);
    }
}
