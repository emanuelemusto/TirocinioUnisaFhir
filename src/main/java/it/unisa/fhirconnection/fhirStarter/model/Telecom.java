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

    public PractitionerEntity getPractionerEntity() {
        return practitionerEntity;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PractitionerEntity practitionerEntity;

    //home | work | temp | old | mobile - purpose of this contact point
    private ContactPoint.ContactPointUse telecomUse;

    private ContactPoint.ContactPointSystem systemt;

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

    public ContactPoint.ContactPointSystem getSystemt() {
        return systemt;
    }

    public void setSystemt(ContactPoint.ContactPointSystem system) {
        this.systemt = system;
    }

    public PatientEntity getPatientEntity() {
        return patientEntity;
    }

    public void setPatientEntity(PatientEntity patientEntity) {
        this.patientEntity = patientEntity;
    }

    public void setPractionerEntity(PractitionerEntity practitionerEntity) {
        this.practitionerEntity = practitionerEntity;
    }



}