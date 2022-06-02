package be.bruFormation.banque.models;

/**
 * Class mutable représentant un compt
 * FA : Account{number,solde}
 *
 * @attribute number String -> format IBAN
 * @attribute owner Titulaire
 * @attribute solde double
 * @invariant number != null && number.length =19
 * @invariant owner != null
 * @invariant solde > -creditLine
 */
public class Account {
    private String number;
    private Titulaire owner;
    private double solde;

    public String getNumber() {
        return number;
    }

    private void setNumber(String number) {
        if (this.number != null && this.number.length() > 0) {
            this.number = number;
        }
    }

    public Titulaire getOwner() {
        return owner;
    }

    private void setOwner(Titulaire owner) {
        this.owner = owner;
    }

    public double getSolde() {
        return solde;
    }

    private void setSolde(double solde) {
        if (solde != 0) {
            this.solde = solde;
        }
    }

    public void withdrawal(double amount) {
        if (amount <= 0) return;
        if (this.solde - amount >= 0) return;
        this.solde += amount;
    }

    /**
     * Methode permetant d'ajouter un montant au solde du compte
     *
     * @param amount > 0
     * @modify this.solde | this.solde = this.solde + amount
     */
    public void deposit(double amount) {
        if (amount <= 0) return;
        this.solde += amount;
    }
}
