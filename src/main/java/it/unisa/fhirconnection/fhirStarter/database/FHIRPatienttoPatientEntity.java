package it.unisa.fhirconnection.fhirStarter.database;

import lombok.SneakyThrows;
import org.apache.commons.collections4.Transformer;
import org.hl7.fhir.dstu3.model.*;
import org.springframework.stereotype.Component;
import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import it.unisa.fhirconnection.fhirStarter.model.Person;
import it.unisa.fhirconnection.fhirStarter.model.Telecom;

@Component
public class FHIRPatienttoPatientEntity implements Transformer<Patient, PatientEntity> {
    @SneakyThrows
    @Override
    public PatientEntity transform(Patient patient) {
        PatientEntity patientEntity = new PatientEntity();
        Person person = new Person();

        patientEntity.setIdpatient(Integer.parseInt(patient.getId()));

        for (org.hl7.fhir.dstu3.model.Identifier identifier : patient.getIdentifier()) {
            it.unisa.fhirconnection.fhirStarter.model.Identifier identifierE = new it.unisa.fhirconnection.fhirStarter.model.Identifier();
            identifierE.setSystem(identifier.getSystem());
            identifierE.setValue(identifier.getValue().replaceAll(" ", ""));

            patientEntity.getIdentifiers().add(identifierE);
        }
        for (HumanName name : patient.getName()) {

            person.setLastName(name.getFamily());
            if (name.hasPrefix()) {
                person.setFirstName(name.getGivenAsSingleString() + " " + name.getPrefix().get(0).getValue());
            }
            else
                person.setFirstName(name.getGivenAsSingleString());
        }
        if (patient.hasBirthDate()) {
            person.setDateOfBirth(patient.getBirthDate().toString());
        }
        if (patient.hasGender()) {
            person.setGender(patient.getGender().toString().toLowerCase());
        }
        for (ContactPoint contactPoint : patient.getTelecom()) {
            Telecom telecom = new Telecom();
            telecom.setValue(contactPoint.getValue());
            if (contactPoint.hasSystem()) {
                telecom.setSystem(contactPoint.getSystem());
            }
            if (contactPoint.hasUse()) telecom.setTelecomUse(contactPoint.getUse());

            patientEntity.getTelecoms().add(telecom);
        }
        for (Address address : patient.getAddress()) {
            it.unisa.fhirconnection.fhirStarter.model.Address addressEntity = new it.unisa.fhirconnection.fhirStarter.model.Address();
                addressEntity.setLines(address.getLine().get(0).toString());


            if (address.hasCity()) {
                addressEntity.setCity(address.getCity());
            }
            if (address.hasPostalCode()) {
                addressEntity.setPostcode(address.getPostalCode());
            }
            if (address.hasDistrict()) {
                addressEntity.setCounty(address.getDistrict());
            }
            if (address.hasUse()) {
                addressEntity.setUse(address.getUse());
            }
            patientEntity.getAddresses().add(addressEntity);
        }
        patientEntity.setPerson(person);
        return patientEntity;
    }
}
