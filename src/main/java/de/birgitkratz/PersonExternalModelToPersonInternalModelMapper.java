package de.birgitkratz;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

import de.birgitkratz.external.CommunicationMeans;
import de.birgitkratz.external.PersonExternalModel;
import de.birgitkratz.internal.*;

public class PersonExternalModelToPersonInternalModelMapper {

    public Function<PersonExternalModel, PersonInternalModel> personMapper() {
        return personExternalModel -> {
            try {
                final var personInternalModel = new PersonInternalModel();
                personInternalModel.setName(personExternalModel.getName());
                personInternalModel.setDateOfBirth(LocalDate.parse(personExternalModel.getDateOfBirth(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                if (personExternalModel.getCommunications() != null) {
                    personInternalModel.setCommunication(communicationsMapper().apply(personExternalModel.getCommunications()));
                }
                return personInternalModel;
            } catch (Exception e) {
                throw new MappingException("Something went wrong while mapping", e);
            }
        };
    }

    private Function<List<de.birgitkratz.external.Communication>, Communication> communicationsMapper() {
        return communications -> {
            final var communication = new Communication();
            final var emailCommunications = communications.stream()
                    .filter(c -> CommunicationMeans.EMAIL.equals(c.communicationMeans()))
                    .map(c -> new Email(c.value()))
                    .toList();
            final var telefonCommunications = communications.stream()
                    .filter(c -> CommunicationMeans.TELEFON.equals(c.communicationMeans()))
                    .map(c -> new Telefon(c.value()))
                    .toList();
            final var mobileCommunications = communications.stream()
                    .filter(c -> CommunicationMeans.MOBILE.equals(c.communicationMeans()))
                    .map(c -> new Mobile(c.value()))
                    .toList();

            communication.setEmails(emailCommunications);
            communication.setTelefons(telefonCommunications);
            communication.setMobiles(mobileCommunications);
            return communication;
        };
    }
}
