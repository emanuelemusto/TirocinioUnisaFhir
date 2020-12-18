package it.unisa.fhirconnection.fhirStarter.model;

import lombok.Data;
import lombok.NoArgsConstructor;

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
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Identifier> identifiers = new HashSet<Identifier>();

    private String name;

    //active | inactive | resolved
    private String clinicalStatus;

    //unconfirmed | confirmed | refuted | entered-in-error
    private String verificationStatus;

    //	problem-list-item | encounter-diagnosis
    private String category;


    //  problem | risk
    private String code;

    //  Anatomical location
    private String bodySite;

    //  Date first version of the resource instance was recorded
    private String recordedDate;

    //  Date(/time) of last known occurrence of a reaction
    private String lastOccurrence;

    //  Additional information about the Condition
    private String note;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PatientEntity patientEntity;

    public Date getLastOccurenceAsDate() throws ParseException {
        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        return fmt.parse(lastOccurrence);
    }

    public Date getRecordedDateAsDate() throws ParseException {
        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        return fmt.parse(recordedDate);
    }
}
