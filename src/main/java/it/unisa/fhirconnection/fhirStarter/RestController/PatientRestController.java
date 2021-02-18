package it.unisa.fhirconnection.fhirStarter.RestController;

import it.unisa.fhirconnection.fhirStarter.service.LogService;
import it.unisa.fhirconnection.fhirStarter.service.PatientService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class PatientRestController {
    @RequestMapping(value = "addpatient", method = RequestMethod.POST)
    public static void addPatient(@RequestBody PatientForm patient, HttpServletRequest request) {
        PatientService.addPatient(patient);
        LogService.printLog(request.getRemoteAddr(),request.getRequestURL(),request.getMethod(), null);



    }
}
