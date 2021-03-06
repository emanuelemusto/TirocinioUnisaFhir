package it.unisa.fhirconnection.fhirStarter.database;

import it.unisa.fhirconnection.fhirStarter.model.Medication;
import lombok.SneakyThrows;
import org.apache.commons.collections4.Transformer;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Narrative;
import org.hl7.fhir.dstu3.model.Reference;
import org.springframework.stereotype.Component;

@Component
public class MedicationToFhirMedication implements Transformer<Medication, org.hl7.fhir.dstu3.model.Medication> {
    @SneakyThrows
    @Override
    public org.hl7.fhir.dstu3.model.Medication transform(Medication medicationEntity){
        final org.hl7.fhir.dstu3.model.Medication medication1 = new org.hl7.fhir.dstu3.model.Medication();
        medication1.setId(String.valueOf(medicationEntity.getId()));

        Reference reference = new Reference();
        reference.setDisplay(medicationEntity.getManufacturer());
        medication1.setManufacturer(reference);
        CodeableConcept codeableConcept = new CodeableConcept();
        codeableConcept.setText(medicationEntity.getForm() + " " + medicationEntity.getAmount());
        medication1.setForm(codeableConcept);
        Narrative narrative = new Narrative();
        narrative.setDivAsString(medicationEntity.getDateStart() + "   *" + medicationEntity.getDateEnd() + "  @" + medicationEntity.getName() + " #" + medicationEntity.getNote());
        medication1.setText(narrative);
        CodeableConcept codeableConcept2 = new CodeableConcept();
        codeableConcept2.setText(medicationEntity.getCode());
        medication1.setCode(codeableConcept2);

        return medication1;
    }

}
