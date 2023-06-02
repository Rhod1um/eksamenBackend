package com.example.eksamentemplate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Race {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "raceid")
    private int raceId;

    @NonNull
    @Column(name = "date")
    private String date; //hvilken dato kan lægges i sql? også tidspunkt

    @OneToMany(mappedBy = "race", orphanRemoval = true)
    @JsonIgnore
    private Set<Participant> participants = new HashSet<>();
}
