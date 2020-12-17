package it.unisa.fhirconnection.fhirStarter.database;

import it.unisa.fhirconnection.fhirStarter.model.AllergyIntolerance;
import it.unisa.fhirconnection.fhirStarter.model.Identifier;
import org.hl7.fhir.dstu3.model.Annotation;
import org.springframework.stereotype.Component;
import lombok.SneakyThrows;
import org.apache.commons.collections4.Transformer;

@Component
public class AllergyIntoleranceToFHIRAllergyIntolerance implements Transformer<AllergyIntolerance, org.hl7.fhir.dstu3.model.AllergyIntolerance> {

    private static final PatientEntityToFHIRPatient patientEntityToFHIRPatient = new PatientEntityToFHIRPatient();


    @SneakyThrows
    @Override
    public org.hl7.fhir.dstu3.model.AllergyIntolerance transform(AllergyIntolerance allergyIntoleranceEntity){
        final org.hl7.fhir.dstu3.model.AllergyIntolerance allergyIntolerance1 = new org.hl7.fhir.dstu3.model.AllergyIntolerance();


        allergyIntolerance1.setId(String.valueOf(allergyIntoleranceEntity.getId()));
        if(allergyIntoleranceEntity.getIdentifiers()!=null) {
            for (Identifier identifier : allergyIntoleranceEntity.getIdentifiers()) {
                allergyIntolerance1.addIdentifier()
                        .setSystem(identifier.getSystem())
                        .setValue(identifier.getValue());
            }
        }

        switch (allergyIntoleranceEntity.getClinicalStatus().toUpperCase()){
            case ("ACTIVE"):
                allergyIntolerance1.setClinicalStatus(org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceClinicalStatus.ACTIVE);
                break;
            case ("INACTIVE"):
                allergyIntolerance1.setClinicalStatus(org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceClinicalStatus.INACTIVE);
                break;
            case ("NULL"):
                allergyIntolerance1.setClinicalStatus(org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceClinicalStatus.NULL);
                break;
            case ("RESOLVED"):
                allergyIntolerance1.setClinicalStatus(org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceClinicalStatus.RESOLVED);
                break;
        }

        switch (allergyIntoleranceEntity.getVerificationStatus().toUpperCase()){
            case ("CONFIRMED"):
                allergyIntolerance1.setVerificationStatus(org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceVerificationStatus.CONFIRMED);
                break;
            case ("UNCONFIRMED"):
                allergyIntolerance1.setVerificationStatus(org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceVerificationStatus.UNCONFIRMED);
                break;
            case ("ENTEREDINERROR"):
                allergyIntolerance1.setVerificationStatus(org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceVerificationStatus.ENTEREDINERROR);
                break;
            case ("NULL"):
                allergyIntolerance1.setVerificationStatus(org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceVerificationStatus.NULL);
                break;
            case ("REFUTED"):
                allergyIntolerance1.setVerificationStatus(org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceVerificationStatus.REFUTED);
                break;
        }

        switch (allergyIntoleranceEntity.getType().toUpperCase()){
            case ("INTOLERANCE"):
                allergyIntolerance1.setType(org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceType.INTOLERANCE);
                break;
            case ("ALLERGY"):
                allergyIntolerance1.setType(org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceType.ALLERGY);
                break;
            case ("NULL"):
                allergyIntolerance1.setType(org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceType.NULL);
                break;
        }

        switch (allergyIntoleranceEntity.getType().toUpperCase()) {
            case ("BIOLOGIC"):
                allergyIntolerance1.addCategory(org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceCategory.BIOLOGIC);
                break;
            case ("ENVIRONMENT"):
                allergyIntolerance1.addCategory(org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceCategory.ENVIRONMENT);
                break;
            case ("FOOD"):
                allergyIntolerance1.addCategory(org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceCategory.FOOD);
                break;
            case ("NULL"):
                allergyIntolerance1.addCategory(org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceCategory.NULL);
                break;
            case ("MEDICATION"):
                allergyIntolerance1.addCategory(org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceCategory.MEDICATION);
                break;
        }


        allergyIntolerance1.setLastOccurrence(allergyIntoleranceEntity.getLastOccurenceAsDate());

        Annotation annotation = new Annotation();
        annotation.setText(allergyIntoleranceEntity.getNote());
        annotation.setTime(allergyIntoleranceEntity.getRecordedDateAsDate());

        allergyIntolerance1.addNote(annotation);

        /*allergyIntolerance1.setRecorder("dadad");*/ //TODO AGGIUNGERE MEDICO CHE FA COSE
        allergyIntolerance1.setPatientTarget(patientEntityToFHIRPatient.transform(allergyIntoleranceEntity.getPatientEntity()));
        return allergyIntolerance1;
    }
}
