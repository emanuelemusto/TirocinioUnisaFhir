package it.unisa.fhirconnection.fhirStarter.service;

import it.unisa.fhirconnection.fhirStarter.RestController.DiagnosticReportForm;
import it.unisa.fhirconnection.fhirStarter.database.DiagnosticReportDAO;
import it.unisa.fhirconnection.fhirStarter.database.DiagnosticReportToFHIRDiagnosticReport;
import it.unisa.fhirconnection.fhirStarter.database.PatientDAO;
import it.unisa.fhirconnection.fhirStarter.exception.StorageException;
import it.unisa.fhirconnection.fhirStarter.model.DiagnosticReport;
import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import java.nio.file.StandardCopyOption;
import java.util.Set;

@Service
public class DiagnosticReportService {
    private static DiagnosticReportDAO diagnosticDAO;

    private static PatientDAO patientDAO;

    private static final String BASE_PATH = "src/main/media/";

    private static DiagnosticReportToFHIRDiagnosticReport diagnosticReportToFHIRDiagnosticReport;

    private static DiagnosticReport drwi;


    @Autowired
    public DiagnosticReportService(DiagnosticReportDAO diagnosticDAO, PatientDAO patientDAO, DiagnosticReportToFHIRDiagnosticReport diagnosticReportToFHIRDiagnosticReport) {
        DiagnosticReportService.diagnosticDAO = diagnosticDAO;
        DiagnosticReportService.patientDAO = patientDAO;
        DiagnosticReportService.diagnosticReportToFHIRDiagnosticReport = diagnosticReportToFHIRDiagnosticReport;
    }

    public static org.hl7.fhir.dstu3.model.DiagnosticReport transform(DiagnosticReport diagnosticReport) {
        return diagnosticReportToFHIRDiagnosticReport.transform(diagnosticReport);
    }

    public static void addDiagnosticReport(DiagnosticReportForm form) {
        DiagnosticReport diagnosticReport = new DiagnosticReport();
        diagnosticReport.setName(form.getName());
        diagnosticReport.setStatus(form.getStatus());
        diagnosticReport.setDate(form.getDate());
        diagnosticReport.setCategory(form.getCategory());
        diagnosticReport.setCode(form.getCode());
        diagnosticReport.setSystemd(form.getSystem());
        diagnosticReport.setDisplay(form.getCategory());

        System.out.println(form);

        PatientEntity patientEntity = PatientService.getById(Integer.parseInt(form.getPatientId()));
        System.out.println(patientEntity);

        Set<DiagnosticReport> drs = patientEntity.getDiagnosticReports();
        drs.add(diagnosticReport);
        patientEntity.setDiagnosticReports(drs);
        PatientService.save(patientEntity.getIdpatient());
        for (DiagnosticReport drsave : patientEntity.getDiagnosticReports()) {
            drwi = drsave;
        }
    }

    public static void uploadFile(MultipartFile file) {

        try {
            Path copyLocation = Paths
                    .get("src/main/media/" + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            drwi.setMedia("src/main/media/" + file.getOriginalFilename());
            drwi.setMediacomment(copyLocation.toString());
            diagnosticDAO.save(drwi);
            System.out.println(drwi);
        } catch (Exception e) {
            e.printStackTrace();
            throw new StorageException("Could not store file " + file.getOriginalFilename()
                    + ". Please try again!");
        }

    }

    public static Resource loadAsResource(final String path) throws Exception {
        try {
            Resource resource = new UrlResource(Paths.get(path).toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new Exception();
            }
        } catch (MalformedURLException e) {
            throw new Exception();
        }
    }
}
