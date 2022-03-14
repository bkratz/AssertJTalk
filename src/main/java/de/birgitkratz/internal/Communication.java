package de.birgitkratz.internal;

import java.util.List;
import java.util.Objects;

public class Communication {
    private List<Email> emails;
    private List<Telefon> telefons;
    private List<Mobile> mobiles;

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(final List<Email> emails) {
        this.emails = emails;
    }

    public List<Telefon> getTelefons() {
        return telefons;
    }

    public void setTelefons(final List<Telefon> telefons) {
        this.telefons = telefons;
    }

    public List<Mobile> getMobiles() {
        return mobiles;
    }

    public void setMobiles(final List<Mobile> mobiles) {
        this.mobiles = mobiles;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Communication that = (Communication) o;
        return Objects.equals(emails, that.emails) && Objects.equals(
                telefons, that.telefons) && Objects.equals(mobiles,
                that.mobiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emails, telefons, mobiles);
    }
}
