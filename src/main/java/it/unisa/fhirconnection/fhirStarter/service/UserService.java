/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.fhirconnection.fhirStarter.service;

import com.sun.deploy.nativesandbox.comm.Response;
import it.unisa.fhirconnection.fhirStarter.RestController.LoginForm;
import it.unisa.fhirconnection.fhirStarter.RestController.RegistrationForm;
import it.unisa.fhirconnection.fhirStarter.database.UserDAO;
import it.unisa.fhirconnection.fhirStarter.model.*;
import org.hl7.fhir.dstu3.model.ContactPoint;
import org.hl7.fhir.dstu3.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private static User current_user = null;
    private static UserDAO userDAO;



    @Autowired
    public UserService(UserDAO userDAO){
        this.userDAO =userDAO;
    }

    public static User getByUsername(String username){
        return userDAO.findByUsername(username);
    }

    // restituisce il ruolo solo se quell'utente si è loggato
    public static String authorize(String token, String username){
        if(userDAO.existsUsersByToken(token)){
            User user = userDAO.findByUsername(username);
            return  user.getRole();

        }

        return null;
    }

    public static Boolean authorizeByPatientId(String token, String username,int id){

        if(userDAO.existsUsersByToken(token)){

            User user = userDAO.findByUsername(username);
            Person person = user.getPerson();
            Set<PatientEntity> patients;
            String role=  user.getRole();
            if(role.equals("MEDIC")){

                  patients =person.getPractitionerEntity().getPatientEntity();

                for (PatientEntity patientEntity : patients) {

                    if( patientEntity.getIdpatient()==id){

                        return true;
                    }

                }

            }else return role.equals("PATIENT") && (person.getPatientEntity().getIdpatient() == id);
        }
        return false;
    }


    public static User authenticate(LoginForm userpass) {
        System.out.println("lo username è:" + userpass.getUsername());
        User test = userDAO.findByUsername(userpass.getUsername()) ;
        if(test != null){
            if (userpass.getPassword().equals(test.getPassword())) {
                test.setToken(userpass.getToken());
                userDAO.save(test);
                current_user = test;
                System.out.println("prova token metodo authenticate "+current_user.getToken());

                return current_user;
            }
        }

        return null;


    }

    public static boolean registrate(RegistrationForm userpass){
        System.out.print("questa è una prova per la registrazione"+userpass.getUsername()+"   "+userpass.getPassword());
        User test = userDAO.findByUsername(userpass.getUsername());
        if(test == null) {
            System.out.print("ecco il ruolo "+userpass.getRuolo());
            User utente = new User(userpass.getUsername(), userpass.getPassword(), userpass.getRuolo());
            if (userDAO.save(utente) != null)
                return true;
        }

        return false;


    }
}