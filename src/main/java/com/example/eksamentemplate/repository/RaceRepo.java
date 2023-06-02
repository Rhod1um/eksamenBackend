package com.example.eksamentemplate.repository;

import com.example.eksamentemplate.model.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RaceRepo extends JpaRepository<Race, Integer> {
    @Query(value="select COUNT(raceid) from race", nativeQuery=true)
    Integer countAll();
}
