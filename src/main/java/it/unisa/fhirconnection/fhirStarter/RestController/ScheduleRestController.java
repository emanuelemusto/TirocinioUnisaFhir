package it.unisa.fhirconnection.fhirStarter.RestController;

import it.unisa.fhirconnection.fhirStarter.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin(origins = "*")
public class ScheduleRestController {
    @RequestMapping(value = "addSchedulePractitioner", method = RequestMethod.POST)
    public static void addSchedule(@RequestBody ScheduleForm scheduleForm) {
        ScheduleService.addSchedule(scheduleForm);

    }

    @RequestMapping(value = "confirmSchedule", method = RequestMethod.POST)
    public static void confirm(@RequestBody HashMap<String, String> body) {
        System.out.println(body.get("id"));
        ScheduleService.confirmSchedule(Integer.parseInt(body.get("id")));
    }

    @RequestMapping(value = "rejectSchedule", method = RequestMethod.POST)
    public static void reject(@RequestBody HashMap<String, String> body) {
        System.out.println(body.get("id"));
        ScheduleService.rejectSchedule(Integer.parseInt(body.get("id")));
    }

}
