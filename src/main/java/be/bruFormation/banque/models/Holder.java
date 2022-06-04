package be.bruFormation.banque.models;

import java.time.LocalDate;
import java.util.Objects;
/**
 * Mutable class representing an account holder within the bank
 * FA: Account Holder{name, lastName}
 *
 * @attribute lastName String
 * @attribute firstname String
 * @attribute birthDate LocalDate
 *
 * @invariant firstName != null && firstName.length > 0
 * @invariant lastName != null && lastName.length > 0
 */

public class Holder {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    public Holder(String firstName, String lastName, LocalDate birthDate) {
        setFirstName(firstName);
        setLastName(lastName);
        setBirthDate(birthDate);
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    private void setFirstName(String firstName) {
        if (firstName != null && firstName.length() > 0) {
            this.firstName = firstName;
        }
    }
    private void setLastName(String lastName) {
        if ((lastName != null) && lastName.length() > 0) this.lastName = lastName;
    }
    private void setBirthDate(LocalDate birthDate) {
        LocalDate dateMin = LocalDate.of(1900, 1, 1);
        if (birthDate.isAfter(dateMin)) this.birthDate = birthDate;

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Holder holder)) return false;
        return getFirstName().equals(holder.getFirstName()) && getLastName().equals(holder.getLastName()) && getBirthDate().equals(holder.getBirthDate());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getBirthDate());
    }
}
