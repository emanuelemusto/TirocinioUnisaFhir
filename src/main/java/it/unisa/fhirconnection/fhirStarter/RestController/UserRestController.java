package it.unisa.fhirconnection.fhirStarter.RestController;


import it.unisa.fhirconnection.fhirStarter.service.LogService;
import it.unisa.fhirconnection.fhirStarter.service.UserService;
import it.unisa.fhirconnection.fhirStarter.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@CrossOrigin(origins = "*")
public class UserRestController {
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public static ResponseEntity<String> login(@RequestBody LoginForm userpass, HttpServletRequest request) {

        // No exception thrown means the authentication succeeded
        String body = "";
        User utente = UserService.authenticate(userpass);
        LogService.printLog(request.getRemoteAddr(),request.getRequestURL(),request.getMethod(), utente.getUsername());


        if (utente != null) {
            //username e password corrispondono



            if (utente.getRole() == "MEDIC") {
                return ResponseEntity.ok().body("{\n" +
                        "    \"username\":\"" + utente.getUsername() + "\",\n" +
                        "    \"role\":\"" + utente.getRole() + "\",\n" +
                        "    \"token\":" + utente.getToken() + ",\n" +
                        "    \"id\":" + utente.getPerson().getPractitionerEntity().getId() + "\n" +
                        "}");
            } else {
                return ResponseEntity.ok().body("{\n" +
                        "    \"username\":\"" + utente.getUsername() + "\",\n" +
                        "    \"role\":\"" + utente.getRole() + "\",\n" +
                        "    \"token\":" + utente.getToken() + ",\n" +
                        "    \"id\":" + utente.getPerson().getPatientEntity().getIdpatient() + "\n" +
                        "}");
            }
        } else {
            System.out.println("non loggato");
        }
        return ResponseEntity.status(503).body(null);


    }

    @RequestMapping(value = "registrazione", method = RequestMethod.POST)
    public static ResponseEntity<String> registrazione(@RequestBody RegistrationForm registrationData, HttpServletRequest request) {
        // No exception thrown means the authentication succeeded
        LogService.printLog(request.getRemoteAddr(),request.getRequestURL(),request.getMethod(), null);

        boolean result = UserService.registrate(registrationData);

        if (result) {
            //usernma e password corrispondono
            System.out.println("Registrato con successo");


            return ResponseEntity.ok().body("utente registrato");
        } else {
            System.out.println("utente gia esistente");
            return ResponseEntity.ok().body("utente gia esistente");

        }


    }
}