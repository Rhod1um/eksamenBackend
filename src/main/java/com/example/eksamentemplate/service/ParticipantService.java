package com.example.eksamentemplate.service;

import com.example.eksamentemplate.exception.ObjectAlreadyExistsException;
import com.example.eksamentemplate.exception.ResourceNotFoundException;
import com.example.eksamentemplate.model.Boat;
import com.example.eksamentemplate.model.Participant;
import com.example.eksamentemplate.model.Race;
import com.example.eksamentemplate.repository.ParticipantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParticipantService {
    private final int maxNumber;
    private final ParticipantRepo participantRepo;
    @Autowired
    public ParticipantService(ParticipantRepo participantRepo) {
        this.participantRepo = participantRepo;
        maxNumber = 15;
    }
    //repo skal ikke annoteres med @Repository, autowire kan altid finde repo
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
        int id = participant.getParticipantId();
        if (participantRepo.findById(id).isEmpty()){
            return check(participant);
        } else throw new ObjectAlreadyExistsException("Deltager med id " + id + " findes allerede");
    }
    private ResponseEntity<Participant> check(Participant participant){
        Race race = participant.getRace();
        System.out.println(race);
        Integer count = participantRepo.countAllParticipantsByRace(race); //count
        System.out.println(count);
        if (count < maxNumber) {
            System.out.println("den skal ikke gå videre hvis count over 15");
            try {
                participantRepo.save(participant);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(participantRepo.save(participant), HttpStatus.CREATED);
        } else throw new ResponseStatusException(HttpStatus.CONFLICT,
                "Der er for mange deltagere på dette kapsejlads");
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
    //EKSTRA
    //countAll
    public Integer countAll(){
        System.out.println(participantRepo.countAll());
        return participantRepo.countAll();
    }

    //Ekstra
    /*
    public List<Participant> getAllParticipantsByBoat(Integer boat){
        return participantRepo.getAllParticipantsByBoatbyBoatId(boat);
    }*/
    public List<Integer> getTotalPointsGroupedByBoatId(){
        /*
        List<Boat> boats = participantRepo.getAllBoatsByParticipantId();
        System.out.println(boats);
        Map<Boat, Integer> boatSummedPoint = new HashMap<>();
        //var obj = participantRepo.getTotalPointsByBoatId(boat.getBoatId());
        for (Boat boat : boats) {
            boatSummedPoint.put(boat, participantRepo.getTotalPointsByBoatId(boat.getBoatId()));
        }
        System.out.println(boatSummedPoint);*/

        //List<Integer> boatId = participantRepo.getAllBoatsByParticipantId();
        //System.out.println(boatId);
        //participant repo kan ikke returnere båd objekt, kun id
        //List<Boat> boats = participantRepo.getAllBoatsByParticipantId();

        return participantRepo.getTotalPointsGroupedByBoatId();
    }
}
