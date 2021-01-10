package it.unisa.fhirconnection.fhirStarter.RestController;

import it.unisa.fhirconnection.fhirStarter.service.AllergyIntoleranceService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class AllergyIntoleranceRestController {
    @RequestMapping(value = "addAllergy", method = RequestMethod.POST)
    public static void addAllergy(@RequestBody AllergyIntoleranceForm allergyIntoleranceForm) {
        AllergyIntoleranceService.addAllergy(allergyIntoleranceForm);

    }

}
