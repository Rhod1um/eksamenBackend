package com.example.eksamentemplate.service;

import com.example.eksamentemplate.exception.ResourceNotFoundException;
import com.example.eksamentemplate.model.BoatType;
import com.example.eksamentemplate.repository.BoatTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class BoatTypeService {
    private final BoatTypeRepo boatTypeRepo;
    @Autowired
    public BoatTypeService(BoatTypeRepo boatTypeRepo) {
        this.boatTypeRepo = boatTypeRepo;
    }
    //GET all
    public List<BoatType> getAll() {
        return boatTypeRepo.findAll();
    }
    //GET by id
    public ResponseEntity<BoatType> getById(Integer id) {
        if (boatTypeRepo.findById(id).isPresent()){
            return new ResponseEntity<>(boatTypeRepo.findById(id).get(), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("BoatType med id " + id + " blev ikke fundet");
        }
    }
    //POST
    public ResponseEntity<BoatType> create(BoatType boatType) {
        //post er non-idempotent, så tjek om id findes i forvejen
        try {
            boatTypeRepo.save(boatType);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); //kan smide sin egen her
        } //man får den præcise fejl i fejlbesked
        return new ResponseEntity<>(boatTypeRepo.save(boatType), HttpStatus.CREATED);
    }
    //PUT
    public ResponseEntity<BoatType> update(BoatType updatedBoatType) {
        //PUT er idempotent og skal derfor tjekke id, for at den opdatere en der allerede eksisterer
        int id = updatedBoatType.getBoatTypeId();
        if (boatTypeRepo.findById(id).isPresent()){
            return new ResponseEntity<>(boatTypeRepo.save(updatedBoatType), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("BoatType med id " + id + " blev ikke fundet");
        }
    }
    //DELETE
    public ResponseEntity<BoatType> delete(Integer id) {
        if (boatTypeRepo.findById(id).isPresent()){
            BoatType boatType = boatTypeRepo.findById(id).get();
            boatTypeRepo.deleteById(id);
            return new ResponseEntity<>(boatType, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("BoatType med id " + id + " blev ikke fundet");
        }
    }
    //COUNT
    public Integer countAll(){
        return boatTypeRepo.countAll();
    }
}
