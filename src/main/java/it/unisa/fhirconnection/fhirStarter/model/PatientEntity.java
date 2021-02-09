package it.unisa.fhirconnection.fhirStarter.model;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idpatient;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Identifier> identifiers = new HashSet<Identifier>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Telecom> telecoms = new HashSet<Telecom>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Address> addresses = new HashSet<Address>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<PractitionerEntity> practitionerEntities = new HashSet<>();

    @NonNull
    private String socialSecurity;

    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    private Person person;

    @OneToMany(fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
    private Set<DiagnosticReport> diagnosticReports = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
    private Set<AllergyIntolerance> allergyIntolerances = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
    private Set<Problem> problems = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
    private Set<Medication> medications = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Schedule> schedules = new HashSet<>();

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

    public void addMedications(final Medication medication) {
        if (!this.medications.contains(medication)) {
            medications.add(medication);
            medication.setPatientEntity(this);
        }
    }

    public void addProblem(final Problem problem) {
        if (!this.problems.contains(problem)) {
            problems.add(problem);
            problem.setPatientEntity(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientEntity)) return false;
        PatientEntity that = (PatientEntity) o;
        return idpatient == that.idpatient &&
                Objects.equals(identifiers, that.identifiers) &&
                Objects.equals(telecoms, that.telecoms) &&
                Objects.equals(addresses, that.addresses) &&
                Objects.equals(practitionerEntities, that.practitionerEntities) &&
                Objects.equals(socialSecurity, that.socialSecurity) &&
                Objects.equals(person, that.person) &&
                Objects.equals(diagnosticReports, that.diagnosticReports) &&
                Objects.equals(allergyIntolerances, that.allergyIntolerances) &&
                Objects.equals(problems, that.problems) &&
                Objects.equals(medications, that.medications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idpatient, identifiers, telecoms, addresses, practitionerEntities, socialSecurity, person, diagnosticReports, allergyIntolerances, problems, medications);
    }
}
