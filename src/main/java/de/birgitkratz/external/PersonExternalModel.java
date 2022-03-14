package de.birgitkratz.external;

import java.util.List;
import java.util.Objects;

public class PersonExternalModel {
    private String name;
    private String dateOfBirth;
    private List<Communication> communications;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Communication> getCommunications() {
        return communications;
    }

    public void setCommunications(final List<Communication> communications) {
        this.communications = communications;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonExternalModel that = (PersonExternalModel) o;
        return Objects.equals(name, that.name) && Objects.equals(dateOfBirth, that.dateOfBirth) && Objects.equals(communications, that.communications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateOfBirth, communications);
    }
}
