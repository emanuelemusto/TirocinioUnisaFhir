package it.unisa.fhirconnection.fhirStarter.database;


import it.unisa.fhirconnection.fhirStarter.model.PatientEntity;
import lombok.SneakyThrows;
import org.hl7.fhir.dstu3.model.*;
import org.springframework.stereotype.Component;
import org.apache.commons.collections4.Transformer;
import it.unisa.fhirconnection.fhirStarter.model.Telecom;


@Component
public class ScheduleToFHIRSchedule  implements Transformer<it.unisa.fhirconnection.fhirStarter.model.Schedule, Schedule> {
    @SneakyThrows
    @Override
    public Schedule transform(it.unisa.fhirconnection.fhirStarter.model.Schedule scheduleEntity) {
        final Schedule scheduleFHIR = new Schedule();

        Meta meta = new Meta().addProfile("https://fhir.hl7.org/STU3/StructureDefinition/CareConnect-Schedule-" + scheduleEntity.getId()); //TODO

        scheduleFHIR.setMeta(meta);

        scheduleFHIR.setId(String.valueOf(scheduleEntity.getId()));
        if(scheduleEntity.getIdentifiers()!=null) {
            for (it.unisa.fhirconnection.fhirStarter.model.Identifier identifier : scheduleEntity.getIdentifiers()) {
                scheduleFHIR.addIdentifier()
                        .setSystem(identifier.getSystemid())
                        .setValue(identifier.getValue());
            }
        }
        scheduleFHIR.setActive(scheduleEntity.isActive());


        Period period = new Period();
        period.setStart(scheduleEntity.getDateAsDate());
        scheduleFHIR.setPlanningHorizon(period);

        CodeableConcept codeableConcept = new CodeableConcept();
        codeableConcept.setText(scheduleEntity.getServiceCategory());
        scheduleFHIR.setServiceCategory(codeableConcept);
        scheduleFHIR.setComment(scheduleEntity.getServiceType());

        if(scheduleEntity.getPractitionerEntity()!=null) {
            Reference reference = new Reference();
            reference.setReference(scheduleEntity.getPractitionerEntity().getPerson().getLastName() + scheduleEntity.getPractitionerEntity().getPerson().getFirstName());
            scheduleFHIR.addActor(reference);
        }

        Reference reference1 = new Reference();
        reference1.setReference(scheduleEntity.getPractitionerName() + " / " +  scheduleEntity.getPatientName());
        scheduleFHIR.addActor(reference1);

        return scheduleFHIR;
    }
}