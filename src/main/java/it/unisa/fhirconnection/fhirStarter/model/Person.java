package it.unisa.fhirconnection.fhirStarter.model;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idperson;

    @OneToOne(cascade = CascadeType.ALL)
    private PatientEntity patientEntity;

    @OneToOne(cascade = CascadeType.ALL)
    private PractitionerEntity practitionerEntity;

    private String firstName;

    private String lastName;

    //female, male or null
    private String gender;

    private String dateOfBirth;

    public static int last_insert_id = 0;

    public Person(int idperson, String firstName, String lastName, String gender, String dateOfBirth) {
        this.idperson = idperson;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        if( idperson > this.last_insert_id ){
            this.last_insert_id = idperson;
        }
    }

    public Person(String firstName, String lastName, String gender, String dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public Person(){

    }

    @Override
    public String toString(){
        return "[ID_PER=" + this.idperson + "] " + lastName.toUpperCase() + " " + firstName + " (" + gender + ") - " + dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public Date getDateOfBirthAsDate() throws ParseException {
        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        return fmt.parse(dateOfBirth);
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getIdperson() {
        return idperson;
    }

    public void setIdperson(int idperson) {
        this.idperson = idperson;
    }


    public PatientEntity getPatientEntity() {
        return patientEntity;
    }

    public void setPatientEntity(PatientEntity patientEntity) {
        this.patientEntity = patientEntity;
    }
}