package de.birgitkratz.external;

import de.birgitkratz.PersonInternalModelToPersonExternalModelMapper;
import de.birgitkratz.TestData;
import de.birgitkratz.internal.PersonInternalModel;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static de.birgitkratz.external.PersonExternalModelAssert.assertThat;

class CustomAssertionsTest {

    private final PersonInternalModel personInternalModel = TestData.personInternalModel();

    @Test
    void testNameUsingCustomAssertion_OK() {
        final var personExternalModel = new PersonInternalModelToPersonExternalModelMapper().mapPerson().apply(personInternalModel);

        assertThat(personExternalModel).hasName("Birgit");
    }

    @Test
    void testNameUsingCustomAssertion_Fail() {
        final var personExternalModel = new PersonInternalModelToPersonExternalModelMapper().mapPerson().apply(personInternalModel);

        assertThat(personExternalModel).hasName("Fred");
    }

    @Test
    void testCommunicationsUsingCustomAssertion_OK() {
        final var personExternalModel = new PersonInternalModelToPersonExternalModelMapper().mapPerson().apply(personInternalModel);

        assertThat(personExternalModel)
                .hasCommunicationOfTypeWithValue(CommunicationMeans.EMAIL, "business@birgitkratz.de")
                .hasCommunicationOfTypeWithValue(CommunicationMeans.EMAIL, "mail@birgitkratz.de")
                .hasCommunicationOfTypeWithValue(CommunicationMeans.TELEFON, "my other number")
                .hasCommunicationOfTypeWithValue(CommunicationMeans.TELEFON, "my primary number")
                .hasCommunicationOfTypeWithValue(CommunicationMeans.MOBILE, "my mobile number");
    }

    @Test
    void testCommunicationsUsingCustomAssertion_CommunicationIsNull() {
        final var personExternalModel = new PersonInternalModelToPersonExternalModelMapper().mapPerson().apply(personInternalModel);
        personExternalModel.setCommunications(null);

        assertThat(personExternalModel).hasCommunicationOfTypeWithValue(CommunicationMeans.TELEFON, "my primary number");
    }

    @Test
    void testCommunicationsUsingCustomAssertion_CommunicationIsEmpty() {
        final var personExternalModel = new PersonInternalModelToPersonExternalModelMapper().mapPerson().apply(personInternalModel);
        personExternalModel.setCommunications(Collections.emptyList());

        assertThat(personExternalModel).hasCommunicationOfTypeWithValue(CommunicationMeans.MOBILE, "my mobile number");
    }
}
