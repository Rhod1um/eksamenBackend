package com.example.eksamentemplate;

import java.util.Arrays;

import com.example.eksamentemplate.controller.BoatController;
import com.example.eksamentemplate.controller.BoatTypeController;
import com.example.eksamentemplate.model.Boat;
import com.example.eksamentemplate.model.BoatType;
import com.example.eksamentemplate.repository.BoatRepo;
import com.example.eksamentemplate.repository.BoatTypeRepo;
import com.example.eksamentemplate.service.BoatService;
import com.example.eksamentemplate.service.BoatTypeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

//ingen annotering, behøver man ikke hvis det er simple tests. Så kan man dog heller ikke autowire
//kun annoter hvis man fx skal have hele spring startet.
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class Test1 { //fjern public foran class Test

    private BoatTypeController boatTypeController;
    @Autowired
    private BoatTypeRepo boatTypeRepo;

    @BeforeEach
    public void setup() {
        BoatTypeService boatTypeService = new BoatTypeService(boatTypeRepo);
        boatTypeController = new BoatTypeController(boatTypeService);
    }

    @Test
    public void testParent1FindByName() {
        //Arrange - klargør objekter og whatnot
        String name = "NyBådetype";
        BoatType boatType = new BoatType();
        boatType.setName(name);
        String expected = boatType.getName();
        if (boatTypeRepo.findByName(name).isPresent()){
            int id = boatTypeRepo.findByName(name).get().getBoatTypeId();
            boatTypeController.delete(id);
        }
        boatTypeRepo.save(boatType);

        //Act - udfør den kode der skal testes
        int id = boatTypeRepo.findByName(name).get().getBoatTypeId();
        System.out.println(id);

        String actual = boatTypeRepo.findById(id).get().getName();

        //Assert - se om det er hvad man forventer
        Assertions.assertEquals(actual, expected, "Er id'erne ens");

        boatTypeController.delete(id);
    }
}
