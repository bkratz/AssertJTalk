package de.birgitkratz.external;

import de.birgitkratz.PersonInternalModelToPersonExternalModelMapper;
import de.birgitkratz.TestData;
import de.birgitkratz.internal.PersonInternalModel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//import org.junit.Test;
//import static org.junit.Assert.*;

public class Junit4AssertionsTest {

    private final PersonInternalModel personInternalModel = TestData.personInternalModel();

    @Test
    public void testWithMultipleAssertions() {
        final var personExternalModel = new PersonInternalModelToPersonExternalModelMapper().mapPerson().apply(personInternalModel);

        assertNotNull(personExternalModel);
        assertNotNull(personExternalModel.getName());
        assertNotNull(personExternalModel.getDateOfBirth());
        assertNotNull(personExternalModel.getCommunications());

        assertEquals("Birgit", personExternalModel.getName());
        assertEquals("09-02-1968", personExternalModel.getDateOfBirth());

        final var communications = personExternalModel.getCommunications();

        assertEquals(5, communications.size());

        // bound to order
        assertEquals(new Communication("mail@birgitkratz.de", CommunicationMeans.EMAIL), communications.get(0));
        assertEquals(new Communication("business@birgitkratz.de", CommunicationMeans.EMAIL), communications.get(1));
        assertEquals(new Communication("my primary number", CommunicationMeans.TELEFON), communications.get(2));
        assertEquals(new Communication("my other number", CommunicationMeans.TELEFON), communications.get(3));
        assertEquals(new Communication("my mobile number", CommunicationMeans.MOBILE), communications.get(4));

        //----------------------
        // not bound to order
        assertTrue(communications.contains(new Communication("business@birgitkratz.de", CommunicationMeans.EMAIL)));
        assertTrue(communications.contains(new Communication("mail@birgitkratz.de", CommunicationMeans.EMAIL)));
        assertTrue(communications.contains(new Communication("my primary number", CommunicationMeans.TELEFON)));
        assertTrue(communications.contains(new Communication("my other number", CommunicationMeans.TELEFON)));
        assertTrue(communications.contains(new Communication("my mobile number", CommunicationMeans.MOBILE)));
    }

    @Test
    public void testUsingFiltering() {
        final var personExternalModel = new PersonInternalModelToPersonExternalModelMapper().mapPerson().apply(personInternalModel);

        final var emailCommunications = personExternalModel.getCommunications().stream()
                .filter(c -> CommunicationMeans.EMAIL.equals(c.communicationMeans()))
                .toList();

        assertEquals(2, emailCommunications.size());
        assertTrue(emailCommunications.contains(new Communication("mail@birgitkratz.de", CommunicationMeans.EMAIL)));
        assertTrue(emailCommunications.contains(new Communication("business@birgitkratz.de", CommunicationMeans.EMAIL)));

        final var telefonCommunications = personExternalModel.getCommunications().stream()
                .filter(c -> CommunicationMeans.TELEFON.equals(c.communicationMeans()))
                .toList();

        assertEquals(2, telefonCommunications.size());
        assertTrue(telefonCommunications.contains(new Communication("my primary number", CommunicationMeans.TELEFON)));
        assertTrue(telefonCommunications.contains(new Communication("my other number", CommunicationMeans.TELEFON)));

        final var mobileCommunications = personExternalModel.getCommunications().stream()
                .filter(c -> CommunicationMeans.MOBILE.equals(c.communicationMeans()))
                .toList();

        assertEquals(1, mobileCommunications.size());
        assertTrue(mobileCommunications.contains(new Communication("my mobile number", CommunicationMeans.MOBILE)));
    }
}
