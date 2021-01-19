package it.unisa.fhirconnection.fhirStarter.RestController;

import it.unisa.fhirconnection.fhirStarter.service.PatientService;
import it.unisa.fhirconnection.fhirStarter.service.PractitionerService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class PractitionerRestController {
    @RequestMapping(value = "addpractitioner", method = RequestMethod.POST)
    public static void addPractitioner(@RequestBody PractitionerForm practitioner) {
        PractitionerService.addPractitioner(practitioner);
        System.out.println(practitioner.toString());

    }
}
