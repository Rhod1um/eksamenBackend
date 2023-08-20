package com.example.eksamentemplate.service;

import com.example.eksamentemplate.exception.ObjectAlreadyExistsException;
import com.example.eksamentemplate.exception.ResourceNotFoundException;
import com.example.eksamentemplate.model.Boat;
import com.example.eksamentemplate.repository.BoatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoatService {
    private final BoatRepo boatRepo;
    @Autowired
    public BoatService(BoatRepo boatRepo) {
        this.boatRepo = boatRepo;
    }
    //GET all
    public List<Boat> getAll() {
        return boatRepo.findAll();
    }
    //GET by id
    public ResponseEntity<Boat> getById(Integer id) {
        if (boatRepo.findById(id).isPresent()){
            return new ResponseEntity<>(boatRepo.findById(id).get(), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Båd med id " + id + " blev ikke fundet");
        }
    }
    //POST
    public ResponseEntity<Boat> create(Boat boat) {
        int id = boat.getBoatId();
        if (boatRepo.findById(id).isEmpty()){
            try {
                boatRepo.save(boat);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(boatRepo.save(boat), HttpStatus.CREATED); //sker hvis den ikke catcher
        } else {
            throw new ObjectAlreadyExistsException("Båd med id " + id + " findes allerede");
        }
    }
    //PUT
    public ResponseEntity<Boat> update(@RequestBody Boat updatedBoat) {
        //PUT er idempotent og skal derfor tjekke id, for at den opdatere en der allerede eksisterer
        //int id = updatedParent1.getParent1Id(); tjekkes direkte i kaldet nedenunder
        //da den ellers stopper før if'en hvis id'et ikke er der
        int id = updatedBoat.getBoatId();
        if (boatRepo.findById(id).isPresent()){
            return new ResponseEntity<>(boatRepo.save(updatedBoat), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Båd med id " + id + " blev ikke fundet");
        }
    }
    //DELETE
    public ResponseEntity<Boat> delete(Integer id) {
        if (boatRepo.findById(id).isPresent()){
            Boat boat = boatRepo.findById(id).get();
            boatRepo.deleteById(id);
            return new ResponseEntity<>(boat, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Båd med id " + id + " blev ikke fundet");
        }
    }

    //EKSTRA

    //findByName
    public ResponseEntity<Boat> findByName(String name){
        if (boatRepo.findByName(name).isPresent()){
            return new ResponseEntity<>(boatRepo.findByName(name).get(), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Båd med navn " + name + " blev ikke fundet");
        }
    }
    //COUNT
    public Integer countAll(){
        return boatRepo.countAll();
    }

    public Map<Integer, Boat> boats(){
        System.out.println(boatRepo.boatIdSortedByPointSum());
        System.out.println(boatRepo.sumPointsSorted()); //point er sorteret her!
        List<Integer> sumPointList = boatRepo.sumPointsSorted();
        List<Integer> boatIds = boatRepo.boatIdSortedByPointSum();
        Map<Boat, Integer> boatAndSum = new HashMap<>();

        List<Boat> boats = new ArrayList<>();
        /*for (Integer i : boatIds) {
            System.out.println(i);
            System.out.println("loop start");
            boats.add(boatRepo.findById(boatIds.get(i-1)).get());
            System.out.println("loop efter boats");
            System.out.println(boats.get(i-1));
            boatAndSum.put(boats.get(i), sumPointList.get(i));
        }
        System.out.println(boats);
        System.out.println(boatAndSum);

        for (int i = 0; i < boatIds.size(); i++) {
            System.out.println(i);
            System.out.println("loop start");
            boats.add(boatRepo.findById(boatIds.get(i)).get());
            System.out.println("loop efter boats");
            System.out.println(boats.get(i));
            boatAndSum.put(boats.get(i), sumPointList.get(i));
        }*/
        Map<Integer, Boat> boatAndSum2 = new HashMap<>();
        for (int i = 0; i < boatIds.size(); i++) {
            System.out.println(i);
            System.out.println("loop start");
            boats.add(boatRepo.findById(boatIds.get(i)).get());
            System.out.println("loop efter boats");
            System.out.println(boats.get(i));
            System.out.println(sumPointList);
            boatAndSum2.put(sumPointList.get(i), boats.get(i));
        }
        
        //pointen med det her: vi får to lister, en med bådid og en med summeret point
        //de er synkrone, så index 1 i den ene passer til index 1 i den anden fordi de begge
        //er sorteret baseret på point ascending
        //fordi jpa ikke kan returnere en båd med disse conditions må vi selv lave en liste med
        //både ud fra bådid'erne. Dette lægges så ind i en hashmap så at en key båd passer til
        //value som er sum af point

        return boatAndSum2;
    }

    public List<Integer> sumPoints(){
        System.out.println(boatRepo.sumPointsSorted());
        return boatRepo.sumPointsSorted();
    }
}
