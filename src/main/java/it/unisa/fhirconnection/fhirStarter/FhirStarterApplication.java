package it.unisa.fhirconnection.fhirStarter;

import it.unisa.fhirconnection.fhirStarter.database.*;
import it.unisa.fhirconnection.fhirStarter.service.PatientService;
import it.unisa.fhirconnection.fhirStarter.model.*;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.ContactPoint;
import org.hl7.fhir.dstu3.model.Practitioner;
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
            UserDAO userDAO,
            PractitionerDAO practitionerDAO
    ) {
        return args -> {
            User utente = new User("mario","rossi",User.PATIENT_ROLE);
            User utente2 = new User("giuseppe","verdi",User.MEDIC_ROLE);

            Person person1 = new Person("firstName", "Cognome", "male", "18/05/2014","RSSMRA80A01H501U");
            PatientEntity patientEntity1 = new PatientEntity();

            Person person2 = new Person("Nome2", "Cognome2", "female", "12/12/2012","RSSMRA80A01H501U");
            PatientEntity patientEntity2 = new PatientEntity();

            Person person3 = new Person("Nome3", "Cognome3", "female", "11/11/2011","RSSMRA80A01H501U");
            PatientEntity patientEntity3 = new PatientEntity();

            Person person4 = new Person("Nome4", "Cognome4", "null", "11/11/2011","RSSMRA80A01H501U");
            PatientEntity patientEntity4 = new PatientEntity();

            Person person5 = new Person("Nome5", "Cognome5", "male", "11/11/2011","RSSMRA80A01H501U");
            PatientEntity patientEntity5 = new PatientEntity();

            Person person6 = new Person("Nome6", "Cognome6", "female", "11/11/2011","RSSMRA80A01H501U");
            PatientEntity patientEntity6 = new PatientEntity();

            Person person7 = new Person("Nome7", "Cognome7", "male", "11/11/2011","RSSMRA80A01H501U");
            PatientEntity patientEntity7 = new PatientEntity();

            person1.setUser(utente);
            utente.setPerson(person1);

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
            diagnosticReport.setMediacomment("Rad of patient 1");
            diagnosticReport.setMedia("src/main/media/rad.jpg");

            DiagnosticReport report = new DiagnosticReport();
            report.setName("Radiology 2 of patient 1");
            report.setStatus("Final");
            report.setCode("12193");
            report.setDisplay("Display");
            report.setSystem("ReD");
            report.setDate("01/01/2020");
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
            medication.setAmount("100 ml");
            medication.setCode("31321");
            medication.setDateStart("01/01/2020");
            medication.setDateEnd("01/02/2020");
            medication.setForm("capsule");
            medication.setManufacturer("Pharma 1");
            medication.setName("Name Medication 1");
            medication.setNote("Note 1");

            Medication medication2 = new Medication();
            medication2.setAmount("65 ml");
            medication2.setCode("31313313121");
            medication2.setDateStart("01/01/2020");
            medication2.setDateEnd("01/02/2020");
            medication2.setForm("capsule");
            medication2.setManufacturer("Pharma");
            medication2.setName("Name Medication 2");
            medication2.setNote("Note 2");

            Medication medication3 = new Medication();
            medication3.setAmount("50 capsule");
            medication3.setCode("31313313121");
            medication3.setDateStart("01/01/2020");
            medication3.setDateEnd("01/02/2020");
            medication3.setForm("capsule");
            medication3.setManufacturer("Pharma");
            medication3.setName("Name Medication 3");
            medication3.setNote("Note 3");

            Medication medication4 = new Medication();
            medication4.setAmount("20 pillow");
            medication4.setCode("31313313121");
            medication4.setDateStart("01/01/2020");
            medication4.setDateEnd("01/02/2020");
            medication4.setForm("capsule");
            medication4.setManufacturer("BigPharma");
            medication4.setName("Name Medication 4");
            medication4.setNote("Note 4");

            Medication medication5 = new Medication();
            medication5.setAmount("100 ml");
            medication5.setCode("31313313121");
            medication5.setDateStart("01/01/2020");
            medication5.setDateEnd("01/02/2020");
            medication5.setForm("capsule");
            medication5.setManufacturer("BigPharma");
            medication5.setName("Name Medication 5");
            medication5.setNote("Note 5");

            Medication medication6 = new Medication();
            medication6.setAmount("2 ml");
            medication6.setCode("31313313121");
            medication6.setDateStart("01/01/2020");
            medication6.setDateEnd("01/02/2020");
            medication6.setForm("Spray");
            medication6.setManufacturer("BigPharma");
            medication6.setName("Name Medication 6");
            medication6.setNote("Note 6");

            Set<Medication> medications = patientEntity1.getMedications();
            medications.add(medication);
            medications.add(medication2);
            medications.add(medication3);
            medications.add(medication4);
            medications.add(medication5);
            medications.add(medication6);
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

            Person person8 = new Person("1", "Doctor", "male", "11/11/1961", "RSSMRA80A01H501U");
            PractitionerEntity practitionerEntity1 = new PractitionerEntity();
            practitionerEntity1.setQualificationComponent("Dentist");
            practitionerEntity1.setIssuer("Issue 1");
            person8.setUser(utente2);
            utente2.setPerson(person8);

            Person person9 = new Person("2", "Doctor", "female", "11/11/1961", "RSSMRA80A01H501U");
            PractitionerEntity practitionerEntity2 = new PractitionerEntity();
            practitionerEntity2.setQualificationComponent("Family doctor");
            practitionerEntity2.setIssuer("Issue 2");

            Schedule schedule = new Schedule();
            schedule.setActive(true);
            schedule.setServiceType("Service 1");
            schedule.setServiceCategory("Category 1");
            schedule.setPlanning("2021-01-29 12:00:00");

            Telecom telecom2 = new Telecom();
            telecom2.setValue("0648352738");
            telecom2.setTelecomUse(ContactPoint.ContactPointUse.WORK);

            Address address2 = new Address();
            address2.setCity("Amsterdam");
            address2.setPostcode("1055RW");
            address2.setCountry("NLD");
            address2.setLines("Bos en Lommerplein 280");
            address2.setUse(org.hl7.fhir.dstu3.model.Address.AddressUse.WORK);

            Set<Telecom> telecoms2 = practitionerEntity1.getTelecoms();
            telecoms2.add(telecom2);
            practitionerEntity1.setTelecoms(telecoms2);

            Set<Address> addresses2 = practitionerEntity1.getAddresses();
            addresses2.add(address2);
            practitionerEntity1.setAddresses(addresses2);

            practitionerEntity1.setPerson(person8);
            person8.setPractitionerEntity(practitionerEntity1);

            practitionerEntity2.setPerson(person9);
            person9.setPractitionerEntity(practitionerEntity2);

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

            Set<Schedule> scheduleSet = patientEntity1.getSchedules();
            scheduleSet.add(schedule);
            patientEntity1.setSchedules(scheduleSet);

            Set<Schedule> scheduleSet2 = practitionerEntity1.getSchedules();
            scheduleSet2.add(schedule);
            practitionerEntity1.setSchedules(scheduleSet2);
            schedule.setPractitionerName(practitionerEntity1.getPerson().getFirstName() + " " + practitionerEntity1.getPerson().getLastName());
            schedule.setPatientName(patientEntity1.getPerson().getFirstName() + " " + patientEntity1.getPerson().getLastName());

            Set<PatientEntity> patientEntitySet = practitionerEntity1.getPatientEntity();
            patientEntitySet.add(patientEntity1);
            patientEntitySet.add(patientEntity2);
            patientEntitySet.add(patientEntity3);
            patientEntitySet.add(patientEntity4);
            patientEntitySet.add(patientEntity5);
            patientEntitySet.add(patientEntity6);
            patientEntitySet.add(patientEntity7);
            practitionerEntity1.setPatientEntity(patientEntitySet);

            personDAO.save(person1);
            patientDAO.save(patientEntity1);
            userDAO.save(utente);
            System.out.println("prova1 "+ utente.getRole());

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

            personDAO.save(person8);
            practitionerDAO.save(practitionerEntity1);
            userDAO.save(utente2);

            personDAO.save(person9);
            practitionerDAO.save(practitionerEntity1);

            drDAO.save(report);
            drDAO.save(diagnosticReport);

            System.out.println(patientEntity1.getIdpatient());
            for (PatientEntity patient : patientDAO.findAll()) {
                System.out.println(patient.getPerson().getLastName());
                for (DiagnosticReport diagnosticReport1 : patient.getDiagnosticReports()) {
                    System.out.println(diagnosticReport1.toString());
                }

            }

            for (PractitionerEntity patient : practitionerDAO.findAll()) {
                System.out.println(patient.getPerson().getLastName());

            }
            System.out.println(patientDAO.findByIdpatient(20).getPerson().getFirstName());

        };

    }
}

