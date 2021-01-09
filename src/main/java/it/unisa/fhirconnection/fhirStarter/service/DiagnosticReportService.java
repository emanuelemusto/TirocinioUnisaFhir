package it.unisa.fhirconnection.fhirStarter.service;

import it.unisa.fhirconnection.fhirStarter.RestController.DiagnosticReportForm;
import it.unisa.fhirconnection.fhirStarter.database.DiagnosticReportDAO;
import it.unisa.fhirconnection.fhirStarter.database.DiagnosticReportToFHIRDiagnosticReport;
import it.unisa.fhirconnection.fhirStarter.database.PatientDAO;
import it.unisa.fhirconnection.fhirStarter.database.PatientEntityToFHIRPatient;
import it.unisa.fhirconnection.fhirStarter.model.DiagnosticReport;
import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import org.hl7.fhir.dstu3.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import java.util.Set;

@Service
public class DiagnosticReportService {
    private static DiagnosticReportDAO diagnosticDAO;

    private static PatientDAO patientDAO;

    private static String BASE_PATH = "src/main/media/";

    private  static DiagnosticReportToFHIRDiagnosticReport diagnosticReportToFHIRDiagnosticReport;


    @Autowired
    public DiagnosticReportService(DiagnosticReportDAO diagnosticDAO, PatientDAO patientDAO, DiagnosticReportToFHIRDiagnosticReport diagnosticReportToFHIRDiagnosticReport) {
        DiagnosticReportService.diagnosticDAO = diagnosticDAO;
        DiagnosticReportService.patientDAO = patientDAO;
        DiagnosticReportService.diagnosticReportToFHIRDiagnosticReport = diagnosticReportToFHIRDiagnosticReport;
    }

    public static org.hl7.fhir.dstu3.model.DiagnosticReport transform (DiagnosticReport diagnosticReport) {
        return diagnosticReportToFHIRDiagnosticReport.transform(diagnosticReport);
    }

    public static void addDiagnosticReport(DiagnosticReportForm form){
        DiagnosticReport diagnosticReport = new DiagnosticReport();
        diagnosticReport.setName(form.getName());
        diagnosticReport.setStatus(form.getStatus());
        diagnosticReport.setDate(form.getDate());
        diagnosticReport.setCategory(form.getCategory());
        diagnosticReport.setCode(form.getCode());
        diagnosticReport.setSystem(form.getSystem());
        diagnosticReport.setDisplay(form.getCategory());

        System.out.println(form);

        PatientEntity patientEntity = PatientService.getById(Integer.parseInt(form.getPatientId()));
        System.out.println(patientEntity);

        Set<DiagnosticReport> drs= patientEntity.getDiagnosticReports();
        drs.add(diagnosticReport);
        patientEntity.setDiagnosticReports(drs);
        PatientService.save(patientEntity.getIdpatient());
    }

    private String storeFile(final byte[] bytes, final String fileName) {
        String first = BASE_PATH + System.currentTimeMillis() + "-" + fileName;
        Path path = Paths.get(first);
        try {
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return first;
    }
}
