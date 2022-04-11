package ch.pmo.cli;

import ch.pmo.cli.converter.PatientConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PatientConverter")
class PatientConverterTest {

    @Test
    @DisplayName("should convert a patient with correct input")
    void should_convert_healthy_patient() {
        var converter = new PatientConverter();
        var patient = converter.convert("H");
        assertEquals("H", patient.toString());
    }

    @Test
    @DisplayName("throw IllegalArgumentException with invalid imput")
    void should_throw_an_exception_if_invalid() {
        var converter = new PatientConverter();
        assertThrows(NoSuchElementException.class, () -> converter.convert("H2"));
    }

}