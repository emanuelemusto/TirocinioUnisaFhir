package it.unisa.fhirconnection.fhirStarter.service;

import it.unisa.fhirconnection.fhirStarter.RestController.ScheduleForm;
import it.unisa.fhirconnection.fhirStarter.database.*;
import it.unisa.fhirconnection.fhirStarter.model.*;
import org.hl7.fhir.dstu3.model.Practitioner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
public class ScheduleService {

    private static ScheduleDAO scheduleDAO;

    private static PatientDAO patientDAO;

    private static ScheduleToFHIRSchedule scheduleToFHIRSchedule;

    @Autowired
    public ScheduleService(ScheduleDAO scheduleDAO, PatientDAO patientDAO, ScheduleToFHIRSchedule scheduleToFHIRSchedule) {
        ScheduleService.scheduleDAO = scheduleDAO;
        ScheduleService.patientDAO = patientDAO;
        ScheduleService.scheduleToFHIRSchedule = scheduleToFHIRSchedule;
    }

    public static ArrayList<Schedule> getAllSchedule() {
        ArrayList<Schedule> scheduleArrayList = new ArrayList<>();
        for (Schedule schedule : scheduleDAO.findAll()) {
            scheduleArrayList.add(schedule);
        }

        return scheduleArrayList;
    }

    public static Schedule getById(int id) {
        return scheduleDAO.findById(id);
    }

    public static org.hl7.fhir.dstu3.model.Schedule transform(Schedule schedule) {
        return scheduleToFHIRSchedule.transform(schedule);
    }

    public static void save(int id) {
        scheduleDAO.save(scheduleDAO.findById(id));
    }

    public static void addSchedule(ScheduleForm scheduleForm) {
        Schedule schedule = new Schedule();
        schedule.setPlanning(scheduleForm.getPlanning());
        schedule.setServiceCategory(scheduleForm.getServiceCategory());
        schedule.setServiceType(scheduleForm.getServiceType());
        schedule.setPatientName(PatientService.getById(Integer.parseInt(scheduleForm.getPatientId())).getPerson().getFirstName() + " " + PatientService.getById(Integer.parseInt(scheduleForm.getPatientId())).getPerson().getLastName());
        schedule.setPatientName(PractitionerService.getById(Integer.parseInt(scheduleForm.getPatientId())).getPerson().getFirstName() + " " + PractitionerService.getById(Integer.parseInt(scheduleForm.getPatientId())).getPerson().getLastName());

        switch(scheduleForm.getActive().toLowerCase()) {
            case ("active"):
                schedule.setActive(true);
                break;
            case ("disabled"):
                schedule.setActive(false);
                break;
        }

        PatientEntity patientEntity = PatientService.getById(Integer.parseInt(scheduleForm.getPatientId()));
        Set<Schedule> patientEntitySchedules= patientEntity.getSchedules();
        patientEntitySchedules.add(schedule);
        patientEntity.setSchedules(patientEntitySchedules);

        PractitionerEntity practitioner = PractitionerService.getById(Integer.parseInt(scheduleForm.getPractitionerId()));
        Set<Schedule> practitionerSchedules= practitioner.getSchedules();
        practitionerSchedules.add(schedule);
        patientEntity.setSchedules(practitionerSchedules);

        PatientService.save(patientEntity.getIdpatient());
        PractitionerService.save(practitioner.getId());
    }

    public static void confirmSchedule(int id) {
        Schedule schedule = scheduleDAO.findById(id);
        schedule.setActive(true);
        scheduleDAO.save(schedule);
    }

    public static void rejectSchedule(int id) {
        Schedule schedule = scheduleDAO.findById(id);
        schedule.setActive(false);
        scheduleDAO.save(schedule);
    }
}
