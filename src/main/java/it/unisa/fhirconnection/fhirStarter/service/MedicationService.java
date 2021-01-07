package it.unisa.fhirconnection.fhirStarter.service;

import it.unisa.fhirconnection.fhirStarter.database.MedicationDAO;
import it.unisa.fhirconnection.fhirStarter.database.MedicationToFhirMedication;
import it.unisa.fhirconnection.fhirStarter.model.Medication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicationService {
    private static MedicationDAO medicationDAO;

    private static MedicationToFhirMedication medicationToFhirMedication;


    @Autowired
    public MedicationService(MedicationDAO medicationDAO, MedicationToFhirMedication medicationToFhirMedication) {

        MedicationService.medicationDAO = medicationDAO;

        MedicationService.medicationToFhirMedication = medicationToFhirMedication;
    }

    public static void addMedication(String code, String manufacturer, String form, int amount, String dateStart, String dateEnd) {
        Medication medication = new Medication();
        medication.setCode(code);
        medication.setManufacturer(manufacturer);
        medication.setForm(form);
        medication.setAmount(amount);
        medication.setDateStart(dateStart);
        medication.setDateEnd(dateEnd);

        medicationDAO.save(medication);

    }

    public static org.hl7.fhir.dstu3.model.Medication transform (Medication medication) {
        return medicationToFhirMedication.transform(medication);
    }
}
