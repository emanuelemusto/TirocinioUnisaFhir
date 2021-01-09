package it.unisa.fhirconnection.fhirStarter.RestController;

import it.unisa.fhirconnection.fhirStarter.service.ProblemService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class ProblemRestController {
    @RequestMapping(value = "addCondition", method = RequestMethod.POST)
    public static void addSchedule(@RequestBody ProblemForm problemForm) {
        ProblemService.addProblem(problemForm);

    }

}