package com.example.eksamentemplate.repository;

import com.example.eksamentemplate.model.Boat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoatRepo extends JpaRepository<Boat, Integer> {
    Optional<Boat> findByName(String name);
    //Integer countChildren();
    @Query(value="select COUNT(boatid) from boat", nativeQuery=true)
    Integer countAll();
}
