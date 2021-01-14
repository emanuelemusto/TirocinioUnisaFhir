package it.unisa.fhirconnection.fhirStarter.RestController;


import it.unisa.fhirconnection.fhirStarter.service.UserService;
import it.unisa.fhirconnection.fhirStarter.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class UserRestController {
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public static ResponseEntity<String> login(@RequestBody LoginForm userpass) {
        // No exception thrown means the authentication succeeded
         String body="";
         User utente = UserService.authenticate(userpass);

        if(utente!=  null){
            //usernma e password corrispondono
            System.out.println("loggato");
            System.out.println("loggato"+utente.getRole());


            return ResponseEntity.ok().body("{\n" +
                    "    \"username\":\""+utente.getUsername()+"\",\n" +
                    "    \"role\":\""+utente.getRole()+"\",\n" +
                    "    \"token\":"+utente.getToken()+"\n" +
                    "}");
        }else{
            System.out.println("non loggato");
        }
        return ResponseEntity.ok().body(null);



    }

    @RequestMapping(value = "registrazione", method = RequestMethod.POST)
    public static ResponseEntity<String> registrazione(@RequestBody RegistrationForm registrationData) {
        // No exception thrown means the authentication succeeded
        boolean result = UserService.registrate(registrationData);

        if(result){
            //usernma e password corrispondono
            System.out.println("Registrato con successo");



            return ResponseEntity.ok().body("utente registrato");
        }else{
            System.out.println("utente gia esistente");
            return ResponseEntity.ok().body("utente gia esistente");

        }




    }
}
