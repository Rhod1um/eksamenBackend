package com.example.eksamentemplate.controller;

import com.example.eksamentemplate.model.Boat;
import com.example.eksamentemplate.service.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}, allowedHeaders = {"Content-Type", "Authorization"})
@RestControllerAdvice
@RequestMapping("/boat")
public class BoatController {
    private final BoatService boatService;

    @Autowired
    public BoatController(BoatService boatService) {
        this.boatService = boatService;
    }

    @GetMapping //med og uden slash til sidst i url er ikke længere det samme i spring boot
    public List<Boat> getAll(){
        return boatService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Boat> getOne(@PathVariable Integer id){
        return boatService.getById(id);
    }
    @PostMapping
    public ResponseEntity<Boat> create(@RequestBody Boat boat){
        return boatService.create(boat);
    }
    @PutMapping
    public ResponseEntity<Boat> update(@RequestBody Boat boat){
        return boatService.update(boat);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boat> delete(@PathVariable Integer id){
        return boatService.delete(id);
    }

    //ekstra
    @GetMapping("/name/{name}")
    public ResponseEntity<Boat> findByName(@PathVariable String name){
        return boatService.findByName(name);
    }

    @GetMapping("/count")
    public Integer countAll(){
        return boatService.countAll();
    }
}
