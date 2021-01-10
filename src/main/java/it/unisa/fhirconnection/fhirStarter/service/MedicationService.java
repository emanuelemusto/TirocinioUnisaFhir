package it.unisa.fhirconnection.fhirStarter.service;

import it.unisa.fhirconnection.fhirStarter.RestController.MedicationForm;
import it.unisa.fhirconnection.fhirStarter.database.MedicationDAO;
import it.unisa.fhirconnection.fhirStarter.database.MedicationToFhirMedication;
import it.unisa.fhirconnection.fhirStarter.model.AllergyIntolerance;
import it.unisa.fhirconnection.fhirStarter.model.Medication;
import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MedicationService {
    private static MedicationDAO medicationDAO;

    private static MedicationToFhirMedication medicationToFhirMedication;


    @Autowired
    public MedicationService(MedicationDAO medicationDAO, MedicationToFhirMedication medicationToFhirMedication) {

        MedicationService.medicationDAO = medicationDAO;

        MedicationService.medicationToFhirMedication = medicationToFhirMedication;
    }

    public static void addMedication(MedicationForm form) {
        Medication medication = new Medication();
        medication.setName(form.getName());
        medication.setCode(form.getCode());
        medication.setForm(form.getForm());
        medication.setDateStart(form.getDateStart());
        medication.setDateEnd(form.getDateEnd());
        medication.setAmount((form.getAmount()));

        System.out.println(form);

        PatientEntity patientEntity = PatientService.getById(Integer.parseInt(form.getPatientId()));

        Set<Medication> medicationSet= patientEntity.getMedications();
        medicationSet.add(medication);
        patientEntity.setMedications(medicationSet);
        PatientService.save(patientEntity.getIdpatient());
    }

    public static org.hl7.fhir.dstu3.model.Medication transform (Medication medication) {
        return medicationToFhirMedication.transform(medication);
    }
}
