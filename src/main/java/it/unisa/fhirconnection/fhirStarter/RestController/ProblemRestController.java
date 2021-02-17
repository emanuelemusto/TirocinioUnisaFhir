package it.unisa.fhirconnection.fhirStarter.RestController;

import it.unisa.fhirconnection.fhirStarter.service.LogService;
import it.unisa.fhirconnection.fhirStarter.service.ProblemService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class ProblemRestController {
    @RequestMapping(value = "addCondition", method = RequestMethod.POST)
    public static void addSchedule(@RequestBody ProblemForm problemForm, HttpServletRequest request) {
        ProblemService.addProblem(problemForm);
        LogService.printLog(request.getRemoteAddr(),request.getRequestURL(),request.getMethod(), null);


    }

}