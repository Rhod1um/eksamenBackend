package com.example.eksamentemplate.repository;

import com.example.eksamentemplate.model.Boat;
import com.example.eksamentemplate.model.Participant;
import com.example.eksamentemplate.model.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ParticipantRepo extends JpaRepository<Participant, Integer> {

    Integer countAllParticipantsByRace(Race race);

    //Integer sumPointsByBoat(Integer boatId);

    @Query(value="SELECT DISTINCT b.* FROM participant p JOIN boat b ON p.boatid = b.boatid ORDER BY b.boatid ASC;", nativeQuery=true)
    List<Integer> getAllBoatsByParticipantId();
    //kan kun retunrere boatId, ikke hele boat

    @Query(value="SELECT SUM(p.point) FROM participant p JOIN boat b ON p.boatid = b.boatid WHERE b.boatid = :boatId", nativeQuery=true)
    Integer getTotalPointsByBoatId(@Param("boatId") Integer boatId);
    @Query(value="SELECT SUM(p.point) FROM participant p JOIN boat b ON p.boatid = b.boatid GROUP BY b.boatid ORDER BY b.boatid ASC", nativeQuery=true)
    List<Integer> getTotalPointsGroupedByBoatId();
    //behøver ikke ORDER BY SUM(p.point) ASC til sidst da det automatisk er ASC

    @Query(value="select COUNT(participantid) from participant", nativeQuery=true)
    Integer countAll();
}
