package it.unisa.fhirconnection.fhirStarter.service;

import it.unisa.fhirconnection.fhirStarter.database.DiagnosticReportDAO;
import it.unisa.fhirconnection.fhirStarter.database.DiagnosticReportToFHIRDiagnosticReport;
import it.unisa.fhirconnection.fhirStarter.database.PatientEntityToFHIRPatient;
import it.unisa.fhirconnection.fhirStarter.model.DiagnosticReport;
import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import org.hl7.fhir.dstu3.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Set;

@Service
public class DiagnosticReportService {
    private static DiagnosticReportDAO diagnosticDAO;

    private  static DiagnosticReportToFHIRDiagnosticReport diagnosticReportToFHIRDiagnosticReport;


    @Autowired
    public DiagnosticReportService(DiagnosticReportDAO diagnosticDAO, DiagnosticReportToFHIRDiagnosticReport diagnosticReportToFHIRDiagnosticReport) {
        DiagnosticReportService.diagnosticDAO = diagnosticDAO;
        DiagnosticReportService.diagnosticReportToFHIRDiagnosticReport = diagnosticReportToFHIRDiagnosticReport;
    }

    public static org.hl7.fhir.dstu3.model.DiagnosticReport trasform (DiagnosticReport diagnosticReport) {
        return diagnosticReportToFHIRDiagnosticReport.transform(diagnosticReport);
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
        PatientService.save(patientEntity.getIdpatient());
    }
}
