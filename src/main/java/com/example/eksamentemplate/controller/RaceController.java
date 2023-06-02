package com.example.eksamentemplate.controller;

import com.example.eksamentemplate.model.Race;
import com.example.eksamentemplate.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/race")
public class RaceController {
    private final RaceService raceService;

    @Autowired
    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    @GetMapping
    public List<Race> getAll(){
        return raceService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Race> getOne(@PathVariable Integer id){
        return raceService.getById(id);
    }
    @PostMapping
    public ResponseEntity<Race> create(@RequestBody Race race){
        return raceService.create(race);
    }
    @PutMapping
    public ResponseEntity<Race> update(@RequestBody Race race){
        return raceService.update(race);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Race> delete(@PathVariable Integer id){
        return raceService.delete(id);
    }
    @GetMapping("/count")
    public Integer countAll(){
        return raceService.countAll();
    }
}
