package it.unisa.fhirconnection.fhirStarter.RestController;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ScheduleForm {
    private String serviceType;
    private String serviceCategory;
    private String patientId;
    private String practitionerId;
    private String planning;
    private String active;

}
