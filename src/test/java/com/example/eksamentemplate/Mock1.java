package com.example.eksamentemplate;

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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Mock1 { //fjern public? behøves ikke

    @Autowired
    private BoatTypeRepo boatTypeRepo;

    @BeforeEach
    public void setup() {
        BoatTypeService boatTypeService = new BoatTypeService(boatTypeRepo);
        BoatTypeController boatTypeController = new BoatTypeController(boatTypeService);
    }

    @Test
    void testBoatTypeFindByNameMock(){
        //lav mock:
        BoatTypeRepo boatTypeRepo = Mockito.mock(BoatTypeRepo.class);
        //gør klar:
        String name = "25-40fod";
        BoatType boatType = new BoatType();
        Optional<BoatType> optBoatType = Optional.of(boatType);
        boatType.setName(name);
        String expected = boatType.getName();

        //mocker repo adfærd, returnerer optional
        when(boatTypeRepo.findByName("25-40fod")).thenReturn(optBoatType);

        String actual = boatTypeRepo.findByName(name).get().getName();

        Assertions.assertEquals(expected, actual, "are the names equal?");
    }
}
