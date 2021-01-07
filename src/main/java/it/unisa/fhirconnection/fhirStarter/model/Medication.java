package it.unisa.fhirconnection.fhirStarter.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Identifier> identifiers = new HashSet<Identifier>();

    //	Codes that identify this medication
    private String code;

    //	Name that identify this medication
    private String name;

    //Manufacturer of the item
    private String  manufacturer;

    //powder | tablets | capsule +
    private String form;

    //allergy | intolerance - Underlying mechanism (if known)
    private int amount;

    private String dateStart;

    private String dateEnd;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PatientEntity patientEntity;
}
