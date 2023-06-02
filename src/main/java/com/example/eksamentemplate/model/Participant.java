package com.example.eksamentemplate.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participantid")
    private int participantId;

    @NonNull
    private int point;

    @ManyToOne
    @JoinColumn(name = "boatid", referencedColumnName = "boatid")
    private Boat boat;

    @ManyToOne
    @JoinColumn(name = "raceid", referencedColumnName = "raceid")
    private Race race;
}
