package com.example.eksamentemplate.controller;

import com.example.eksamentemplate.model.Boat;
import com.example.eksamentemplate.model.Participant;
import com.example.eksamentemplate.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/participant")
public class ParticipantController {

    private final ParticipantService participantService;

    @Autowired
    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping
    public List<Participant> getAll(){
        return participantService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Participant> getOne(@PathVariable Integer id){
        return participantService.getById(id);
    }
    @PostMapping
    public ResponseEntity<Participant> create(@RequestBody Participant participant){
        return participantService.create(participant);
    }
    @PutMapping
    public ResponseEntity<Participant> update(@RequestBody Participant participant){
        return participantService.update(participant);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Participant> delete(@PathVariable Integer id){
        return participantService.delete(id);
    }
    @GetMapping("/count")
    public Integer countAll(){
        return participantService.countAll();
    }

    @GetMapping("/points")
    public List<Integer> getTotalPointsGroupedByBoatId(){
        return participantService.getTotalPointsGroupedByBoatId();
    }

    //Ekstra
    /*
    @GetMapping("/name/{name}") //skal have /name/ ellers er der ambiguous mapping med /id
    //intellij ved jo ikke om strengen er et navn eller id
    public ResponseEntity<Participant> findByName(@PathVariable String name){
        return participantService.findByName(name);
    }
    @GetMapping("/parent1name/{name}")
    public List<Participant> findByParent1Name(@PathVariable String name){
        return participantService.findByParent1Name(name);
    }
    @GetMapping("/pointbyboat/{id}")
    public List<Participant> getAllParticipantsByBoat(@PathVariable Integer id){
        System.out.println("point:");
        System.out.println(participantService.getAllParticipantsByBoat(id));
        return participantService.getAllParticipantsByBoat(id);
    }*/
}
