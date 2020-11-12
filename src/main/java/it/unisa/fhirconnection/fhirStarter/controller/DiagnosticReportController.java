package it.unisa.fhirconnection.fhirStarter.controller;

import it.unisa.fhirconnection.fhirStarter.database.DiagnosticReportDAO;
import it.unisa.fhirconnection.fhirStarter.model.DiagnosticReport;
import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Set;

@Service
public class DiagnosticReportController {
    private static DiagnosticReportDAO diagnosticDAO;


    @Autowired
    public DiagnosticReportController(DiagnosticReportDAO diagnosticDAO) {
        DiagnosticReportController.diagnosticDAO = diagnosticDAO;
    }

    public static void addDiagnostic(String name, String status, String date, boolean experimental, String category, String description, String publisher, PatientEntity patientEntity){
        DiagnosticReport diagnosticReport = new DiagnosticReport();
        diagnosticReport.setName(name);
        diagnosticReport.setStatus(status);
        diagnosticReport.setDate(date);
        diagnosticReport.setExperimental(experimental);
        diagnosticReport.setCategory(category);
        diagnosticReport.setPublisher(publisher);
        diagnosticReport.setDescription(description);

        Set<DiagnosticReport> drs= patientEntity.getDiagnosticReports();
        drs.add(diagnosticReport);
        patientEntity.setDiagnosticReports(drs);
        diagnosticReport.setPatientEntity(patientEntity);
        diagnosticDAO.save(diagnosticReport);
        PatientController.save(patientEntity.getIdpatient());
    }
}
