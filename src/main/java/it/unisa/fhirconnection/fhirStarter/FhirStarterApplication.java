package it.unisa.fhirconnection.fhirStarter;

import it.unisa.fhirconnection.fhirStarter.controller.PatientController;
import it.unisa.fhirconnection.fhirStarter.database.DiagnosticReportDAO;
import it.unisa.fhirconnection.fhirStarter.database.PatientDAO;
import it.unisa.fhirconnection.fhirStarter.database.PersonDAO;
import it.unisa.fhirconnection.fhirStarter.model.DiagnosticReport;
import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import it.unisa.fhirconnection.fhirStarter.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class FhirStarterApplication {

    @Autowired
    ApplicationContext context;


    public static void main(String[] args) {
        System.setProperty("server.port", "8183");
        SpringApplication.run(FhirStarterApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean ServletRegistrationBean() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new fhirStarterRestfulServer(context), "/STU3/*");
        registration.setName("FhirServlet");
        return registration;
    }

    @Bean
    CorsConfigurationSource
    corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public CommandLineRunner demo(
            PatientDAO patientDAO,
            PersonDAO personDAO,
            DiagnosticReportDAO drDAO
    ) {
        return args -> {

            Person person1 = new Person("firstName", "lastName", "gender", "18/05/2014");
            PatientEntity patientEntity1 = new PatientEntity();

            DiagnosticReport diagnosticReport = new DiagnosticReport();
            diagnosticReport.setName("Radiology of patient 1");
            diagnosticReport.setStatus("final");
            diagnosticReport.setDate("1/11/2020");
            diagnosticReport.setExperimental(false);
            diagnosticReport.setCategory("RAD");
            diagnosticReport.setPublisher("Health Level Seven International (Clinical Genomics)");
            diagnosticReport.setDescription("Ultrasonography of abdomen");


            person1.setPatientEntity(patientEntity1);
            patientEntity1.setPerson(person1);
            Set<DiagnosticReport> drs= patientEntity1.getDiagnosticReports();
            drs.add(diagnosticReport);
            patientEntity1.setDiagnosticReports(drs); //get e poi set



            personDAO.save(person1);
            patientDAO.save(patientEntity1);
            drDAO.save(diagnosticReport);
            System.out.println(patientEntity1.getIdpatient());
            for (PatientEntity patient : patientDAO.findAll()) {
                System.out.println(patient.getPerson().getLastName());
                for (DiagnosticReport diagnosticReport1 : patient.getDiagnosticReports()) {
                    System.out.println(diagnosticReport1.toString());
                }

            }
            System.out.println(patientDAO.findByIdpatient(20).getPerson().getFirstName());
            for (PatientEntity patient : PatientController.getAllPatients()) {
                System.out.println(patient.getPerson().getLastName());
            }



        };

    }
}

