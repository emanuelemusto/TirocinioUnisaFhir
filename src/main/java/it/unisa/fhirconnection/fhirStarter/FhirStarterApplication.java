package it.unisa.fhirconnection.fhirStarter;

import it.unisa.fhirconnection.fhirStarter.database.UserDAO;
import it.unisa.fhirconnection.fhirStarter.service.PatientService;
import it.unisa.fhirconnection.fhirStarter.database.DiagnosticReportDAO;
import it.unisa.fhirconnection.fhirStarter.database.PatientDAO;
import it.unisa.fhirconnection.fhirStarter.database.PersonDAO;
import it.unisa.fhirconnection.fhirStarter.model.*;
import org.hl7.fhir.dstu3.model.ContactPoint;
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
            DiagnosticReportDAO drDAO,
            UserDAO userDAO
    ) {
        return args -> {
            //User utente = new User("mario","rossi",User.PATIENT_ROLE);
            User utente = new User("mario","rossi",User.PATIENT_ROLE);


            Person person1 = new Person("firstName", "Cognome", "male", "18/05/2014");
            PatientEntity patientEntity1 = new PatientEntity();

            Person person2 = new Person("Nome2", "Cognome2", "female", "12/12/2012");
            PatientEntity patientEntity2 = new PatientEntity();

            Person person3 = new Person("Nome3", "Cognome3", "female", "11/11/2011");
            PatientEntity patientEntity3 = new PatientEntity();

            Person person4 = new Person("Nome4", "Cognome4", "null", "11/11/2011");
            PatientEntity patientEntity4 = new PatientEntity();

            Person person5 = new Person("Nome5", "Cognome5", "male", "11/11/2011");
            PatientEntity patientEntity5 = new PatientEntity();

            Person person6 = new Person("Nome6", "Cognome6", "female", "11/11/2011");
            PatientEntity patientEntity6 = new PatientEntity();

            Person person7 = new Person("Nome7", "Cognome7", "male", "11/11/2011");
            PatientEntity patientEntity7 = new PatientEntity();

            person1.setUser(utente);
            utente.setPerson(person1);

            personDAO.save(person1);
            patientDAO.save(patientEntity1);
            userDAO.save(utente);
            System.out.println("prova1 "+ utente.getRole());

            DiagnosticReport diagnosticReport = new DiagnosticReport();
            diagnosticReport.setName("Radiology of patient 1");
            diagnosticReport.setStatus("Final");
            diagnosticReport.setCode("12192");
            diagnosticReport.setDisplay("Display");
            diagnosticReport.setSystem("RAD");
            diagnosticReport.setDate("1/11/2020");
            diagnosticReport.setExperimental(false);
            diagnosticReport.setCategory("RAD");
            diagnosticReport.setPublisher("Health Level Seven International (Clinical Genomics)");
            diagnosticReport.setDescription("Ultrasonography of abdomen");

            DiagnosticReport report = new DiagnosticReport();
            report.setName("Radiology 2 of patient 1");
            report.setStatus("Final");
            report.setCode("12193");
            report.setDisplay("Display");
            report.setSystem("ReD");
            report.setDate("1/1/2020");
            report.setExperimental(false);
            report.setCategory("RAD");
            report.setPublisher("Health Level Seven International (Clinical Genomics)");
            report.setDescription("Ultrasonography of abdomen");


            Telecom telecom1 = new Telecom();
            telecom1.setValue("0648352638");
            telecom1.setTelecomUse(ContactPoint.ContactPointUse.MOBILE);

            Address address1 = new Address();
            address1.setCity("Amsterdam");
            address1.setPostcode("1055RW");
            address1.setCountry("NLD");
            address1.setLines("Bos en Lommerplein 280");
            address1.setUse(org.hl7.fhir.dstu3.model.Address.AddressUse.HOME);

            Medication medication = new Medication();
            medication.setAmount(1);
            medication.setCode("31321");
            medication.setDateStart("1/1/2020");
            medication.setDateEnd("1/2/2020");
            medication.setForm("capsule");
            medication.setManufacturer("BigPharma");
            medication.setName("Name Medication 1");


            Set<Medication> medications = patientEntity1.getMedications();
            medications.add(medication);
            patientEntity1.setMedications(medications);


            Problem problem1 = new Problem();
            problem1.setCategory("RAS");
            problem1.setBodySite("Stomach");
            problem1.setClinicalStatus("active");
            problem1.setVerificationStatus("confirmed");
            problem1.setCode("Problem 1");
            problem1.setLastOccurrence("1/2/2020");
            problem1.setRecordedDate("1/1/2020");
            problem1.setNote("Doctor's note");

            Set<Problem> problems = patientEntity1.getProblems();
            problems.add(problem1);
            patientEntity1.setProblems(problems);

            AllergyIntolerance allergyIntolerance = new AllergyIntolerance();
            allergyIntolerance.setCategory("food");
            allergyIntolerance.setVerificationStatus("CONFIRMED");
            allergyIntolerance.setClinicalStatus("active");
            allergyIntolerance.setType("ALLERGY");
            allergyIntolerance.setLastOccurrence("1/1/2020");
            allergyIntolerance.setName("Food Allergy");
            allergyIntolerance.setRecordedDate("1/2/2000");


            Set<AllergyIntolerance> allergyIntolerances = patientEntity1.getAllergyIntolerances();
            allergyIntolerances.add(allergyIntolerance);
            patientEntity1.setAllergyIntolerances(allergyIntolerances);

            person1.setPatientEntity(patientEntity1);
            patientEntity1.setPerson(person1);

            person2.setPatientEntity(patientEntity2);
            patientEntity2.setPerson(person2);

            person3.setPatientEntity(patientEntity3);
            patientEntity3.setPerson(person3);

            person4.setPatientEntity(patientEntity4);
            patientEntity4.setPerson(person4);

            person5.setPatientEntity(patientEntity5);
            patientEntity5.setPerson(person5);

            person6.setPatientEntity(patientEntity6);
            patientEntity6.setPerson(person6);

            person7.setPatientEntity(patientEntity7);
            patientEntity7.setPerson(person7);

            Set<DiagnosticReport> drs= patientEntity1.getDiagnosticReports();
            drs.add(report);
            drs.add(diagnosticReport);
            patientEntity1.setDiagnosticReports(drs); //get e poi set

            Set<Telecom> telecoms = patientEntity1.getTelecoms();
            telecoms.add(telecom1);
            patientEntity1.setTelecoms(telecoms);

            Set<Address> addresses = patientEntity1.getAddresses();
            addresses.add(address1);
            patientEntity1.setAddresses(addresses);

            personDAO.save(person1);
            patientDAO.save(patientEntity1);

            personDAO.save(person2);
            patientDAO.save(patientEntity2);

            personDAO.save(person3);
            patientDAO.save(patientEntity3);

            personDAO.save(person4);
            patientDAO.save(patientEntity4);

            personDAO.save(person5);
            patientDAO.save(patientEntity5);

            personDAO.save(person6);
            patientDAO.save(patientEntity6);

            personDAO.save(person7);
            patientDAO.save(patientEntity7);


            drDAO.save(report);
            drDAO.save(diagnosticReport);

            System.out.println(patientEntity1.getIdpatient());
            for (PatientEntity patient : patientDAO.findAll()) {
                System.out.println(patient.getPerson().getLastName());
                for (DiagnosticReport diagnosticReport1 : patient.getDiagnosticReports()) {
                    System.out.println(diagnosticReport1.toString());
                }

            }
            System.out.println(patientDAO.findByIdpatient(20).getPerson().getFirstName());




        };

    }
}

