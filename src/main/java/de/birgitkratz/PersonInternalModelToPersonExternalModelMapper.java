package de.birgitkratz;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import de.birgitkratz.external.Communication;
import de.birgitkratz.external.CommunicationMeans;
import de.birgitkratz.external.PersonExternalModel;
import de.birgitkratz.internal.PersonInternalModel;

public class PersonInternalModelToPersonExternalModelMapper {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Function<PersonInternalModel, PersonExternalModel> mapPerson() {
        return personInternalModel -> {
            final var personExternalModel = new PersonExternalModel();
            personExternalModel.setName(personInternalModel.getName());
            personExternalModel.setDateOfBirth(personInternalModel.getDateOfBirth().format(dateTimeFormatter));
            if (personInternalModel.getCommunication() != null) {
                personExternalModel.setCommunications(communicationsMapper().apply(personInternalModel.getCommunication()));
            }
            return personExternalModel;
        };
    }

    private Function<de.birgitkratz.internal.Communication, List<Communication>> communicationsMapper() {
        return communication -> {
            List<Communication> communications = new ArrayList<>();
            if (communication.getEmails() != null) {
                final var emailCommunicationList = communication.getEmails().stream()
                        .map(emailCommunication -> new Communication(emailCommunication.value(), CommunicationMeans.EMAIL))
                        .toList();
                communications.addAll(emailCommunicationList);
            }
            if (communication.getTelefons() != null) {
                final var telefonCommunicationList = communication.getTelefons().stream()
                        .map(telefonCommunication -> new Communication(telefonCommunication.value(),
                                CommunicationMeans.TELEFON))
                        .toList();
                communications.addAll(telefonCommunicationList);
            }
            if (communication.getMobiles() != null) {
                final var mobileCommunicationList = communication.getMobiles().stream()
                        .map(mobileCommunication -> new Communication(mobileCommunication.value(),
                                CommunicationMeans.MOBILE))
                        .toList();
                communications.addAll(mobileCommunicationList);
            }
            return communications;
        };
    }
}
