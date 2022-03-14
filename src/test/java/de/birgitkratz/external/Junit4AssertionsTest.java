package de.birgitkratz.external;

import de.birgitkratz.PersonInternalModelToPersonExternalModelMapper;
import de.birgitkratz.TestData;
import de.birgitkratz.internal.PersonInternalModel;
import org.junit.Test;

import static org.junit.Assert.*;

public class Junit4AssertionsTest {

    private final PersonInternalModel personInternalModel = TestData.personInternalModel();

    @Test
    public void testWithMultipleAssertions() {
        final var personExternalModel = new PersonInternalModelToPersonExternalModelMapper().mapPerson().apply(personInternalModel);

        assertNotNull(personExternalModel);
        assertEquals("Birgit", personExternalModel.getName());
        assertEquals("09-02-1968", personExternalModel.getDateOfBirth());

        // bound to order
        assertEquals(5, personExternalModel.getCommunications().size());
        assertEquals(new Communication("mail@birgitkratz.de", CommunicationMeans.EMAIL), personExternalModel.getCommunications().get(0));
        assertEquals(new Communication("business@birgitkratz.de", CommunicationMeans.EMAIL), personExternalModel.getCommunications().get(1));
        assertEquals(new Communication("my primary number", CommunicationMeans.TELEFON), personExternalModel.getCommunications().get(2));
        assertEquals(new Communication("my other number", CommunicationMeans.TELEFON), personExternalModel.getCommunications().get(3));
        assertEquals(new Communication("my mobile number", CommunicationMeans.MOBILE), personExternalModel.getCommunications().get(4));
    }

    @Test
    public void testUsingFiltering() {
        final var personExternalModel = new PersonInternalModelToPersonExternalModelMapper().mapPerson().apply(personInternalModel);

        final var emailCommunications = personExternalModel.getCommunications().stream()
                .filter(c -> CommunicationMeans.EMAIL.equals(c.communicationMeans()))
                .toList();

        assertEquals(2, emailCommunications.size());
        assertEquals(new Communication("mail@birgitkratz.de", CommunicationMeans.EMAIL), emailCommunications.get(0));
        assertEquals(new Communication("business@birgitkratz.de", CommunicationMeans.EMAIL), emailCommunications.get(1));

        final var telefonCommunications = personExternalModel.getCommunications().stream()
                .filter(c -> CommunicationMeans.TELEFON.equals(c.communicationMeans()))
                .toList();

        assertEquals(2, telefonCommunications.size());
        assertEquals(new Communication("my primary number", CommunicationMeans.TELEFON), telefonCommunications.get(0));
        assertEquals(new Communication("my other number", CommunicationMeans.TELEFON), telefonCommunications.get(1));

        final var mobileCommunications = personExternalModel.getCommunications().stream()
                .filter(c -> CommunicationMeans.MOBILE.equals(c.communicationMeans()))
                .toList();

        assertEquals(1, mobileCommunications.size());
        assertEquals(new Communication("my mobile number", CommunicationMeans.MOBILE), mobileCommunications.get(0));
    }
}
