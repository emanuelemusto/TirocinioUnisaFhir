package it.unisa.fhirconnection.fhirStarter.RestController;

import it.unisa.fhirconnection.fhirStarter.service.PatientService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class PatientRestController {
    @RequestMapping(value = "addpatient", method = RequestMethod.POST)
    public static void addEmployee(@RequestBody PatientForm patient) {
        PatientService.addPatient(patient);
        System.out.println(patient.toString());

    }
    @RequestMapping(value = "printcose", method = RequestMethod.GET)
    public static void getAllEmployees(){
        System.out.println("ci sono ");
    }
}
