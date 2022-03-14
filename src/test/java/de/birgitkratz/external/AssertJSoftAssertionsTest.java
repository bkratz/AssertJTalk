package de.birgitkratz.external;

import de.birgitkratz.PersonInternalModelToPersonExternalModelMapper;
import de.birgitkratz.TestData;
import de.birgitkratz.internal.PersonInternalModel;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.from;
import static org.assertj.core.api.Assertions.tuple;

@ExtendWith(SoftAssertionsExtension.class)
class AssertJSoftAssertionsTest {
    private final PersonInternalModel personInternalModel = TestData.personInternalModel();

    @Test
    void testWithMultipleAssertions(SoftAssertions softly) {
        final var personExternalModel = new PersonInternalModelToPersonExternalModelMapper().mapPerson().apply(personInternalModel);

        softly.assertThat(personExternalModel).isNotNull()
                .hasFieldOrPropertyWithValue("name", "Birgit")
                .returns("02-09-1968", from(PersonExternalModel::getDateOfBirth));

        // not bound to any order
        softly.assertThat(personExternalModel)
                .extracting(PersonExternalModel::getCommunications, InstanceOfAssertFactories.list(Communication.class))
                .hasSize(4)
                .extracting("communicationMeans", "value")
                .containsExactlyInAnyOrder(
                        tuple(CommunicationMeans.EMAIL, "birgit@birgitkratz.de"),
                        tuple(CommunicationMeans.EMAIL, "business@birgitkratz.de"),
                        tuple(CommunicationMeans.TELEFON, "my other number"),
                        tuple(CommunicationMeans.TELEFON, "my primary number"),
                        tuple(CommunicationMeans.MOBILE, "my mobile number"));
    }

    @Test
    void testUsingFiltering() {
        SoftAssertions softly = new SoftAssertions();

        final var personExternalModel = new PersonInternalModelToPersonExternalModelMapper().mapPerson().apply(personInternalModel);

        // extracting, tuple
        softly.assertThat(personExternalModel)
                .extracting(PersonExternalModel::getCommunications, InstanceOfAssertFactories.list(Communication.class))
                .filteredOn(communication -> CommunicationMeans.EMAIL.equals(communication.communicationMeans()))
                .hasSize(2)
                .extracting("communicationMeans", "value")
                .containsExactlyInAnyOrder(
                        tuple(CommunicationMeans.EMAIL, "birgit@birgitkratz.de"),
                        tuple(CommunicationMeans.EMAIL, "business@birgitkratz.de"));

        // flatExtracting
        softly.assertThat(personExternalModel.getCommunications())
                .filteredOn(communication -> CommunicationMeans.TELEFON.equals(communication.communicationMeans()))
                .hasSize(2)
                .flatExtracting(Communication::value)
                .containsExactlyInAnyOrder("my other number", "my primary number");

        // first
        softly.assertThat(personExternalModel)
                .extracting(PersonExternalModel::getCommunications, InstanceOfAssertFactories.list(Communication.class))
                .filteredOn(communication -> CommunicationMeans.MOBILE.equals(communication.communicationMeans()))
                .hasSize(1)
                .first()
                .hasFieldOrPropertyWithValue("value", "meine mobile number")
                .returns(CommunicationMeans.MOBILE, from(Communication::communicationMeans));

        softly.assertAll();
    }
}
