package it.unisa.fhirconnection.fhirStarter.service;

import it.unisa.fhirconnection.fhirStarter.RestController.ProblemForm;
import it.unisa.fhirconnection.fhirStarter.database.ProblemDAO;
import it.unisa.fhirconnection.fhirStarter.database.AllergyIntoleranceToFHIRAllergyIntolerance;
import it.unisa.fhirconnection.fhirStarter.database.ProblemToFHIRProblem;
import it.unisa.fhirconnection.fhirStarter.model.AllergyIntolerance;
import it.unisa.fhirconnection.fhirStarter.model.DiagnosticReport;
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

    public static void addProblem(ProblemForm form){
        Problem problem = new Problem();
        problem.setName(form.getName());
        problem.setClinicalStatus(form.getClinicalStatus());
        problem.setVerificationStatus(form.getVerificationStatus());
        problem.setRecordedDate(form.getDate());
        problem.setNote(form.getDescription());


        PatientEntity patientEntity = PatientService.getById(Integer.parseInt(form.getPatientId()));
        System.out.println(patientEntity);

        Set<Problem> prs= patientEntity.getProblems();
        prs.add(problem);
        patientEntity.setProblems(prs);
        PatientService.save(patientEntity.getIdpatient());

        PatientService.save(patientEntity.getIdpatient());
    }


}