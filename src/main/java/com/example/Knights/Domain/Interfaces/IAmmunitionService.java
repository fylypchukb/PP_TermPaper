package com.example.Knights.Domain.Interfaces;

import com.example.Knights.Data.Entities.Ammunition.Ammunition;
import com.example.Knights.Domain.Response.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface IAmmunitionService<T extends Ammunition> {
   <E> ResponseEntity<BaseResponse> addAmmunition(E ammunition);

    <E> ResponseEntity<BaseResponse> updateAmmunition(E ammunition,Long id);

    <E> ResponseEntity<BaseResponse> deleteAmmunition(Long id);
}
