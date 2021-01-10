package it.unisa.fhirconnection.fhirStarter.RestController;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AllergyIntoleranceForm {
    String name;
    String clinicalStatus;
    String verificationStatus;
    String patientId;
    String category;
    String issueddate;
    String lastOccurencedate;
    String practitionerId;
    String patientName;
    String type;
    String note;

}
