package de.birgitkratz;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import de.birgitkratz.internal.Communication;
import de.birgitkratz.internal.Email;
import de.birgitkratz.internal.Mobile;
import de.birgitkratz.internal.PersonInternalModel;
import de.birgitkratz.internal.Telefon;
import de.birgitkratz.external.CommunicationMeans;
import de.birgitkratz.external.PersonExternalModel;

public class TestData {
    public static PersonExternalModel personExternalModel() {
        final var personExternalModel = new PersonExternalModel();
        personExternalModel.setName("Birgit");
        personExternalModel.setDateOfBirth("09-02-1968");

        final var communications = new ArrayList<de.birgitkratz.external.Communication>();
        communications.add(new de.birgitkratz.external.Communication("mail@birgitkratz.de", CommunicationMeans.EMAIL));
        communications.add(new de.birgitkratz.external.Communication("business@birgitkratz.de", CommunicationMeans.EMAIL));
        communications.add(new de.birgitkratz.external.Communication("my primary number", CommunicationMeans.TELEFON));
        communications.add(new de.birgitkratz.external.Communication("my other number", CommunicationMeans.TELEFON));
        communications.add(new de.birgitkratz.external.Communication("my mobile number", CommunicationMeans.MOBILE));

        personExternalModel.setCommunications(communications);
        return personExternalModel;
    }

    public static PersonInternalModel personInternalModel() {
        final var personInternalModel = new PersonInternalModel();
        personInternalModel.setName("Birgit");
        personInternalModel.setDateOfBirth(LocalDate.of(1968, 2, 9));

        final var communication = new Communication();
        communication.setEmails(List.of(
                new Email("mail@birgitkratz.de"),
                new Email("business@birgitkratz.de")));

        communication.setTelefons(List.of(
                new Telefon("my primary number"),
                new Telefon("my other number")));

        communication.setMobiles(List.of(
                new Mobile("my mobile number")));

        personInternalModel.setCommunication(communication);
        return personInternalModel;
    }
}
