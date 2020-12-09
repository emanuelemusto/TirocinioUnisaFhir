package it.unisa.fhirconnection.fhirStarter.service;

import it.unisa.fhirconnection.fhirStarter.database.AllergyIntoleranceDAO;
import it.unisa.fhirconnection.fhirStarter.model.AllergyIntolerance;
import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AllergyIntoleranceService {
    private static AllergyIntoleranceDAO allergyIntoleranceDAO;


    @Autowired
    public AllergyIntoleranceService(AllergyIntoleranceDAO allergyIntoleranceDAO) {
        AllergyIntoleranceService.allergyIntoleranceDAO = allergyIntoleranceDAO;
    }

    public static void addAllergy(String name, String clinicalStatus, String verificationStatus, String type, String recordedDate, String lastOccurrence, String category, String note, PatientEntity patientEntity){
        AllergyIntolerance allergyIntolerance = new AllergyIntolerance();
        allergyIntolerance.setName(name);
        allergyIntolerance.setClinicalStatus(clinicalStatus);
        allergyIntolerance.setVerificationStatus(verificationStatus);
        allergyIntolerance.setType(type);
        allergyIntolerance.setRecordedDate(recordedDate);
        allergyIntolerance.setCategory(category);
        allergyIntolerance.setNote(note);


        Set<AllergyIntolerance> als= patientEntity.getAllergyIntolerances();
        als.add(allergyIntolerance);
        patientEntity.setAllergyIntolerances(als);
        allergyIntolerance.setPatientEntity(patientEntity);
        allergyIntoleranceDAO.save(allergyIntolerance);
        PatientService.save(patientEntity.getIdpatient());
    }


}
