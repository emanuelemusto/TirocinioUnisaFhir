package it.unisa.fhirconnection.fhirStarter.service;

import it.unisa.fhirconnection.fhirStarter.database.ProblemDAO;
import it.unisa.fhirconnection.fhirStarter.database.AllergyIntoleranceToFHIRAllergyIntolerance;
import it.unisa.fhirconnection.fhirStarter.database.ProblemToFHIRProblem;
import it.unisa.fhirconnection.fhirStarter.model.AllergyIntolerance;
import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import it.unisa.fhirconnection.fhirStarter.model.Problem;
import org.hl7.fhir.dstu3.model.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProblemService {
    private static ProblemDAO problemDAO;
    public static ProblemToFHIRProblem problemToFHIRProblem;


    @Autowired
    public ProblemService(ProblemDAO problemDAO, ProblemToFHIRProblem problemToFHIRProblem) {
        ProblemService.problemDAO = problemDAO;
        ProblemService.problemToFHIRProblem = problemToFHIRProblem;
    }

    public static Condition transform(Problem problem) {
        return problemToFHIRProblem.transform(problem);
    }

    public static void addProblem(String name, String clinicalStatus, String verificationStatus, String type, String recordedDate, String lastOccurrence, String category, String note, PatientEntity patientEntity){
        Problem problem = new Problem();
        problem.setName(name);
        problem.setClinicalStatus(clinicalStatus);
        problem.setVerificationStatus(verificationStatus);
        problem.setCategory(type);
        problem.setRecordedDate(recordedDate);
        problem.setCategory(category);
        problem.setNote(note);


       /* Set<AllergyIntolerance> als= patientEntity.getAllergyIntolerances();
        als.add(problem);
        patientEntity.setAllergyIntolerances(als);
        problem.setPatientEntity(patientEntity);*/
        problemDAO.save(problem);
        PatientService.save(patientEntity.getIdpatient());
    }


}