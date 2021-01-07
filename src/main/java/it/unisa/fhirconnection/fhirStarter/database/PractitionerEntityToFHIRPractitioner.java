package it.unisa.fhirconnection.fhirStarter.database;


import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import it.unisa.fhirconnection.fhirStarter.model.PractitionerEntity;
import lombok.SneakyThrows;
import org.hl7.fhir.dstu3.model.*;
import org.springframework.stereotype.Component;
import org.apache.commons.collections4.Transformer;
import it.unisa.fhirconnection.fhirStarter.model.Telecom;


@Component
public class PractitionerEntityToFHIRPractitioner  implements Transformer<PractitionerEntity, Practitioner> {
    @SneakyThrows
    @Override
    public Practitioner transform(PractitionerEntity practitionerEntity) {
        final Practitioner practitioner = new Practitioner();

        Meta meta = new Meta().addProfile("https://fhir.hl7.org/STU3/StructureDefinition/CareConnect-Practitioner"); //TODO

        practitioner.setMeta(meta);

        practitioner.setId(String.valueOf(practitionerEntity.getId()));
        if(practitionerEntity.getIdentifiers()!=null) {
            for (it.unisa.fhirconnection.fhirStarter.model.Identifier identifier : practitionerEntity.getIdentifiers()) {
                practitioner.addIdentifier()
                        .setSystem(identifier.getSystem())
                        .setValue(identifier.getValue());
            }
        }
        practitioner.addName().setFamily(practitionerEntity.getPerson().getLastName()).addGiven(practitionerEntity.getPerson().getFirstName());


        if (practitionerEntity.getPerson().getDateOfBirth() != null) {
            practitioner.setBirthDate(practitionerEntity.getPerson().getDateOfBirthAsDate());
        }
        if (practitionerEntity.getPerson().getGender()!=null) {
            switch(practitionerEntity.getPerson().getGender().toLowerCase()) {
                case ("male"):
                    practitioner.setGender(Enumerations.AdministrativeGender.MALE);
                    break;
                case ("female"):
                    practitioner.setGender(Enumerations.AdministrativeGender.FEMALE);
                    break;
                case ("null"):
                    practitioner.setGender(Enumerations.AdministrativeGender.NULL);
                    break;
                case ("other"):
                    practitioner.setGender(Enumerations.AdministrativeGender.OTHER);
                    break;
            }

        } else practitioner.setGender(Enumerations.AdministrativeGender.UNKNOWN);

        for (Telecom telecom : practitionerEntity.getTelecoms()) {
            ContactPoint contactPoint = new ContactPoint();
            contactPoint.setValue(telecom.getValue());
            if (telecom.getSystem() != null) contactPoint.setSystem(telecom.getSystem());
            if (telecom.getTelecomUse()!=null) {
                contactPoint.setUse(telecom.getTelecomUse());
            }
            practitioner.getTelecom().add(contactPoint);
        }
        for (it.unisa.fhirconnection.fhirStarter.model.Address addressEntity : practitionerEntity.getAddresses()) {
            org.hl7.fhir.dstu3.model.Address address = new org.hl7.fhir.dstu3.model.Address();
            practitioner.getAddress().add(address);


            address.addLine(addressEntity.getLines());
            //TODO
            if (addressEntity.getCity()!=null) address.setCity(addressEntity.getCity());
            if (addressEntity.getCounty()!=null) address.setDistrict(addressEntity.getCounty());
            if (addressEntity.getPostcode()!=null) address.setPostalCode(addressEntity.getPostcode());
            if (addressEntity.getUse()!=null) address.setUse(addressEntity.getUse());
        }

        practitioner.addQualification(practitionerEntity.getQualificationComponent());


        return practitioner;
    }
}