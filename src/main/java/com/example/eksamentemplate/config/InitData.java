package com.example.eksamentemplate.config;

import com.example.eksamentemplate.model.*;
import com.example.eksamentemplate.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Component
public class InitData implements CommandLineRunner {
    private final ParticipantService participantService;
    private final BoatService boatService;
    private final RaceService raceService;
    private final BoatTypeService boatTypeService;
    private final MemberService memberService;
    @Autowired
    public InitData(ParticipantService participantService, BoatService boatService,
                    RaceService raceService, BoatTypeService boatTypeService, MemberService memberService) {
        this.participantService = participantService;
        this.boatService = boatService;
        this.raceService = raceService;
        this.boatTypeService = boatTypeService;
        this.memberService = memberService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (false) { //gøres så dette ikke gøres hver gang man køre programmet men nemt kan ændres
            System.out.println("initData kører");
            // 3 bådtyper: Længere end 40fod, mindre end 25fod og 25-40fod.
            BoatType boatType1 = new BoatType();
            boatType1.setName("Længere end 40fod");
            boatTypeService.create(boatType1);

            BoatType boatType2 = new BoatType();
            boatType2.setName("Mindre end 25fod");
            boatTypeService.create(boatType2);

            BoatType boatType3 = new BoatType();
            boatType3.setName("25-40fod");
            boatTypeService.create(boatType3);

            //Både
            Boat boat = new Boat();
            boat.setName("Dingus");
            boat.setBoatType(boatType1);
            boatService.create(boat);

            Boat boat1 = new Boat();
            boat1.setName("Lulu");
            boat1.setBoatType(boatType2);
            boatService.create(boat1);

            Boat boat2 = new Boat();
            boat2.setName("Bat");
            boat2.setBoatType(boatType3);
            boatService.create(boat2);

            Boat boat3 = new Boat();
            boat3.setName("Lange");
            boat3.setBoatType(boatType3);
            boatService.create(boat3);

            Boat boat4 = new Boat();
            boat4.setName("Ariel");
            boat4.setBoatType(boatType2);
            boatService.create(boat4);

            Boat boat5 = new Boat();
            boat5.setName("Sine");
            boat5.setBoatType(boatType1);
            boatService.create(boat5);

            //Kapsejlads
            Race race = new Race();
            race.setDate("2023-09-27");
            raceService.create(race);

            findWednesdays();

            //Deltagere
            Participant participant = new Participant();
            participant.setPoint(1);
            participant.setBoat(boat);
            participant.setRace(race);
            participantService.create(participant);

            Participant participant1 = new Participant();
            participant1.setPoint(2);
            participant1.setBoat(boat1);
            participant1.setRace(race);
            participantService.create(participant1);

            Participant participant2 = new Participant();
            participant2.setPoint(3);
            participant2.setBoat(boat2);
            participant2.setRace(race);
            participantService.create(participant2);

            for (int i = 0; i < 10; i++) {
                Participant participant3 = new Participant();
                participant3.setPoint(i+1);
                participant3.setBoat(boat2);
                participant3.setRace(race);
                participantService.create(participant3);
            }

            //Medlemmer af foreningen
            Member member1 = new Member();
            member1.setName("Anna Korneliussen");
            member1.setEmail("anna@mail.com");
            memberService.create(member1);

            for (int i = 0; i < 50; i++) {
                Member member2 = new Member();
                member2.setName("Louise Dam");
                member2.setEmail("louise@mail.com");
                memberService.create(member2);
            }
        }
    }
    //lav ræs pr onsdag
    public void findWednesdays() {
        LocalDate startDate = LocalDate.of(2023, 5, 1);
        LocalDate endDate = LocalDate.of(2023, 10, 1);

        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            if (date.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
                //Kapsejlads
                Race race = new Race();
                race.setDate(date.toString());
                raceService.create(race);
            }
            date = date.plusDays(1); //går en dag frem
        }
    }
}
