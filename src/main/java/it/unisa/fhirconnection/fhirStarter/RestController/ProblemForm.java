package it.unisa.fhirconnection.fhirStarter.RestController;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProblemForm {
    private String name;
    private String clinicalStatus;
    private String patientId;
    private String date;
    private String verificationStatus;
    private String description;
}
