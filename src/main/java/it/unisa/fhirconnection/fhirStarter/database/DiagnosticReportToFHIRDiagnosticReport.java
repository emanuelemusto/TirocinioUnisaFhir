package it.unisa.fhirconnection.fhirStarter.database;

import it.unisa.fhirconnection.fhirStarter.model.DiagnosticReport;
import lombok.SneakyThrows;
import org.apache.commons.collections4.Transformer;
import org.hl7.fhir.dstu3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiagnosticReportToFHIRDiagnosticReport  implements Transformer<DiagnosticReport, org.hl7.fhir.dstu3.model.DiagnosticReport> {
    @Autowired
    private static final PatientEntityToFHIRPatient patientEntityToFHIRPatient = new PatientEntityToFHIRPatient();

    @Autowired
    private static final PractitionerEntityToFHIRPractitioner practitionerEntityToFHIRPractitioner = new PractitionerEntityToFHIRPractitioner();


    @SneakyThrows
    @Override
    public org.hl7.fhir.dstu3.model.DiagnosticReport transform(DiagnosticReport diagnosticReport) {
        final org.hl7.fhir.dstu3.model.DiagnosticReport diagnosticReportFhir = new org.hl7.fhir.dstu3.model.DiagnosticReport();

        diagnosticReportFhir.setId(String.valueOf(diagnosticReport.getId()));
        if(diagnosticReport.getIdentifiers()!=null) {
            for (it.unisa.fhirconnection.fhirStarter.model.Identifier identifier : diagnosticReport.getIdentifiers()) {
                diagnosticReportFhir.addIdentifier()
                        .setSystem(identifier.getSystem())
                        .setValue(identifier.getValue());
            }
        }

        switch(diagnosticReport.getStatus().toUpperCase()) {
            case ("AMENDED"):
                diagnosticReportFhir.setStatus(org.hl7.fhir.dstu3.model.DiagnosticReport.DiagnosticReportStatus.AMENDED);
                break;
            case ("APPENDED"):
                diagnosticReportFhir.setStatus(org.hl7.fhir.dstu3.model.DiagnosticReport.DiagnosticReportStatus.APPENDED);
                break;
            case ("CANCELLED"):
                diagnosticReportFhir.setStatus(org.hl7.fhir.dstu3.model.DiagnosticReport.DiagnosticReportStatus.CANCELLED);
                break;
            case ("CORRECTED"):
                diagnosticReportFhir.setStatus(org.hl7.fhir.dstu3.model.DiagnosticReport.DiagnosticReportStatus.CORRECTED);
                break;
            case ("ENTEREDINERROR"):
                diagnosticReportFhir.setStatus(org.hl7.fhir.dstu3.model.DiagnosticReport.DiagnosticReportStatus.ENTEREDINERROR);
                break;
            case ("FINAL"):
                diagnosticReportFhir.setStatus(org.hl7.fhir.dstu3.model.DiagnosticReport.DiagnosticReportStatus.FINAL);
                break;
            case ("NULL"):
                diagnosticReportFhir.setStatus(org.hl7.fhir.dstu3.model.DiagnosticReport.DiagnosticReportStatus.NULL);
                break;
            case ("PARTIAL"):
                diagnosticReportFhir.setStatus(org.hl7.fhir.dstu3.model.DiagnosticReport.DiagnosticReportStatus.PARTIAL);
                break;
            case ("PRELIMINARY"):
                diagnosticReportFhir.setStatus(org.hl7.fhir.dstu3.model.DiagnosticReport.DiagnosticReportStatus.PRELIMINARY);
                break;
            case ("REGISTERED"):
                diagnosticReportFhir.setStatus(org.hl7.fhir.dstu3.model.DiagnosticReport.DiagnosticReportStatus.REGISTERED);
                break;
            case ("UNKNOWN"):
                diagnosticReportFhir.setStatus(org.hl7.fhir.dstu3.model.DiagnosticReport.DiagnosticReportStatus.UNKNOWN);
                break;
        }

        diagnosticReportFhir.setIssued(diagnosticReport.getDateAsDate());

        CodeableConcept codeableConcept = new CodeableConcept();
        codeableConcept.setText(diagnosticReport.getName());
        Coding coding = new Coding();
        coding.setCode(diagnosticReport.getCode());
        coding.setSystem(diagnosticReport.getSystem());
        coding.setDisplay(diagnosticReport.getDisplay());
        codeableConcept.addCoding(coding);
        diagnosticReportFhir.setCategory(codeableConcept);


        if(diagnosticReport.getPractitionerEntity()!=null) {
            diagnosticReportFhir.getSubject().setReference(diagnosticReport.getPatientEntity().getPerson().getLastName() + " " + diagnosticReport.getPatientEntity().getPerson().getFirstName());
        }


       if(diagnosticReport.getPractitionerEntity()!=null) {
           Reference reference = new Reference();
           reference.setReference(diagnosticReport.getPractitionerEntity().getPerson().getLastName() + " " + diagnosticReport.getPractitionerEntity().getPerson().getFirstName());
           org.hl7.fhir.dstu3.model.DiagnosticReport.DiagnosticReportPerformerComponent diagnosticReportPerformerComponent = new org.hl7.fhir.dstu3.model.DiagnosticReport.DiagnosticReportPerformerComponent();
           diagnosticReportPerformerComponent.setActor(reference);

           diagnosticReportFhir.addPerformer(diagnosticReportPerformerComponent);
       }


        if (diagnosticReport.getMedia()!=null) {
            org.hl7.fhir.dstu3.model.DiagnosticReport.DiagnosticReportImageComponent diagnosticReportImageComponent = new org.hl7.fhir.dstu3.model.DiagnosticReport.DiagnosticReportImageComponent();
            diagnosticReportImageComponent.setComment(diagnosticReport.getMediacomment());

            Reference reference = new Reference();
            reference.setReference(diagnosticReport.getMedia());
            diagnosticReportImageComponent.setLink(reference);

            diagnosticReportFhir.addImage(diagnosticReportImageComponent);
        }

        Narrative narrative = new Narrative();
        narrative.setDivAsString(diagnosticReport.getText());
        diagnosticReportFhir.setText(narrative);

        return diagnosticReportFhir;
    }



}
