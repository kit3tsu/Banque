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
    private Holder holder;
    private double solde;
    public Account(Account account){
        this.solde = account.solde;
        this.holder = account.holder;
        this.number = account.number;
    }
    public Account(String number, Holder holder) {
        setNumber(number);
        setHolder(holder);
    }
    public Account(String number, Holder holder, double solde){
        setNumber(number);
        setHolder(holder);
        setSolde(solde);
    }

    public String getNumber() {
        return number;
    }

    private void setNumber(String number) {
        if (this.number != null && this.number.length() > 0) {
            this.number = number;
        }
    }

    public Holder getHolder() {
        return holder;
    }

    private void setHolder(Holder holder) {
        this.holder = holder;
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
    public double sumAccount(Account secondAccount){
        return  this.solde + secondAccount.getSolde();
    }
}
