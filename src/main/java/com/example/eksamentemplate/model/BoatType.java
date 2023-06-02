package com.example.eksamentemplate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "boattype") //så den ikke hedder boat_type i db
public class BoatType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boattypeid")
    private int boatTypeId;

    @NonNull
    private String name;

    @OneToMany(mappedBy = "boatType", orphanRemoval = true) //skal være boatType, ikke boattype
    @JsonIgnore
    private Set<Boat> boats = new HashSet<>();
}
