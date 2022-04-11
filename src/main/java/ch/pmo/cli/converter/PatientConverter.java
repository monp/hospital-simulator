package ch.pmo.cli.converter;

import ch.pmo.model.State;
import ch.pmo.model.Patient;
import picocli.CommandLine.ITypeConverter;

public class PatientConverter implements ITypeConverter<Patient> {

    public Patient convert(String code) {
        return new Patient(State.from(code));
    }

}