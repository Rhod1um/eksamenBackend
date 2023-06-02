package com.example.eksamentemplate.service;

import com.example.eksamentemplate.exception.ResourceNotFoundException;
import com.example.eksamentemplate.model.Participant;
import com.example.eksamentemplate.model.Boat;
import com.example.eksamentemplate.model.Race;
import com.example.eksamentemplate.repository.ParticipantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class ParticipantService {
    private final ParticipantRepo participantRepo;
    @Autowired
    public ParticipantService(ParticipantRepo participantRepo) {
        this.participantRepo = participantRepo;
    }
    //repo skal ikke annoteres med @Repository, autwire kan altid finde repo
    //GET all
    public List<Participant> getAll() {
        return participantRepo.findAll();
    }
    //GET by id
    public ResponseEntity<Participant> getById(Integer id) {
        //String className = childRepo.findById(id).getClass().getSimpleName();

        if (participantRepo.findById(id).isPresent()){
            return new ResponseEntity<>(participantRepo.findById(id).get(), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Deltager med id " + id + "blev ikke fundet.");
            //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //String className = this.getClass().getSimpleName();
        //className initialization er redundant?? Kan den finde class name ud fra className variabel alene?

        //kan ikke bruge det her orElseThrow med ResponseEntity
        //orElsThrow er på Optional
        //return childRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException(className + " med id " + id));

    }
    //POST
    public ResponseEntity<Participant> create(Participant participant) {
        //check hvor mange deltagere et sejlads har
        Race race = participant.getRace();
        System.out.println(race);
        Integer count = participantRepo.countAllParticipantsByRace(race);
        System.out.println(count);
        if (count < 15) {
            System.out.println("den skal ikke gå videre");
            try {
                participantRepo.save(participant);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); //kan smide sin egen her
            } //man får den præcise fejl i fejlbesked
            return new ResponseEntity<>(participantRepo.save(participant), HttpStatus.CREATED);
        } else throw new ResponseStatusException(HttpStatus.CONFLICT,
                "der er for mange deltagere på dette sejlads");
    }
    //PUT
    public ResponseEntity<Participant> update(Participant updatedParticipant) {
        //PUT er idempotent og skal derfor tjekke id, for at den opdatere en der allerede eksisterer
        int id = updatedParticipant.getParticipantId();
        if (participantRepo.findById(id).isPresent()){
            return new ResponseEntity<>(participantRepo.save(updatedParticipant), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Deltager med id " + id + "blev ikke fundet.");
        }
    }
    //DELETE
    public ResponseEntity<Participant> delete(Integer id) {
        if (participantRepo.findById(id).isPresent()){
            Participant participant = participantRepo.findById(id).get();
            participantRepo.deleteById(id);
            return new ResponseEntity<>(participant, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Deltager med id " + id + "blev ikke fundet.");
        }
    }
    //countAll
    public Integer countAll(){
        System.out.println(participantRepo.countAll());
        return participantRepo.countAll();
    }

    //Ekstra
    /*
    public ResponseEntity<Participant> findByName(String name){
        if (participantRepo.findByName(name).isPresent()){
            return new ResponseEntity<>(participantRepo.findByName(name).get(), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Child med navn " + name + "blev ikke fundet.");
            //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public List<Participant> findByParent1Name(String name){
        return participantRepo.findByParent1Name(name);
    }

    public List<Participant> getAllParticipantsByBoat(Integer boat){
        return participantRepo.getAllParticipantsByBoatbyBoatId(boat);
    }*/
    public Integer getPointsPerBoat(Integer id){
        System.out.println(participantRepo.getTotalPointsByBoatId(id));
        return participantRepo.getTotalPointsByBoatId(id);
    }
}
