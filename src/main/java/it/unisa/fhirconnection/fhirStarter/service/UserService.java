/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.fhirconnection.fhirStarter.service;


import it.unisa.fhirconnection.fhirStarter.RestController.LoginForm;
import it.unisa.fhirconnection.fhirStarter.RestController.RegistrationForm;
import it.unisa.fhirconnection.fhirStarter.database.UserDAO;
import it.unisa.fhirconnection.fhirStarter.model.*;
import org.hl7.fhir.dstu3.model.ContactPoint;
import org.hl7.fhir.dstu3.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.annotation.WebServlet;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    private static User current_user = null;
    private static UserDAO userDAO;


    @Autowired
    public UserService(UserDAO userDAO) {
        UserService.userDAO = userDAO;
    }

    public static User getByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Scheduled(fixedRate = 900000)
    public void scheduleFixedRateTask() {

        long startTime = System.currentTimeMillis();

        ArrayList<User> utenti = (ArrayList<User>) userDAO.findAll();

        for (User utente : utenti) {
            if (utente.getTime() != null) {
                Long tempoTrascorso = TimeUnit.MILLISECONDS.toHours(startTime - utente.getTime());
                if (tempoTrascorso == 1) {
                    utente.setToken(null);
                    userDAO.save(utente);
                }

            }

        }


    }

    // restituisce il ruolo solo se quell'utente si è loggato
    public static String authorize(String token, String username) {
        if (userDAO.existsUsersByToken(token)) {
            User user = userDAO.findByUsername(username);
            return user.getRole();

        }

        return null;
    }

    public static Boolean authorizeByPatientId(String token, String username, int id) {

        if (userDAO.existsUsersByToken(token)) {

            User user = userDAO.findByUsername(username);
            Person person = user.getPerson();
            Set<PatientEntity> patients;
            String role = user.getRole();
            if (role.equals("MEDIC")) {

                patients = person.getPractitionerEntity().getPatientEntity();

                for (PatientEntity patientEntity : patients) {

                    if (patientEntity.getIdpatient() == id) {

                        return true;
                    }

                }

            } else return role.equals("PATIENT") && (person.getPatientEntity().getIdpatient() == id);
        }
        return false;
    }


    public static User authenticate(LoginForm userpass) {
        User test = userDAO.findByUsername(userpass.getUsername());
        if (test != null) {
            if (userpass.getPassword().equals(test.getPassword())) {

                test.setTime(System.currentTimeMillis());
                test.setToken(userpass.getToken());
                userDAO.save(test);
                current_user = test;

                return current_user;
            }
        }

        return null;


    }

    public static boolean registrate(RegistrationForm userpass) {
        User test = userDAO.findByUsername(userpass.getUsername());
        if (test == null) {
            User utente = new User(userpass.getUsername(), userpass.getPassword(), userpass.getRuolo());
            return userDAO.save(utente) != null;
        }

        return false;


    }

    public static String findbyUsername(String username) {
        User user = userDAO.findByUsername(username);
        return user.getRole();
    }
}