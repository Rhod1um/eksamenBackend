package com.example.eksamentemplate.service;

import com.example.eksamentemplate.exception.ObjectAlreadyExistsException;
import com.example.eksamentemplate.exception.ResourceNotFoundException;
import com.example.eksamentemplate.model.Race;
import com.example.eksamentemplate.repository.RaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaceService {
    private final RaceRepo raceRepo;
    @Autowired
    public RaceService(RaceRepo raceRepo) {
        this.raceRepo = raceRepo;
    }
    //GET all
    public List<Race> getAll() {
        return raceRepo.findAll();
    }
    //GET by id
    public ResponseEntity<Race> getById(Integer id) {
        if (raceRepo.findById(id).isPresent()){
            return new ResponseEntity<>(raceRepo.findById(id).get(), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Kapsejlads med id " + id + " blev ikke fundet");
        }
    }
    //POST
    public ResponseEntity<Race> create(Race race) {
        //post er non-idempotent, så tjek om id findes i forvejen
        int id = race.getRaceId();
        if (raceRepo.findById(id).isEmpty()){
            try {
                raceRepo.save(race);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); //kan smide sin egen her
            } //man får den præcise fejl i fejlbesked
            return new ResponseEntity<>(raceRepo.save(race), HttpStatus.CREATED);
        } else {
            throw new ObjectAlreadyExistsException("Kapsejlads med id " + id + " findes allerede");
        }
    }
    //PUT
    public ResponseEntity<Race> update(Race updatedRace) {
        //PUT er idempotent og skal derfor tjekke id, for at den opdatere en der allerede eksisterer
        int id = updatedRace.getRaceId();
        if (raceRepo.findById(id).isPresent()){
            return new ResponseEntity<>(raceRepo.save(updatedRace), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Kapsejlads med id " + id + " blev ikke fundet");
        }
    }
    //DELETE
    public ResponseEntity<Race> delete(Integer id) {
        if (raceRepo.findById(id).isPresent()){
            Race race = raceRepo.findById(id).get();
            raceRepo.deleteById(id);
            return new ResponseEntity<>(race, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Kapsejlads med id " + id + " blev ikke fundet");
        }
    }
    //COUNT
    public Integer countAll(){
        return raceRepo.countAll();
    }
}
