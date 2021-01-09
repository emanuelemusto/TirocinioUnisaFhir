package it.unisa.fhirconnection.fhirStarter.RestController;

import it.unisa.fhirconnection.fhirStarter.model.DiagnosticReport;
import it.unisa.fhirconnection.fhirStarter.service.DiagnosticReportService;
import it.unisa.fhirconnection.fhirStarter.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class DiagnosticReportRestController {
    @RequestMapping(value = "addDiagnosticReport", method = RequestMethod.POST)
    public static void addDiagnosticReport (@RequestBody DiagnosticReportForm diagnosticReportForm) {
        DiagnosticReportService.addDiagnosticReport(diagnosticReportForm);

    }

}