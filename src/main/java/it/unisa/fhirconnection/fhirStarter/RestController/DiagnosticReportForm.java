package it.unisa.fhirconnection.fhirStarter.RestController;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiagnosticReportForm {
    private String name;
    private String status;
    private String patientId;
    /*String practitionerId;*/
    private String date;
    private String category;
    private String code;
    private String system;
    private String display;

}
