package com.example.Knights.Domain.Services;

import com.example.Knights.Data.Entities.Ammunition.Helm;
import com.example.Knights.Domain.Response.BaseResponse;
import com.example.Knights.Domain.Repositories.IGenericRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class HelmService extends AmmunitionService<Helm> {
    private final IGenericRepository<Helm> helmRepository;

    public HelmService(IGenericRepository<Helm> helmRepository) {
        super(helmRepository);
        this.helmRepository = helmRepository;
    }
    @Override
    public ResponseEntity<BaseResponse> addAmmunition(Object ammunition) {
        try {
            Helm helm=(Helm) ammunition;
            helmRepository.save(new Helm(helm.getName(), helm.getCanTakeDamage(), helm.getSize(), helm.isCloseHelm(), helm.getPrice(), helm.getWeight()));
            return new ResponseEntity<>(new BaseResponse("Ammunition Helm was added"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse("Ammunition Helm was not added"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<BaseResponse> updateAmmunition(Object ammunition,Long id) {
        try {
            Helm helmViewModel=(Helm)ammunition;
            var helm=helmRepository.findById(id);

            if(helm.isEmpty())
            {
                return new ResponseEntity<>(new BaseResponse("Ammunition Helm was not found"), HttpStatus.NOT_FOUND);
            }
            Helm updateHelm=helm.get();

            updateHelm.setName(helmViewModel.getName());
            updateHelm.setSize(helmViewModel.getSize());
            updateHelm.setCloseHelm(helmViewModel.isCloseHelm());
            updateHelm.setCanTakeDamage(helmViewModel.getCanTakeDamage());
            updateHelm.setPrice(helmViewModel.getPrice());
            updateHelm.setWeight(helmViewModel.getWeight());

            helmRepository.save(updateHelm);

            return new ResponseEntity<>(new BaseResponse("Ammunition Helm was updated"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse("Ammunition Helm was not updated"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
