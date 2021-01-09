package it.unisa.fhirconnection.fhirStarter.RestController;

import it.unisa.fhirconnection.fhirStarter.service.PatientService;
import it.unisa.fhirconnection.fhirStarter.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class ScheduleRestController {
    @RequestMapping(value = "addSchedulePractitioner", method = RequestMethod.POST)
    public static void addSchedule(@RequestBody ScheduleForm scheduleForm) {
        ScheduleService.addSchedule(scheduleForm);

    }

}
