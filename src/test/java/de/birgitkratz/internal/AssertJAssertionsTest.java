package de.birgitkratz.internal;

import de.birgitkratz.PersonExternalModelToPersonInternalModelMapper;
import de.birgitkratz.TestData;
import de.birgitkratz.external.PersonExternalModel;
import org.assertj.core.api.Condition;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.*;

class AssertJAssertionsTest {

    private final PersonExternalModel personExternalModel = TestData.personExternalModel();

    @Test
    void testUsingSatisfies() {
        final var personInternalModel = new PersonExternalModelToPersonInternalModelMapper().personMapper().apply(personExternalModel);

        assertThat(personInternalModel)
                .hasFieldOrPropertyWithValue("name", "Birgit")
                .returns(LocalDate.of(1968, 2, 9), from(PersonInternalModel::getDateOfBirth))
                .satisfies(person -> assertThat(person.getDateOfBirth()).isCloseTo(LocalDate.now().minusYears(50), byLessThan(5, ChronoUnit.YEARS)))
                .extracting("communication").asInstanceOf(InstanceOfAssertFactories.type(Communication.class))
                .satisfies(communication -> {
                    assertThat(communication.getMobiles()).hasSize(1);
                    assertThat(communication.getTelefons()).hasSize(2);
                    assertThat(communication.getEmails()).hasSize(2);
                });
//                .satisfies(
//                        communication -> assertThat(communication.getMobiles()).hasSize(1),
//                        communication -> assertThat(communication.getTelefons()).hasSize(2),
//                        communication -> assertThat(communication.getEmails()).hasSize(2)
//                );
    }

    @Test
    void testUsingCustomConditions() {
        final var personInternalModel = new PersonExternalModelToPersonInternalModelMapper().personMapper().apply(personExternalModel);

        Condition<Communication> mobiles = new Condition<>(communication -> communication.getMobiles() != null && !communication.getMobiles().isEmpty(), "mobile communications");
        Condition<Communication> telefons = new Condition<>(communication -> communication.getTelefons() != null && !communication.getTelefons().isEmpty(), "telefon communications");
        Condition<Communication> emails = new Condition<>(communication -> communication.getEmails() != null && !communication.getEmails().isEmpty(), "email communications");

        assertThat(personInternalModel)
                .extracting("communication").asInstanceOf(InstanceOfAssertFactories.type(Communication.class))
                .has(anyOf(
                        new Condition<>(communication -> communication.getMobiles() != null, "mobile communication"),
                        new Condition<>(communication -> communication.getTelefons() != null, "telefon communication")
                ))
                // 'has' or 'is' are synonym, just use the one better fitting to for a sentence
                .has(emails)
                .has(telefons)
                .has(mobiles)
                .has(anyOf(telefons, emails, mobiles))
                .has(allOf(telefons, emails, mobiles));
    }
}
