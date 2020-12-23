package it.unisa.fhirconnection.fhirStarter.database;

import it.unisa.fhirconnection.fhirStarter.model.AllergyIntolerance;
import it.unisa.fhirconnection.fhirStarter.model.Identifier;
import it.unisa.fhirconnection.fhirStarter.model.Problem;
import org.hl7.fhir.dstu3.model.Annotation;
import org.hl7.fhir.dstu3.model.Condition;
import org.springframework.stereotype.Component;
import lombok.SneakyThrows;
import org.apache.commons.collections4.Transformer;

@Component
public class ProblemToFHIRProblem implements Transformer<Problem, Condition> {

    private static final Condition condition = new Condition();


    @SneakyThrows
    @Override
    public org.hl7.fhir.dstu3.model.Condition transform(Problem problem){
        final org.hl7.fhir.dstu3.model.Condition condition = new org.hl7.fhir.dstu3.model.Condition();


        condition.setId(String.valueOf(problem.getId()));
        if(problem.getIdentifiers()!=null) {
            for (Identifier identifier : problem.getIdentifiers()) {
                condition.addIdentifier()
                        .setSystem(identifier.getSystem())
                        .setValue(identifier.getValue());
            }
        }

        switch (problem.getClinicalStatus().toUpperCase()){
            case ("ACTIVE"):
                condition.setClinicalStatus(Condition.ConditionClinicalStatus.ACTIVE);
                break;
            case ("INACTIVE"):
                condition.setClinicalStatus(Condition.ConditionClinicalStatus.INACTIVE);
                break;
            case ("NULL"):
                condition.setClinicalStatus(Condition.ConditionClinicalStatus.NULL);
                break;
            case ("RESOLVED"):
                condition.setClinicalStatus(Condition.ConditionClinicalStatus.RESOLVED);
                break;
        }

        switch (problem.getVerificationStatus().toUpperCase()){
            case ("CONFIRMED"):
                condition.setVerificationStatus(Condition.ConditionVerificationStatus.CONFIRMED);
                break;
            case ("DIFFERENTIAL"):
                condition.setVerificationStatus(Condition.ConditionVerificationStatus.DIFFERENTIAL);
                break;
            case ("ENTEREDINERROR"):
                condition.setVerificationStatus(Condition.ConditionVerificationStatus.ENTEREDINERROR);
                break;
            case ("NULL"):
                condition.setVerificationStatus(Condition.ConditionVerificationStatus.NULL);
                break;
            case ("REFUTED"):
                condition.setVerificationStatus(Condition.ConditionVerificationStatus.REFUTED);
                break;
            case ("UNKNOWN"):
                condition.setVerificationStatus(Condition.ConditionVerificationStatus.UNKNOWN);
                break;
        }



        //TODO

        Annotation annotation = new Annotation();
        annotation.setText(problem.getNote());
        annotation.setTime(problem.getRecordedDateAsDate());

        condition.addNote(annotation);



        return condition;
    }
}
