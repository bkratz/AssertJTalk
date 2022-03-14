package de.birgitkratz.internal;

import java.time.LocalDate;
import java.util.Objects;

public class PersonInternalModel {
    private String name;
    private LocalDate dateOfBirth;
    private Communication communication;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Communication getCommunication() {
        return communication;
    }

    public void setCommunication(final Communication communication) {
        this.communication = communication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonInternalModel that = (PersonInternalModel) o;
        return Objects.equals(name, that.name) && Objects.equals(dateOfBirth, that.dateOfBirth) && Objects.equals(communication, that.communication);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateOfBirth, communication);
    }
}
