package it.unisa.fhirconnection.fhirStarter.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class DiagnosticReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Identifier> identifiers = new HashSet<Identifier>();

    private String name;

    //"registered", "partial", "preliminary", "final"
    private String status;

    private String date;

    private boolean experimental;

    private String category;

    private String code;

    private String system;

    private String display;

    private String publisher;

    private String text;

    private String media;

    private String mediacomment;

    //TODO Add performer

    @OneToOne(cascade = CascadeType.ALL)
    private Telecom contact;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PatientEntity patientEntity;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PractitionerEntity practitionerEntity;

    //TODO image

    public Date getDateAsDate() throws ParseException {
        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        return fmt.parse(date);
    }
}
