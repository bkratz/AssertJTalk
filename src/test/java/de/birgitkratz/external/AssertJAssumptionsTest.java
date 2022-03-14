package de.birgitkratz.external;

import de.birgitkratz.PersonInternalModelToPersonExternalModelMapper;
import de.birgitkratz.TestData;
import de.birgitkratz.internal.PersonInternalModel;
import org.assertj.core.api.Assumptions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class AssertJAssumptionsTest {

    private final PersonInternalModel personInternalModel = TestData.personInternalModel();

    @Test
    void testWithMultipleAssertions() {
//        Assumptions.assumeThat(personInternalModel.getDateOfBirth()).isAfter(LocalDate.now());
        Assumptions.assumeThat(personInternalModel.getDateOfBirth()).isBefore(LocalDate.now());

        final var personExternalModel = new PersonInternalModelToPersonExternalModelMapper().mapPerson().apply(personInternalModel);

        assertThat(personExternalModel).isNotNull()
                .hasFieldOrPropertyWithValue("name", "Birgit")
                .returns("09-02-1968", from(PersonExternalModel::getDateOfBirth));

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
    }
}
