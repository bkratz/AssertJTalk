package de.birgitkratz.internal;

import de.birgitkratz.MappingException;
import de.birgitkratz.PersonExternalModelToPersonInternalModelMapper;
import de.birgitkratz.TestData;
import de.birgitkratz.external.PersonExternalModel;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.*;

class AssertJExceptionTest {
    private final PersonExternalModel personExternalModel = TestData.personExternalModel();

    @Test
    void testExceptionUsingAssertThat() {
        personExternalModel.setDateOfBirth("300-02-1968");
        try {
            new PersonExternalModelToPersonInternalModelMapper().personMapper().apply(personExternalModel);
            fail("Exception should have been thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(MappingException.class)
                    .hasMessageContaining("while mapping")
                    .hasCauseInstanceOf(DateTimeParseException.class);
        }
    }

    @Test
    void testExceptionUsingAssertExceptionOfType() {
        personExternalModel.setDateOfBirth("300-02-1968");
        assertThatExceptionOfType(MappingException.class)
                .isThrownBy(() -> new PersonExternalModelToPersonInternalModelMapper().personMapper().apply(personExternalModel))
                .withMessageStartingWith("Something went wrong")
                .withRootCauseInstanceOf(DateTimeParseException.class);
    }

    @Test
    void testExceptionUsingAssertThatThrownBy() {
        personExternalModel.setDateOfBirth(null);
        assertThatThrownBy(() -> new PersonExternalModelToPersonInternalModelMapper().personMapper().apply(personExternalModel))
                .isInstanceOf(MappingException.class)
                .hasMessageContaining("Something went wrong")
                .hasCauseInstanceOf(NullPointerException.class);
    }

    @Test
    void testNoExceptionIsThrown() {
        assertThatCode(() -> new PersonExternalModelToPersonInternalModelMapper().personMapper().apply(personExternalModel))
                .doesNotThrowAnyException();
    }
}
