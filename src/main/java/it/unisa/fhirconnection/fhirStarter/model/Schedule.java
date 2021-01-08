package it.unisa.fhirconnection.fhirStarter.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private PatientEntity patientEntity;

    @OneToOne(cascade = CascadeType.ALL)
    private PractitionerEntity practitionerEntity;

    private String title;

    private String motivation;

    private String date;

    public Date getDateAsDate() throws ParseException {
        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return fmt.parse(date);
    }

}