package de.birgitkratz.external;

import de.birgitkratz.PersonInternalModelToPersonExternalModelMapper;
import de.birgitkratz.TestData;
import de.birgitkratz.internal.PersonInternalModel;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IntentionallyFailingTest {
    private final PersonInternalModel personInternalModel = TestData.personInternalModel();

    @Test
    public void junit4_intentionallyFail_Name() {
        final var personExternalModel = new PersonInternalModelToPersonExternalModelMapper().mapPerson().apply(personInternalModel);

        assertEquals("Fred", personExternalModel.getName());
    }

    @Test
    public void assertJ_intentionallyFail_Name() {
        final var personExternalModel = new PersonInternalModelToPersonExternalModelMapper().mapPerson().apply(personInternalModel);

        assertThat(personExternalModel.getName())
                .as("Check person name")
                .withFailMessage("Wrong person name")
                .isEqualTo("Fred");
    }

    @Test
    public void junit4_intentionallyFail_CommunicationIsNull() {
        final var personExternalModel = new PersonInternalModelToPersonExternalModelMapper().mapPerson().apply(personInternalModel);
        personExternalModel.setCommunications(null);

        assertTrue(personExternalModel.getCommunications().contains(new Communication("my primary number", CommunicationMeans.TELEFON)));
    }

    @Test
    public void assertJ_intentionallyFail_CommunicationIsNull() {
        final var personExternalModel = new PersonInternalModelToPersonExternalModelMapper().mapPerson().apply(personInternalModel);
        personExternalModel.setCommunications(null);

        assertThat(personExternalModel.getCommunications()).contains(new Communication("my primary number", CommunicationMeans.TELEFON));
    }
}
