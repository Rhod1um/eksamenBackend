package com.example.eksamentemplate.repository;

import com.example.eksamentemplate.model.Boat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoatRepo extends JpaRepository<Boat, Integer> {
    Optional<Boat> findByName(String name);
    @Query(value="select COUNT(boatid) from boat", nativeQuery=true)
    Integer countAll();

    @Query(value="SELECT b.boatid FROM boat b JOIN participant p ON p.boatid = b.boatid GROUP BY b.boatid ORDER BY SUM(p.point) ASC;", nativeQuery=true)
    List<Integer> boatIdSortedByPointSum();

    @Query(value="SELECT SUM(p.point) FROM boat b JOIN participant p ON p.boatid = b.boatid GROUP BY b.boatid ORDER BY SUM(p.point) ASC;", nativeQuery=true)
    List<Integer> sumPointsSorted();

    //se boatid og tilhørende point sorteret:
    //SELECT b.boatid, SUM(p.point) FROM boat b JOIN participant p ON p.boatid = b.boatid GROUP BY b.boatid ORDER BY SUM(p.point) ASC;
}