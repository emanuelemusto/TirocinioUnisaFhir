package it.unisa.fhirconnection.fhirStarter.service;

import it.unisa.fhirconnection.fhirStarter.RestController.ScheduleForm;
import it.unisa.fhirconnection.fhirStarter.database.*;
import it.unisa.fhirconnection.fhirStarter.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

    public static org.hl7.fhir.dstu3.model.Schedule trasformToFHIRPatient(Schedule schedule) {
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
        schedule.setPatientEntity(PatientService.getById(Integer.parseInt(scheduleForm.getPatientId())));


        switch(scheduleForm.getActive().toLowerCase()) {
            case ("active"):
                schedule.setActive(true);
                break;
            case ("disabled"):
                schedule.setActive(false);
                break;
        }

        System.out.println(schedule.toString());
        scheduleDAO.save(schedule);
    }
}
