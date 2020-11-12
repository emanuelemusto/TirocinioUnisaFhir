package it.unisa.fhirconnection.fhirStarter.model;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idpatient;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Identifier> identifiers = new HashSet<Identifier>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Telecom> telecoms = new HashSet<Telecom>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Address> addresses = new HashSet<Address>();

    @NonNull
    private String socialSecurity;

    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    private Person person;

    @OneToMany(fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
    private Set<DiagnosticReport> diagnosticReports = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
    private Set<AllergyIntolerance> allergyIntolerances = new HashSet<>();

    public void addIdentifiers(final Identifier identifier) {
        if (!this.identifiers.contains(identifier)) {
            identifiers.add(identifier);
            identifier.setPatientEntity(this);
        }
    }

    public void addTelecoms(final Telecom telecom) {
        if (!this.telecoms.contains(telecom)) {
            telecoms.add(telecom);
            telecom.setPatientEntity(this);

        }
    }

    public void addAddress(final Address address) {
        if (!this.addresses.contains(address)) {
            addresses.add(address);
            address.setPatientEntity(this);
        }
    }

    public void addDiagnosticReports(final DiagnosticReport diagnosticReport) {
        if (!this.diagnosticReports.contains(diagnosticReport)) {
            diagnosticReports.add(diagnosticReport);
            diagnosticReport.setPatientEntity(this);
        }
    }

    public void addAllergyIntolerance(final AllergyIntolerance allergyIntolerance) {
        if (!this.allergyIntolerances.contains(allergyIntolerance)) {
            allergyIntolerances.add(allergyIntolerance);
            allergyIntolerance.setPatientEntity(this);
        }
    }

}