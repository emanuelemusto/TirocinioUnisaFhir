package it.unisa.fhirconnection.fhirStarter.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
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

    private String systemd;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiagnosticReport)) return false;
        DiagnosticReport that = (DiagnosticReport) o;
        return id == that.id &&
                experimental == that.experimental &&
                Objects.equals(identifiers, that.identifiers) &&
                Objects.equals(name, that.name) &&
                Objects.equals(status, that.status) &&
                Objects.equals(date, that.date) &&
                Objects.equals(category, that.category) &&
                Objects.equals(code, that.code) &&
                Objects.equals(systemd, that.systemd) &&
                Objects.equals(display, that.display) &&
                Objects.equals(publisher, that.publisher) &&
                Objects.equals(text, that.text) &&
                Objects.equals(media, that.media) &&
                Objects.equals(mediacomment, that.mediacomment) &&
                Objects.equals(contact, that.contact) &&
                Objects.equals(description, that.description) &&
                Objects.equals(patientEntity, that.patientEntity) &&
                Objects.equals(practitionerEntity, that.practitionerEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, identifiers, name, status, date, experimental, category, code, systemd, display, publisher, text, media, mediacomment, contact, description, patientEntity, practitionerEntity);
    }
}
