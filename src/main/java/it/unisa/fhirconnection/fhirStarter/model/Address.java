package it.unisa.fhirconnection.fhirStarter.model;

import javax.persistence.*;

@Entity
public class Address  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PatientEntity patientEntity;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PractitionerEntity practitionerEntity;

    private String city;

    private String county;

    //home | work | temp | old | mobile - purpose of this contact point
    private org.hl7.fhir.dstu3.model.Address.AddressUse use;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    private String country;

    private String postcode;


    private String lines;

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() { return this.city;}

    public void setCity(String city) { this.city = city; }

    public String getCounty() { return this.county; }
    public void setCounty(String county) { this.county = county; }

    public org.hl7.fhir.dstu3.model.Address.AddressUse getUse() {
        return use;
    }

    public void setUse(org.hl7.fhir.dstu3.model.Address.AddressUse use) {
        this.use = use;
    }

    public String getLines() {
        return lines;
    }

    public void setLines(String lines) {
        this.lines = lines;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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