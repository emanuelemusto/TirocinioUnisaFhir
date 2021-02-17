package it.unisa.fhirconnection.fhirStarter.service;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogService {
    public static void printLog(String remoteAddr, StringBuffer urlRequest, String method, String username){

        System.out.println(new Date()+"  "+"Remote Address: "+remoteAddr+" Username: "+username+" "+method+"/"+urlRequest);

    }
}
