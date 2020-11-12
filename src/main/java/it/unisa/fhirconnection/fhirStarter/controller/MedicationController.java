package it.unisa.fhirconnection.fhirStarter.controller;

import it.unisa.fhirconnection.fhirStarter.database.MedicationDAO;
import it.unisa.fhirconnection.fhirStarter.model.Medication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicationController {
    private static MedicationDAO medicationDAO;


    @Autowired
    public MedicationController(MedicationDAO medicationDAO) {
        MedicationController.medicationDAO = medicationDAO;
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
}
