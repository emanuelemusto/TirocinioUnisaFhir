package it.unisa.fhirconnection.fhirStarter.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Identifier {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String system;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PatientEntity patientEntity;

    private String value;

    private Integer ordine;

    org.hl7.fhir.dstu3.model.Identifier.IdentifierUse identifierUse;

}