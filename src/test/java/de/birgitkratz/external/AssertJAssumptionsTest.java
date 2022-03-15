package de.birgitkratz.external;

import de.birgitkratz.PersonInternalModelToPersonExternalModelMapper;
import de.birgitkratz.TestData;
import de.birgitkratz.internal.PersonInternalModel;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;

class AssertJAssumptionsTest {

    private final PersonInternalModel personInternalModel = TestData.personInternalModel();

    @Test
    void testWithAssumption() {
        Assumptions.assumeThat(personInternalModel.getDateOfBirth()).isBefore(LocalDate.now());

        final var personExternalModel = new PersonInternalModelToPersonExternalModelMapper().mapPerson().apply(personInternalModel);

        assertThat(personExternalModel).isNotNull()
                .hasFieldOrPropertyWithValue("name", "Birgit")
                .returns("09-02-1968", from(PersonExternalModel::getDateOfBirth));
    }

    @Test
    @EnabledOnOs(OS.MAC)
    void testJUnit5OnMac() {
        final var personExternalModel = new PersonInternalModelToPersonExternalModelMapper().mapPerson().apply(personInternalModel);

        assertThat(personExternalModel).isNotNull()
                .hasFieldOrPropertyWithValue("name", "Birgit")
                .returns("09-02-1968", from(PersonExternalModel::getDateOfBirth));
    }

    @Test
    @EnabledIf("customCondition")
    void testJUnit5Condition() {
        final var personExternalModel = new PersonInternalModelToPersonExternalModelMapper().mapPerson().apply(personInternalModel);

        assertThat(personExternalModel).isNotNull()
                .hasFieldOrPropertyWithValue("name", "Birgit")
                .returns("09-02-1968", from(PersonExternalModel::getDateOfBirth));
    }

    private boolean customCondition() {
        return personInternalModel.getDateOfBirth().isBefore(LocalDate.now());
    }
}
