package it.unisa.fhirconnection.fhirStarter.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class DiagnosticReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Identifier> identifiers = new HashSet<Identifier>();

    private String name;

    //"registered", "partial", "preliminary", "final"
    private String status;

    private String date;

    private boolean experimental;

    private String category;

    private String publisher;

    @OneToOne(cascade = CascadeType.ALL)
    private Telecom contact;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PatientEntity patientEntity;

    //TODO image
}
