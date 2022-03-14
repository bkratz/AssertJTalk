package de.birgitkratz.external;

import de.birgitkratz.PersonInternalModelToPersonExternalModelMapper;
import de.birgitkratz.TestData;
import de.birgitkratz.internal.PersonInternalModel;
import org.assertj.core.api.Condition;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AssertJAssertionsTest {

    private final PersonInternalModel personInternalModel = TestData.personInternalModel();

    @Test
    void testWithMultipleAssertions() {
        final var personExternalModel = new PersonInternalModelToPersonExternalModelMapper().mapPerson().apply(personInternalModel);

        // checking simple fields
        assertThat(personExternalModel).isNotNull()
                .hasFieldOrPropertyWithValue("name", "Birgit")
                .returns("09-02-1968", from(PersonExternalModel::getDateOfBirth));

        // checking iterables
        // not bound to any order
        assertThat(personExternalModel)
                .extracting(PersonExternalModel::getCommunications, InstanceOfAssertFactories.list(Communication.class))
                .hasSize(5)
                .extracting("communicationMeans", "value")
                .containsExactlyInAnyOrder(
                        tuple(CommunicationMeans.EMAIL, "mail@birgitkratz.de"),
                        tuple(CommunicationMeans.EMAIL, "business@birgitkratz.de"),
                        tuple(CommunicationMeans.TELEFON, "my other number"),
                        tuple(CommunicationMeans.TELEFON, "my primary number"),
                        tuple(CommunicationMeans.MOBILE, "my mobile number"));

        // checking iterables
        // alternative with 'asInstanceOf' and 'map' (synonymous for 'extracting')
        assertThat(personExternalModel)
                .extracting("communications")
                .asInstanceOf(InstanceOfAssertFactories.list(Communication.class))
                .hasSize(5)
                .map(Communication::communicationMeans, Communication::value)
                .contains(tuple(CommunicationMeans.EMAIL, "mail@birgitkratz.de"));
    }

    @Test
    void testUsingFiltering() {
        final var personExternalModel = new PersonInternalModelToPersonExternalModelMapper().mapPerson().apply(personInternalModel);

        // extracting, tuple
        assertThat(personExternalModel)
                .extracting(PersonExternalModel::getCommunications, InstanceOfAssertFactories.list(Communication.class))
                .filteredOn(communication -> CommunicationMeans.EMAIL.equals(communication.communicationMeans()))
                .hasSize(2)
                .extracting("communicationMeans", "value")
                .containsExactlyInAnyOrder(
                        tuple(CommunicationMeans.EMAIL, "mail@birgitkratz.de"),
                        tuple(CommunicationMeans.EMAIL, "business@birgitkratz.de"));

        // flatExtracting
        assertThat(personExternalModel.getCommunications())
                .filteredOn(communication -> CommunicationMeans.TELEFON.equals(communication.communicationMeans()))
                .hasSize(2)
                .flatExtracting(Communication::value)
//                alternatively
//                .flatMap(Communication::value)
                .containsExactlyInAnyOrder("my other number", "my primary number");

        // first
        assertThat(personExternalModel)
                .extracting(PersonExternalModel::getCommunications, InstanceOfAssertFactories.list(Communication.class))
                .filteredOn(communication -> CommunicationMeans.MOBILE.equals(communication.communicationMeans()))
                .hasSize(1)
                .first()
                .hasFieldOrPropertyWithValue("value", "my mobile number")
                .returns(CommunicationMeans.MOBILE, from(Communication::communicationMeans));
    }

    @Test
    void testWithConditions() {
        final var personExternalModel = new PersonInternalModelToPersonExternalModelMapper().mapPerson().apply(personInternalModel);
        assertThat(personExternalModel.getCommunications())
                .hasSize(5)
                .haveExactly(2, new Condition<>(communication -> CommunicationMeans.EMAIL.equals(communication.communicationMeans()), "communicationMeans EMAIL"))
                .haveExactly(2, new Condition<>(communication -> CommunicationMeans.TELEFON.equals(communication.communicationMeans()), "communicationMeans TELEFON"))
                .haveExactly(1, new Condition<>(communication -> CommunicationMeans.MOBILE.equals(communication.communicationMeans()), "communicationMeans MOBILE"));
    }
}
