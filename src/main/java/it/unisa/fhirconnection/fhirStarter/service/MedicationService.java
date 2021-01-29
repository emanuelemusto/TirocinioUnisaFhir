package it.unisa.fhirconnection.fhirStarter.service;

import it.unisa.fhirconnection.fhirStarter.RestController.MedicationForm;
import it.unisa.fhirconnection.fhirStarter.database.MedicationDAO;
import it.unisa.fhirconnection.fhirStarter.database.MedicationToFhirMedication;
import it.unisa.fhirconnection.fhirStarter.model.AllergyIntolerance;
import it.unisa.fhirconnection.fhirStarter.model.Medication;
import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
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
        medication.setManufacturer(form.getManufacturer());
        medication.setDateStart(form.getDateStart());
        medication.setDateEnd(form.getDateEnd());
        medication.setAmount(form.getAmount());
        medication.setNote(form.getNote());

        System.out.println(form);

        PatientEntity patientEntity = PatientService.getById(Integer.parseInt(form.getPatientId()));

        Set<Medication> medicationSet = patientEntity.getMedications();
        medicationSet.add(medication);
        patientEntity.setMedications(medicationSet);
        PatientService.save(patientEntity.getIdpatient());
    }

    public static org.hl7.fhir.dstu3.model.Medication transform(Medication medication) {
        return medicationToFhirMedication.transform(medication);
    }

    public static ArrayList<org.hl7.fhir.dstu3.model.Medication> medicationApproved() {
        ArrayList<org.hl7.fhir.dstu3.model.Medication> medicationSet = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(new File("src/main/media/Lista_farmaci_equivalenti.xlsx"));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            ArrayList<Medication> medicationApproveds = new ArrayList<>();
            //I've Header and I'm ignoring header for that I've +1 in loop
            for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
                Medication e = new Medication();
                Row ro = sheet.getRow(i);
                for (int j = ro.getFirstCellNum(); j <= ro.getLastCellNum(); j++) {
                    Cell ce = ro.getCell(j);

                    if (j == 1) {
                        e.setAmount(ce.getStringCellValue());
                    }
                    if (j == 2) {
                        e.setCode(ce.getStringCellValue());
                    }
                    if (j == 4) {
                        e.setName(ce.getStringCellValue());
                    }
                    if (j == 5) {
                        e.setForm(ce.getStringCellValue());
                    }
                    if (j == 6) {
                        e.setManufacturer(ce.getStringCellValue());
                    }
                }
                medicationApproveds.add(e);
            }


            for (Medication emp : medicationApproveds) {
                medicationSet.add(medicationToFhirMedication.transform(emp));
            }
            file.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return medicationSet;
    }

}
