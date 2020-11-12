package it.unisa.fhirconnection.fhirStarter.model;

import org.hl7.fhir.dstu3.model.ContactPoint;

import javax.persistence.*;

@Entity
public class Telecom  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String value;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PatientEntity patientEntity;

    private ContactPoint.ContactPointUse telecomUse;


    private ContactPoint.ContactPointSystem system;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ContactPoint.ContactPointUse getTelecomUse() {
        return telecomUse;
    }

    public void setTelecomUse(ContactPoint.ContactPointUse telecomUse) {
        this.telecomUse = telecomUse;
    }

    public ContactPoint.ContactPointSystem getSystem() {
        return system;
    }

    public void setSystem(ContactPoint.ContactPointSystem system) {
        this.system = system;
    }

    public PatientEntity getPatientEntity() {
        return patientEntity;
    }

    public void setPatientEntity(PatientEntity patientEntity) {
        this.patientEntity = patientEntity;
    }


}