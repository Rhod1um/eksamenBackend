package com.example.eksamentemplate.repository;

import com.example.eksamentemplate.model.Boat;
import com.example.eksamentemplate.model.BoatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoatTypeRepo extends JpaRepository<BoatType, Integer> {
    Optional<BoatType> findByName(String name);

    @Query(value="select COUNT(boattypeid) from boattype", nativeQuery=true)
    Integer countAll();
}
