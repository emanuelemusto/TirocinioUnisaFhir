package it.unisa.fhirconnection.fhirStarter.RestController;

import it.unisa.fhirconnection.fhirStarter.service.AllergyIntoleranceService;
import it.unisa.fhirconnection.fhirStarter.service.MedicationService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class MedicationRestController {
    @RequestMapping(value = "addMedication", method = RequestMethod.POST)
    public static void addMedication(@RequestBody MedicationForm medicationForm) {
        MedicationService.addMedication(medicationForm);

    }

}
