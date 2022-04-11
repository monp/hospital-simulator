package ch.pmo.cli;

import ch.pmo.cli.converter.DrugConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("DrugConverter")
class DrugConverterTest {

    @Test
    @DisplayName("throw IllegalArgumentException with invalid input")
    void should_throw_an_exception_if_empty() {
        var converter = new DrugConverter();
        assertThrows(IllegalArgumentException.class, () -> converter.convert(""));
    }

}