package com.example.eksamentemplate.controller;

import com.example.eksamentemplate.model.BoatType;
import com.example.eksamentemplate.service.BoatTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RestControllerAdvice
@RequestMapping("/boattype")
public class BoatTypeController {
    private final BoatTypeService boatTypeService;

    @Autowired
    public BoatTypeController(BoatTypeService boatTypeService) {
        this.boatTypeService = boatTypeService;
    }

    @GetMapping
    public List<BoatType> getAll(){
        return boatTypeService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<BoatType> getOne(@PathVariable Integer id){
        return boatTypeService.getById(id);
    }
    @PostMapping
    public ResponseEntity<BoatType> create(@RequestBody BoatType boatType){
        return boatTypeService.create(boatType);
    }
    @PutMapping
    public ResponseEntity<BoatType> update(@RequestBody BoatType boatType){
        return boatTypeService.update(boatType);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BoatType> delete(@PathVariable Integer id){
        return boatTypeService.delete(id);
    }
    @GetMapping("/count")
    public Integer countAll(){
        return boatTypeService.countAll();
    }
}
