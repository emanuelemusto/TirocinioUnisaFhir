/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.fhirconnection.fhirStarter.controller;


import it.unisa.fhirconnection.fhirStarter.database.PatientDAO;
import it.unisa.fhirconnection.fhirStarter.database.PatientEntityToFHIRPatient;
import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.dstu3.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PatientController {
    


    private static PatientDAO patientDAO;

    private  static PatientEntityToFHIRPatient patientEntityToFHIRPatient;

    @Autowired
    public PatientController(PatientDAO patientDAO, PatientEntityToFHIRPatient patientEntityToFHIRPatient ) {
        PatientController.patientDAO = patientDAO;
        PatientController.patientEntityToFHIRPatient = patientEntityToFHIRPatient;
    }

    public static ArrayList<PatientEntity> getAllPatients(){
        ArrayList<PatientEntity> patientEntityList = new ArrayList<>();
        for(PatientEntity patientEntity : patientDAO.findAll()) {
            System.out.println(patientEntity.getIdpatient());
            patientEntityList.add(patientEntity);
        }

        return patientEntityList;
    }

    public static PatientEntity getById(int id){
        return patientDAO.findByIdpatient(id);
    }

    public static Patient trasformToFHIRPatient (PatientEntity patientEntity) {
        return patientEntityToFHIRPatient.transform(patientEntity);
    }

    public static void save(int id) {
        patientDAO.save(patientDAO.findByIdpatient(id));
    }
    
  /*  public static void addPatient(String socialSecurity, String firstName, String lastName, String gender, String dateOfBirth){
        PatientEntity p = new PatientEntity(PatientEntity.last_insert_id+1, socialSecurity, new Person(Person.last_insert_id+1, firstName, lastName, gender, dateOfBirth));
        patientEntityList.add(p);
        patientDAO.save(p);
    }

    public static void editPatient(PatientEntity p, String socialSecurity, String firstName, String lastName, String gender, String dateOfBirth) {
        p.setSocialSecurity(socialSecurity);
        p.getPerson().setFirstName(firstName);
        p.getPerson().setLastName(lastName);
        p.getPerson().setGender(gender);
        p.getPerson().setDateOfBirth(dateOfBirth);
        db.editPatient(p);
    }

    public static void deletePatient(PatientEntity p) {
        patientEntityList.remove(p);
        db.deletePatient(p);
    }*/
}

