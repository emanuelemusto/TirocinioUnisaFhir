package it.unisa.fhirconnection.fhirStarter.RestController;

import it.unisa.fhirconnection.fhirStarter.service.LogService;
import it.unisa.fhirconnection.fhirStarter.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@CrossOrigin(origins = "*")
public class ScheduleRestController {
    @RequestMapping(value = "addSchedulePractitioner", method = RequestMethod.POST)
    public static void addSchedule(@RequestBody ScheduleForm scheduleForm, HttpServletRequest request) {

        String user=ScheduleService.addSchedule(scheduleForm);
        LogService.printLog(request.getRemoteAddr(),request.getRequestURL(),request.getMethod(), user);


    }

    @RequestMapping(value = "confirmSchedule", method = RequestMethod.POST)
    public static void confirm(@RequestBody HashMap<String, String> body, HttpServletRequest request) {

        ScheduleService.confirmSchedule(Integer.parseInt(body.get("id")));
        LogService.printLog(request.getRemoteAddr(),request.getRequestURL(),request.getMethod(), null);

    }

    @RequestMapping(value = "rejectSchedule", method = RequestMethod.POST)
    public static void reject(@RequestBody HashMap<String, String> body,HttpServletRequest request) {

        ScheduleService.rejectSchedule(Integer.parseInt(body.get("id")));
        LogService.printLog(request.getRemoteAddr(),request.getRequestURL(),request.getMethod(), null);

    }

}
