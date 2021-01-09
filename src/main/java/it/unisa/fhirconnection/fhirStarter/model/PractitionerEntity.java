package it.unisa.fhirconnection.fhirStarter.model;


import lombok.*;
import org.hl7.fhir.dstu3.model.Practitioner;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class PractitionerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Identifier> identifiers = new HashSet<Identifier>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Telecom> telecoms = new HashSet<Telecom>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Address> addresses = new HashSet<Address>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<PractitionerEntity> patientEntity = new HashSet<>();

    @NonNull
    private Practitioner.PractitionerQualificationComponent qualificationComponent;

    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    private Person person;

    @OneToMany(fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
    private Set<DiagnosticReport> diagnosticReports = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
    private Set<AllergyIntolerance> allergyIntolerances = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
    private Set<Problem> problems = new HashSet<>();

    public void addIdentifiers(final Identifier identifier) {
        if (!this.identifiers.contains(identifier)) {
            identifiers.add(identifier);
            identifier.setPractitionerEntity(this);
        }
    }

    public void addTelecoms(final Telecom telecom) {
        if (!this.telecoms.contains(telecom)) {
            telecoms.add(telecom);
            telecom.setPractionerEntity(this);

        }
    }

    public void addAddress(final Address address) {
        if (!this.addresses.contains(address)) {
            addresses.add(address);
            address.setPractionerEntity(this);
        }
    }


}
