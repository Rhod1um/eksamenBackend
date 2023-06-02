package com.example.eksamentemplate.repository;

import com.example.eksamentemplate.model.Participant;
import com.example.eksamentemplate.model.Boat;
import com.example.eksamentemplate.model.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ParticipantRepo extends JpaRepository<Participant, Integer> {

    //de her mere specifikke metoder lægges i child-klassen fordi her kan man finde den ud
    //fra parent. Omvendt kan man ikke finde parent ud fra child, da parent ikke har reference til child i db

    //enkelt objekt returneres som optional og ellers bare liste
    //Optional<Child> findByNavn(String navn);
    //Optional<Child> findKommuneByNavn(String navn);
    //List<Child> findKommuneByRegionKode(String kode);
    //Optional<Participant> findByName(String name);
    //List<Participant> findByParent1Name(String name);
    Integer countAllParticipantsByRace(Race race);

    //Integer sumPointsByBoat(Integer boatId);


    @Query(value="SELECT SUM(p.point) AS total_points FROM participant p JOIN boat b ON p.boatid = b.boatid WHERE b.boatid = :boatId", nativeQuery=true)
    Integer getTotalPointsByBoatId(@Param("boatId") Integer boatId);

    @Query(value="select COUNT(participantid) from participant", nativeQuery=true)
    Integer countAll();
}
