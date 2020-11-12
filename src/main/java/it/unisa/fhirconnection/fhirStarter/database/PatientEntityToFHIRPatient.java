package it.unisa.fhirconnection.fhirStarter.database;


import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import lombok.SneakyThrows;
import org.hl7.fhir.dstu3.model.*;
import org.springframework.stereotype.Component;
import org.apache.commons.collections4.Transformer;
import it.unisa.fhirconnection.fhirStarter.model.Telecom;


@Component
public class PatientEntityToFHIRPatient  implements Transformer<PatientEntity, Patient> {
    @SneakyThrows
    @Override
    public Patient transform(PatientEntity patientEntity) {
        final Patient patient = new Patient();

        Meta meta = new Meta().addProfile("https://fhir.hl7.org/STU3/StructureDefinition/CareConnect-Patient-1"); //TODO

        patient.setMeta(meta);

        patient.setId(String.valueOf(patientEntity.getIdpatient()));
        if(patientEntity.getIdentifiers()!=null) {
            for (it.unisa.fhirconnection.fhirStarter.model.Identifier identifier : patientEntity.getIdentifiers()) {
                patient.addIdentifier()
                        .setSystem(identifier.getSystem())
                        .setValue(identifier.getValue());
            }
        }
        patient.addName().setFamily(patientEntity.getPerson().getLastName()).addGiven(patientEntity.getPerson().getFirstName());


        if (patientEntity.getPerson().getDateOfBirth() != null) {
            patient.setBirthDate(patientEntity.getPerson().getDateOfBirthAsDate());
        }
        if (patientEntity.getPerson().getGender()!=null) {
            switch(patientEntity.getPerson().getGender().toLowerCase()) {
                case ("male"):
                    patient.setGender(Enumerations.AdministrativeGender.MALE);
                    break;
                case ("female"):
                    patient.setGender(Enumerations.AdministrativeGender.FEMALE);
                    break;
                case ("null"):
                    patient.setGender(Enumerations.AdministrativeGender.NULL);
                    break;
                case ("other"):
                    patient.setGender(Enumerations.AdministrativeGender.OTHER);
                    break;
            }

        } else patient.setGender(Enumerations.AdministrativeGender.UNKNOWN);

        for (Telecom telecom : patientEntity.getTelecoms()) {
            ContactPoint contactPoint = new ContactPoint();
            contactPoint.setValue(telecom.getValue());
            if (telecom.getSystem() != null) contactPoint.setSystem(telecom.getSystem());
            if (telecom.getTelecomUse()!=null) {
                contactPoint.setUse(telecom.getTelecomUse());
            }
            patient.getTelecom().add(contactPoint);
        }
        for (it.unisa.fhirconnection.fhirStarter.model.Address addressEntity : patientEntity.getAddresses()) {
            org.hl7.fhir.dstu3.model.Address address = new org.hl7.fhir.dstu3.model.Address();
            patient.getAddress().add(address);


                address.addLine(addressEntity.getLines());
            //TODO
            if (addressEntity.getCity()!=null) address.setCity(addressEntity.getCity());
            if (addressEntity.getCounty()!=null) address.setDistrict(addressEntity.getCounty());
            if (addressEntity.getPostcode()!=null) address.setPostalCode(addressEntity.getPostcode());
            if (addressEntity.getUse()!=null) address.setUse(addressEntity.getUse());
        }


        return patient;
    }
}