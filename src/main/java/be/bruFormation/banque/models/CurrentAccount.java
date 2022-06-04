package be.bruFormation.banque.models;

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
public class CurrentAccount extends Account {
    private String number;
    private Holder owner;
    private double solde;
    private double creditLine;

    /**
     * Construit un objet Compte courant
     *
     * @param number the number of the account
     * @param owner  the owner of the Count
     */
    public CurrentAccount(String number, Holder owner) {
        super(number,owner);
    }

    public CurrentAccount(String number, Holder owner, double solde) {
        super(number,owner,solde);
    }

    public CurrentAccount(String number, Holder owner, double solde, double creditLine) {
        super(number,owner,solde);
        setCreditLine(creditLine);
    }

    public CurrentAccount(CurrentAccount account) {
        super(account);
        this.creditLine = account.getCreditLine();
    }


    /**
     * Methode permetant de soustraire un montant au solde du compte
     *
     * @param amount > 0
     * @modify this.solde |  this.solde = this.solde - amount ssi this.solde - amount >= -this.creditLine >=
     */
    public void withdrawal(double amount) {
        if (amount <= 0) return;
        if (this.solde - amount >= -this.creditLine) return;
        this.solde += amount;
    }

    private void setOwner(Holder owner) {
        this.owner = owner;
    }

    public double getCreditLine() {
        return creditLine;
    }

    private void setNumber(String numero) {
        if (number != null && number.length() > 0) {
            this.number = numero;
        }
    }

    private void setSolde(double solde) {
        this.solde = solde;
    }

    private void setCreditLine(double creditLine) {
        this.creditLine = creditLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrentAccount)) return false;
        CurrentAccount that = (CurrentAccount) o;
        return Double.compare(that.getSolde(), getSolde()) == 0 && Double.compare(that.getCreditLine(), getCreditLine()) == 0 && getNumber().equals(that.getNumber()) && getHolder().equals(that.getHolder());
    }


}
