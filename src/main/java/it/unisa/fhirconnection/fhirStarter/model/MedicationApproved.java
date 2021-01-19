package it.unisa.fhirconnection.fhirStarter.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class MedicationApproved {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    //	Codes that identify this medication
    private String code;

    //	Name that identify this medication
    private String name;

    //Manufacturer of the item
    private String  manufacturer;

    //powder | tablets | capsule +
    private String form;

    //allergy | intolerance - Underlying mechanism (if known)
    private String amount;


}
