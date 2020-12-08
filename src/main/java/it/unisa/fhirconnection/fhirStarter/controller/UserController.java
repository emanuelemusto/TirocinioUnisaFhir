/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.fhirconnection.fhirStarter.controller;

import com.sun.deploy.nativesandbox.comm.Response;
import it.unisa.fhirconnection.fhirStarter.database.UserDAO;
import it.unisa.fhirconnection.fhirStarter.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.annotation.WebServlet;


public class UserController {
    
    private static User current_user = null;
    private static UserDAO userDAO;
    
    public static boolean authenticate(String username, String password){
        User test = userDAO.findByUsername(username);
        if(password.equals(test.getPassword())) {
            current_user = new User(username);
            return true;
        }
        
        return false;        
    }

    @GetMapping("/auth/{username}/{password}")
    public void login(@PathVariable("username") String username, @PathVariable("password") String password)
            throws Exception {
        // No exception thrown means the authentication succeeded
        System.out.println("eccomi");
    }



    
}
