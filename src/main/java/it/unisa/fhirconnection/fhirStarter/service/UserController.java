/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.fhirconnection.fhirStarter.service;

import it.unisa.fhirconnection.fhirStarter.database.UserDAO;
import it.unisa.fhirconnection.fhirStarter.model.User;


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
    
}
