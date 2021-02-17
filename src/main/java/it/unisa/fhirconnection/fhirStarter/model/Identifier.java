package it.unisa.fhirconnection.fhirStarter.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Identifier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String systemid;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PatientEntity patientEntity;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PractitionerEntity practitionerEntity;

    private String value;

    private Integer ordine;

    org.hl7.fhir.dstu3.model.Identifier.IdentifierUse identifierUse;

}