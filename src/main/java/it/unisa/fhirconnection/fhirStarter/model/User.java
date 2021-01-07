package it.unisa.fhirconnection.fhirStarter.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

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

    public User(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        role = role;
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

}
