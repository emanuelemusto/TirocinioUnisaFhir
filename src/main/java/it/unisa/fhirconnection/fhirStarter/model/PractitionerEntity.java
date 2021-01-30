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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<PatientEntity> patientEntity = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Schedule> schedules = new HashSet<Schedule>();


    @NonNull
    private String qualificationComponent;

    @NonNull
    private String issuer;

    private String descrition;

    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    private Person person;

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
