package de.birgitkratz.internal;

import de.birgitkratz.PersonExternalModelToPersonInternalModelMapper;
import de.birgitkratz.TestData;
import de.birgitkratz.external.PersonExternalModel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AssertJComparisonTest {

    private final PersonExternalModel personExternalModel = TestData.personExternalModel();

    @Test
    void testUsingRecursiveComparison() {
        final var expectedPersonInternalModel = TestData.personInternalModel();
        // uncomment to show failing message
//        expectedPersonInternalModel.setName("BIRGIT");

        final var personInternalModel = new PersonExternalModelToPersonInternalModelMapper().personMapper().apply(personExternalModel);

        assertThat(personInternalModel)
                .usingRecursiveComparison()
                .isEqualTo(expectedPersonInternalModel);
    }

    @Test
    void testUsingRecursiveComparisonIgnoringFields() {
        final var expectedPersonInternalModel = TestData.personInternalModel();
        expectedPersonInternalModel.setName("Fred");

        final var personInternalModel = new PersonExternalModelToPersonInternalModelMapper().personMapper().apply(personExternalModel);

        assertThat(personInternalModel)
                .usingRecursiveComparison()
                .ignoringFields("name")
                .isEqualTo(expectedPersonInternalModel);
    }

    @Test
    void testUsingComparator() {
        final var expectedCaseInsensitivity = TestData.personInternalModel();
        expectedCaseInsensitivity.setName("BIRGIT");

        final var personInternalModel = new PersonExternalModelToPersonInternalModelMapper().personMapper().apply(personExternalModel);

        assertThat(personInternalModel)
                .usingComparatorForFields(String.CASE_INSENSITIVE_ORDER, "name")
                .isEqualTo(personInternalModel);
    }
}
