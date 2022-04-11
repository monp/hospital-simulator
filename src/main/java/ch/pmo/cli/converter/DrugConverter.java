package ch.pmo.cli.converter;

import ch.pmo.model.Drug;
import picocli.CommandLine.ITypeConverter;

public class DrugConverter implements ITypeConverter<Drug> {

    public Drug convert(String value) {
        return new Drug(value);
    }

}