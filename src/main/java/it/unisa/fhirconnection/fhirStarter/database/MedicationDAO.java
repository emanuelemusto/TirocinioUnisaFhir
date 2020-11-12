package it.unisa.fhirconnection.fhirStarter.database;

import it.unisa.fhirconnection.fhirStarter.model.DiagnosticReport;
import it.unisa.fhirconnection.fhirStarter.model.Medication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationDAO extends CrudRepository<Medication, Integer> {
}
