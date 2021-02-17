package it.unisa.fhirconnection.fhirStarter.service;


import it.unisa.fhirconnection.fhirStarter.RestController.PatientForm;
import it.unisa.fhirconnection.fhirStarter.database.PatientDAO;
import it.unisa.fhirconnection.fhirStarter.database.PatientEntityToFHIRPatient;
import it.unisa.fhirconnection.fhirStarter.database.PersonDAO;
import it.unisa.fhirconnection.fhirStarter.database.UserDAO;
import it.unisa.fhirconnection.fhirStarter.model.*;
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
    private static UserDAO userDAO;

    private static PatientEntityToFHIRPatient patientEntityToFHIRPatient;

    @Autowired
    public PatientService(PatientDAO patientDAO, PersonDAO personDAO, PatientEntityToFHIRPatient patientEntityToFHIRPatient) {
        PatientService.patientDAO = patientDAO;
        PatientService.personDAO = personDAO;
        PatientService.patientEntityToFHIRPatient = patientEntityToFHIRPatient;
    }

    public static ArrayList<PatientEntity> getAllPatients() {
        ArrayList<PatientEntity> patientEntityList = new ArrayList<>();
        for (PatientEntity patientEntity : patientDAO.findAll()) {
            System.out.println(patientEntity.getIdpatient());
            patientEntityList.add(patientEntity);
        }

        return patientEntityList;
    }

    public static PatientEntity getById(int id) {
        return patientDAO.findByIdpatient(id);
    }

    public static Patient trasformToFHIRPatient(PatientEntity patientEntity) {
        return patientEntityToFHIRPatient.transform(patientEntity);
    }

    public static void save(int id) {
        patientDAO.save(patientDAO.findByIdpatient(id));
    }

    public static void addPatient(PatientForm form) {

        Person person1 = new Person(form.getFirstname(), form.getFamilyname(), form.getGender(), form.getDate(), form.getCf());
        PatientEntity patientEntity1 = new PatientEntity();

        if (form.getUser() != null) {
            User user = userDAO.findByUsername(form.getUser());
            person1.setUser(user);
            user.setPerson(person1);
        }

        Telecom telecom1 = new Telecom();
        telecom1.setValue(form.getTelecomValue());

        switch (form.getTelecomUse().toLowerCase()) {
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
        address1.setCity(form.getCity());
        address1.setPostcode(form.getPostCode());
        address1.setCountry(form.getCountry());
        address1.setLinesAddress(form.getAddressLine());

        switch (form.getAddressUse().toLowerCase()) {
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

}