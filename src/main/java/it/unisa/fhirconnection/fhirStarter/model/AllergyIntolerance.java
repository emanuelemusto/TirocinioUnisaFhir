package it.unisa.fhirconnection.fhirStarter.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class AllergyIntolerance {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Identifier> identifiers = new HashSet<Identifier>();

    private String name;

    //active | inactive | resolved
    private String clinicalStatus;

    //unconfirmed | confirmed | refuted | entered-in-error
    private String verificationStatus;

    //allergy | intolerance - Underlying mechanism (if known)
    private String type;

    //	Date first version of the resource instance was recorded
    private String recordedDate;

    // Date(/time) of last known occurrence of a reaction
    private String lastOccurrence;

    //	food | medication | environment | biologic
    private String category;


    private String note;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PatientEntity patientEntity;
}