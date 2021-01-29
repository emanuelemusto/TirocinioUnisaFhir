package it.unisa.fhirconnection.fhirStarter.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class User {
    public static final String MEDIC_ROLE="MEDIC";
    public static final String PATIENT_ROLE="PATIENT";

    public User(String user, String pass, String role){
        this.password= pass;
        this.username = user;
        this.role = role;
    }




    @Id
    private String username;

    @NonNull
    private String password;

    @NotNull
    private String role;

    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    private Person person;


    private String token;
    private Long time;



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public User(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        role = role;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

}