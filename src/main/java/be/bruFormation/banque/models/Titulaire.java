package be.bruFormation.banque.models;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @attribute firstName String
 * @attribute lastName String
 * @attribute birthDate LocalDate
 * @invariant firstName != null && nom.length > 0
 * @invariant lastName != null && prenom.length > 0
 * @invariant birthDate > '1900-01-01
 */
public class Titulaire {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public Titulaire(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
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
        LocalDate dateMin = LocalDate.of(1900, 01, 01);
        if (birthDate.isAfter(dateMin)) this.birthDate = birthDate;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Titulaire)) return false;
        Titulaire titulaire = (Titulaire) o;
        return getFirstName().equals(titulaire.getFirstName()) && getLastName().equals(titulaire.getLastName()) && getBirthDate().equals(titulaire.getBirthDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getBirthDate());
    }
}
