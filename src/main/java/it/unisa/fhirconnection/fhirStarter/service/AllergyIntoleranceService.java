package it.unisa.fhirconnection.fhirStarter.service;

import it.unisa.fhirconnection.fhirStarter.RestController.AllergyIntoleranceForm;
import it.unisa.fhirconnection.fhirStarter.database.AllergyIntoleranceDAO;
import it.unisa.fhirconnection.fhirStarter.database.AllergyIntoleranceToFHIRAllergyIntolerance;
import it.unisa.fhirconnection.fhirStarter.model.AllergyIntolerance;
import it.unisa.fhirconnection.fhirStarter.model.DiagnosticReport;
import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import org.hl7.fhir.dstu3.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AllergyIntoleranceService {
    private static AllergyIntoleranceDAO allergyIntoleranceDAO;
    public static AllergyIntoleranceToFHIRAllergyIntolerance allergyIntoleranceToFHIRAllergyIntolerance;


    @Autowired
    public AllergyIntoleranceService(AllergyIntoleranceDAO allergyIntoleranceDAO, AllergyIntoleranceToFHIRAllergyIntolerance allergyIntoleranceToFHIRAllergyIntolerance) {
        AllergyIntoleranceService.allergyIntoleranceDAO = allergyIntoleranceDAO;
        AllergyIntoleranceService.allergyIntoleranceToFHIRAllergyIntolerance = allergyIntoleranceToFHIRAllergyIntolerance;
    }

    public static org.hl7.fhir.dstu3.model.AllergyIntolerance transform(AllergyIntolerance allergyIntolerance) {
        return allergyIntoleranceToFHIRAllergyIntolerance.transform(allergyIntolerance);
    }

    public static void addAllergy(AllergyIntoleranceForm form){
        AllergyIntolerance allergyIntolerance = new AllergyIntolerance();
        allergyIntolerance.setName(form.getName());
        allergyIntolerance.setClinicalStatus(form.getClinicalStatus());
        allergyIntolerance.setVerificationStatus(form.getVerificationStatus());
        allergyIntolerance.setType(form.getType());
        allergyIntolerance.setRecordedDate(form.getIssueddate());
        allergyIntolerance.setLastOccurrence(form.getLastOccurencedate());
        allergyIntolerance.setCategory(form.getCategory());
        allergyIntolerance.setNote(form.getNote());


        System.out.println(form);

        PatientEntity patientEntity = PatientService.getById(Integer.parseInt(form.getPatientId()));

        Set<AllergyIntolerance> allergyIntoleranceSet= patientEntity.getAllergyIntolerances();
        allergyIntoleranceSet.add(allergyIntolerance);
        patientEntity.setAllergyIntolerances(allergyIntoleranceSet);
        PatientService.save(patientEntity.getIdpatient());
    }


}
