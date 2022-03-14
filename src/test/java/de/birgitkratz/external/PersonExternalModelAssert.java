package de.birgitkratz.external;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.InstanceOfAssertFactories;

import java.util.Objects;

import static org.assertj.core.api.Assertions.tuple;

public class PersonExternalModelAssert extends AbstractAssert<PersonExternalModelAssert, PersonExternalModel> {

    public PersonExternalModelAssert(PersonExternalModel actual) {
        super(actual, PersonExternalModelAssert.class);
    }

    public static PersonExternalModelAssert assertThat(PersonExternalModel actual) {
        return new PersonExternalModelAssert(actual);
    }

    public PersonExternalModelAssert hasName (String name) {
        isNotNull();

        if (!Objects.equals(actual.getName(), name)) {
            failWithMessage("Expected person name to be <%s> but was <%s>", name, actual.getName());
        }

        return this;
    }

    public PersonExternalModelAssert hasCommunicationOfTypeWithValue(CommunicationMeans communicationMeans, String value) {
        isNotNull();

        assertThat(actual)
                .extracting(PersonExternalModel::getCommunications, InstanceOfAssertFactories.list(Communication.class))
                .isNotEmpty()
                .extracting("communicationMeans", "value")
                .contains(tuple(communicationMeans, value));

        return this;
    }
}
