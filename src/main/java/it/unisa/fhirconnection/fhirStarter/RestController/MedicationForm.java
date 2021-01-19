package it.unisa.fhirconnection.fhirStarter.RestController;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MedicationForm {
    private String name;
    private String code;
    private String form;
    private String manufacturer;
    private String dateStart;
    private String dateEnd;
    private String patientId;
    private String amount;
}
