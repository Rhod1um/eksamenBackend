package com.example.eksamentemplate.model;

import com.example.eksamentemplate.enums.BoatTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Boat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boatid")
    private int boatId;

    @NonNull
    private String name;

    /*@NonNull
    @Column(name = "boattype")
    private BoatTypeEnum boatTypeEnum;*/

    @OneToMany(mappedBy = "boat", orphanRemoval = true) //orphanRemovel gør at foreign key ikke
    // forhindre at man fjerner parent når der er en child der refererer til den
    @JsonIgnore
    private Set<Participant> participants = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "boattypeid", referencedColumnName = "boattypeid")
    private BoatType boatType;
}
