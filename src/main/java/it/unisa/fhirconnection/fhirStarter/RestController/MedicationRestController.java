package it.unisa.fhirconnection.fhirStarter.RestController;

import it.unisa.fhirconnection.fhirStarter.service.AllergyIntoleranceService;
import it.unisa.fhirconnection.fhirStarter.service.LogService;
import it.unisa.fhirconnection.fhirStarter.service.MedicationService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class MedicationRestController {
    @RequestMapping(value = "addMedication", method = RequestMethod.POST)
    public static void addMedication(@RequestBody MedicationForm medicationForm, HttpServletRequest request) {
        MedicationService.addMedication(medicationForm);
        LogService.printLog(request.getRemoteAddr(),request.getRequestURL(),request.getMethod(), null);


    }

}
