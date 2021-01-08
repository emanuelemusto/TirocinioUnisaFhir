/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.fhirconnection.fhirStarter.service;


import it.unisa.fhirconnection.fhirStarter.RestController.PatientForm;
import it.unisa.fhirconnection.fhirStarter.database.PatientDAO;
import it.unisa.fhirconnection.fhirStarter.database.PatientEntityToFHIRPatient;
import it.unisa.fhirconnection.fhirStarter.database.PersonDAO;
import it.unisa.fhirconnection.fhirStarter.model.Address;
import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import it.unisa.fhirconnection.fhirStarter.model.Person;
import it.unisa.fhirconnection.fhirStarter.model.Telecom;
import org.hl7.fhir.dstu3.model.ContactPoint;
import org.hl7.fhir.dstu3.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
public class PatientService {
    
    private static PersonDAO personDAO;

    private static PatientDAO patientDAO;

    private  static PatientEntityToFHIRPatient patientEntityToFHIRPatient;

    @Autowired
    public PatientService(PatientDAO patientDAO, PersonDAO personDAO, PatientEntityToFHIRPatient patientEntityToFHIRPatient ) {
        PatientService.patientDAO = patientDAO;
        PatientService.personDAO = personDAO;
        PatientService.patientEntityToFHIRPatient = patientEntityToFHIRPatient;
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

    public static void addPatient(PatientForm patientForm){
        Person person1 = new Person(patientForm.getFirstname(), patientForm.getFamilyname(), patientForm.getGender(), patientForm.getDate());
        PatientEntity patientEntity1 = new PatientEntity();

        Telecom telecom1 = new Telecom();
        telecom1.setValue(patientForm.getTelecomValue());

        switch(patientForm.getTelecomUse().toLowerCase()) {
            case ("home"):
                telecom1.setTelecomUse(ContactPoint.ContactPointUse.HOME);
                break;
            case ("work"):
                telecom1.setTelecomUse(ContactPoint.ContactPointUse.WORK);
                break;
            case ("old"):
                telecom1.setTelecomUse(ContactPoint.ContactPointUse.OLD);
                break;
            case ("mobile"):
                telecom1.setTelecomUse(ContactPoint.ContactPointUse.MOBILE);
                break;
        }



        Address address1 = new Address();
        address1.setCity(patientForm.getCity());
        address1.setPostcode(patientForm.getPostCode());
        address1.setCountry(patientForm.getCountry());
        address1.setLines(patientForm.getAddressLine());

        switch(patientForm.getAddressUse().toLowerCase()) {
            case ("home"):
                address1.setUse(org.hl7.fhir.dstu3.model.Address.AddressUse.HOME);
                break;
            case ("work"):
                address1.setUse(org.hl7.fhir.dstu3.model.Address.AddressUse.WORK);
                break;
            case ("old"):
                address1.setUse(org.hl7.fhir.dstu3.model.Address.AddressUse.OLD);
                break;

        }

        Set<Telecom> telecoms = patientEntity1.getTelecoms();
        telecoms.add(telecom1);
        patientEntity1.setTelecoms(telecoms);

        Set<Address> addresses = patientEntity1.getAddresses();
        addresses.add(address1);
        patientEntity1.setAddresses(addresses);

        person1.setPatientEntity(patientEntity1);
        patientEntity1.setPerson(person1);

        personDAO.save(person1);
        patientDAO.save(patientEntity1);

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

