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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.annotation.WebServlet;
import java.util.Set;

@Service
public class UserService {

    private static User current_user = null;
    private static UserDAO userDAO;


    @Autowired
    public UserService(UserDAO userDAO){
        this.userDAO =userDAO;
    }

    public static User authenticate(LoginForm userpass) {
        System.out.println("lo username è:" + userpass.getUsername());
        User test = userDAO.findByUsername(userpass.getUsername());
        if (userpass.getPassword().equals(test.getPassword())) {
            current_user = test;
            return current_user;
        }

        return null;


    }

    public static boolean registrate(LoginForm userpass){
        System.out.print("questa è una prova per la registrazione"+userpass.getUsername()+"   "+userpass.getPassword());
        User utente = new User(userpass.getUsername(),userpass.getPassword(),User.PATIENT_ROLE);
        if(userDAO.save(utente) != null)
            return true;

        return false;


    }
}