package it.unisa.fhirconnection.fhirStarter.RestController;

import it.unisa.fhirconnection.fhirStarter.service.LogService;
import it.unisa.fhirconnection.fhirStarter.service.PatientService;
import it.unisa.fhirconnection.fhirStarter.service.PractitionerService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class PractitionerRestController {
    @RequestMapping(value = "addpractitioner", method = RequestMethod.POST)
    public static void addPractitioner(@RequestBody PractitionerForm practitioner, HttpServletRequest request) {
        PractitionerService.addPractitioner(practitioner);
        LogService.printLog(request.getRemoteAddr(),request.getRequestURL(),request.getMethod(), null);
    }
}
