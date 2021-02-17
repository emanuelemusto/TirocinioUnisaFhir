/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.fhirconnection.fhirStarter.service;


import it.unisa.fhirconnection.fhirStarter.RestController.PractitionerForm;
import it.unisa.fhirconnection.fhirStarter.database.PersonDAO;
import it.unisa.fhirconnection.fhirStarter.database.PractitionerDAO;
import it.unisa.fhirconnection.fhirStarter.database.PractitionerEntityToFHIRPractitioner;
import it.unisa.fhirconnection.fhirStarter.model.*;
import org.hl7.fhir.dstu3.model.ContactPoint;
import org.hl7.fhir.dstu3.model.Practitioner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
public class PractitionerService {


    private static PersonDAO personDAO;

    private static PractitionerDAO practitionerDAO;

    private  static PractitionerEntityToFHIRPractitioner practitionerEntityToFHIRPractitioner;

    @Autowired
    public PractitionerService(PractitionerDAO practitionerDAO, PersonDAO personDAO, PractitionerEntityToFHIRPractitioner practitionerEntityToFHIRPractitioner ) {
        PractitionerService.practitionerDAO = practitionerDAO;
        PractitionerService.personDAO = personDAO;
        PractitionerService.practitionerEntityToFHIRPractitioner = practitionerEntityToFHIRPractitioner;
    }

    public static ArrayList<PractitionerEntity> getAllPractitioners(){
        ArrayList<PractitionerEntity> practitionerEntityList = new ArrayList<>();
        for(PractitionerEntity practitionerEntity : practitionerDAO.findAll()) {
            System.out.println(practitionerEntity.getId());
            practitionerEntityList.add(practitionerEntity);
        }

        return practitionerEntityList;
    }

    public static PractitionerEntity getById(int id){
        return practitionerDAO.findById(id);
    }

    public static Practitioner trasformToFHIRPractitioner (PractitionerEntity practitionerEntity) {
        return practitionerEntityToFHIRPractitioner.transform(practitionerEntity);
    }

    public static void save(int id) {
        practitionerDAO.save(practitionerDAO.findById(id));
    }

    public static void addPractitioner(PractitionerForm dummy){

        User utente = UserService.getByUsername(dummy.getUser());
        Person person1 = new Person(dummy.getFirstname(), dummy.getFamilyname(), dummy.getGender(), dummy.getDate(), dummy.getCf());
        PractitionerEntity practitionerEntity1 = new PractitionerEntity();
        practitionerEntity1.setDescrition(dummy.getDescrition());
        person1.setUser(utente);
        utente.setPerson(person1);

        Telecom telecom1 = new Telecom();
        telecom1.setValue(dummy.getTelecomValue());

        switch(dummy.getTelecomUse().toLowerCase()) {
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
        address1.setCity(dummy.getCity());
        address1.setPostcode(dummy.getPostCode());
        address1.setCountry(dummy.getCountry());
        address1.setLinesAddress(dummy.getAddressLine());

        switch(dummy.getAddressUse().toLowerCase()) {
            case ("home"):
                address1.setAddressUse(org.hl7.fhir.dstu3.model.Address.AddressUse.HOME);
                break;
            case ("work"):
                address1.setAddressUse(org.hl7.fhir.dstu3.model.Address.AddressUse.WORK);
                break;
            case ("old"):
                address1.setAddressUse(org.hl7.fhir.dstu3.model.Address.AddressUse.OLD);
                break;

        }

        Set<Telecom> telecoms = practitionerEntity1.getTelecoms();
        telecoms.add(telecom1);
        practitionerEntity1.setTelecoms(telecoms);

        Set<Address> addresses = practitionerEntity1.getAddresses();
        addresses.add(address1);
        practitionerEntity1.setAddresses(addresses);

        person1.setPractitionerEntity(practitionerEntity1);
        practitionerEntity1.setPerson(person1);

        personDAO.save(person1);
        practitionerDAO.save(practitionerEntity1);

    }

    
  /*  public static void addPractitioner(String socialSecurity, String firstName, String lastName, String gender, String dateOfBirth){
        PractitionerEntity p = new PractitionerEntity(PractitionerEntity.last_insert_id+1, socialSecurity, new Person(Person.last_insert_id+1, firstName, lastName, gender, dateOfBirth));
        practitionerEntityList.add(p);
        practitionerDAO.save(p);
    }

    public static void editPractitioner(PractitionerEntity p, String socialSecurity, String firstName, String lastName, String gender, String dateOfBirth) {
        p.setSocialSecurity(socialSecurity);
        p.getPerson().setFirstName(firstName);
        p.getPerson().setLastName(lastName);
        p.getPerson().setGender(gender);
        p.getPerson().setDateOfBirth(dateOfBirth);
        db.editPractitioner(p);
    }

    public static void deletePractitioner(PractitionerEntity p) {
        practitionerEntityList.remove(p);
        db.deletePractitioner(p);
    }*/
}

