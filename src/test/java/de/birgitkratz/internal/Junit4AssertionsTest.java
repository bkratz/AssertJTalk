package de.birgitkratz.internal;

import de.birgitkratz.PersonExternalModelToPersonInternalModelMapper;
import de.birgitkratz.TestData;
import de.birgitkratz.external.PersonExternalModel;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Junit4AssertionsTest {
    private final PersonExternalModel personExternalModel = TestData.personExternalModel();

    @Test
    public void testWithMultipleAssertions() {
        final var personInternalModel = new PersonExternalModelToPersonInternalModelMapper().personMapper().apply(personExternalModel);

        assertNotNull(personInternalModel);
        assertEquals("Birgit", personInternalModel.getName());
        assertEquals(LocalDate.of(1968, 2, 9), personInternalModel.getDateOfBirth());

        assertNotNull(personInternalModel.getCommunication());

        final var communication = personInternalModel.getCommunication();

        // bound to order
        assertEquals(2, communication.getEmails().size());
        assertEquals("mail@birgitkratz.de", communication.getEmails().get(0).value());
        assertEquals("business@birgitkratz.de", communication.getEmails().get(1).value());

        assertEquals(2, communication.getTelefons().size());
        assertEquals("my primary number", communication.getTelefons().get(0).value());
        assertEquals("my other number", communication.getTelefons().get(1).value());

        assertEquals(1, communication.getMobiles().size());
        assertEquals("my mobile number", communication.getMobiles().get(0).value());
    }

    @Test
    public void testWithExpectedObject() {
        final var expectedPersonInternalModel = TestData.personInternalModel();

        final var personInternalModel = new PersonExternalModelToPersonInternalModelMapper().personMapper().apply(personExternalModel);

        assertNotNull(personInternalModel);

        // here all types should have equals()/hashCode() methods
        assertEquals(expectedPersonInternalModel, personInternalModel);
    }
}