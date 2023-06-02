package com.example.eksamentemplate.service;

import com.example.eksamentemplate.exception.ResourceNotFoundException;
import com.example.eksamentemplate.model.Boat;
import com.example.eksamentemplate.repository.BoatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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
            //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            throw new ResourceNotFoundException("Båd med id " + id + " blev ikke fundet");
        }
    }
    //POST
    public ResponseEntity<Boat> create(Boat boat) {
        //post er non-idempotent

        //post er non-idempotent, så tjek om id findes i forvejen
        //hvis det her var skolesystem, så må der maks være 20 children som en parent peger på
        //student er parent og course er child
        //så der må maks være 20 studerende som et course peger på. Course's student liste må ikke have mere
        //end 20 på sig. Child's parentliste må maks have 20
        //kan bruge en COUNT?
        //her tjek hvor mange parent1 child har i sin liste
        //nej det er parents som jo har lister af child
        //course er parent og student er child
        //så tjek parent liste

            try {
                boatRepo.save(boat);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); //kan smide sin egen her
            } //man får den præcise fejl i fejlbesked
            return new ResponseEntity<>(boatRepo.save(boat), HttpStatus.CREATED); //sker hvis den ikke catcher
    }
    //PUT
    /*public ResponseEntity<Parent1> update(Integer id, @RequestBody Parent1 updatedParent1) {
        //PUT er idempotent og skal derfor tjekke id, for at den opdatere en der allerede eksisterer
        if (parent1Repo.findById(id).isPresent()){
            return new ResponseEntity<>(parent1Repo.save(updatedParent1), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Parent1 med id " + id + " blev ikke fundet");
        }
    }*/
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

    //ekstra
    public ResponseEntity<Boat> findByName(String name){
        if (boatRepo.findByName(name).isPresent()){
            return new ResponseEntity<>(boatRepo.findByName(name).get(), HttpStatus.OK);
        } else {
            //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            throw new ResourceNotFoundException("Båd med navn " + name + " blev ikke fundet");
        }
    }
    //COUNT
    public Integer countAll(){
        return boatRepo.countAll();
    }
}
