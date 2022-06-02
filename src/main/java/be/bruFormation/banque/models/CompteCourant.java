package be.bruFormation.banque.models;

import java.util.Objects;

/**
 * Class mutable représentant un compt pouvant aller en négatif
 * FA : CompteCourant{number,solde,creditLine}
 * @attribute number String -> format IBAN
 * @attribute owner Titulaire
 * @attribute solde double
 * @attribute ligneDeCredit double

 * @invariant number != null && number.length =19
 * @invariant owner != null
 * @invariant solde > -creditLine
 * @invariant creditLine >= 0
 */
public class CompteCourant {
    private String number;
    private Titulaire owner;
    private double solde;
    private double creditLine;

    /**
     * Construit un objet Compte courant
     *
     * @param number the number of the account
     * @param owner  the owner of the Count
     */
    public CompteCourant(String number, Titulaire owner) {
        setNumber(number);
        setOwner(owner);
    }

    public CompteCourant(String number, Titulaire owner, double solde) {
        setNumber(number);
        setOwner(owner);
        setSolde(solde);
    }

    public CompteCourant(String number, Titulaire owner, double solde, double creditLine) {
        setNumber(number);
        setOwner(owner);
        setSolde(solde);
        setCreditLine(creditLine);
    }

    /**
     * Methode permetant d'ajouter un montant au solde du compte
     *
     * @param amount > 0
     * @modify this.solde | this.solde = this.solde + amount
     */
    private void deposit(double amount) {
        if (amount <= 0) return;
        this.solde += amount;
    }

    /**
     * Methode permetant de soustraire un montant au solde du compte
     *
     * @param amount > 0
     * @modify this.solde |  this.solde = this.solde - amount ssi this.solde - amount >= -this.creditLine >=
     */
    private void withdrawal(double amount) {
        if (amount <= 0) return;
        if (this.solde - amount >= -this.creditLine) return;
        this.solde += amount;
    }

    public void setOwner(Titulaire owner) {
        this.owner = owner;
    }

    /**
     * Methode qui donne la valeur de l'attribue number
     *
     * @return le numedro du compte courrant
     */
    public String getNumber() {
        return number;
    }

    public Titulaire getOwner() {
        return owner;
    }

    public double getSolde() {
        return solde;
    }

    public double getCreditLine() {
        return creditLine;
    }

    public void setNumber(String numero) {
        if (number != null && number.length() > 0) {
            this.number = numero;
        }
    }

    private void setSolde(double solde) {
        this.solde = solde;
    }

    public void setCreditLine(double creditLine) {
        this.creditLine = creditLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompteCourant)) return false;
        CompteCourant that = (CompteCourant) o;
        return Double.compare(that.getSolde(), getSolde()) == 0 && Double.compare(that.getCreditLine(), getCreditLine()) == 0 && getNumber().equals(that.getNumber()) && getOwner().equals(that.getOwner());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getOwner(), getSolde(), getCreditLine());
    }
}
