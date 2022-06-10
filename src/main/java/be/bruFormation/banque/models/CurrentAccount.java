package be.bruFormation.banque.models;

/**
 * Mutable class representing an account that can go negative
 * FA: CurrentAccount{number, balance, creditLine}
 *
 * @attribute creditLine double
 *
 * @invariant creditLine >= 0
 * @see be.bruFormation.banque.models.Account
 */
public class CurrentAccount extends Account {
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
     * @see Account#withdrawal
     */
    public void withdrawal(double amount) {
        if (getSolde() - amount >= -this.creditLine) return;
        super.withdrawal(amount);
    }
    public double getCreditLine() {
        return creditLine;
    }
    private void setCreditLine(double creditLine) {
        this.creditLine = creditLine;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Current{");

        builder.append(super.toString());
        builder.append("ligneCredit= ").append(creditLine);

        return builder.append("}").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrentAccount)) return false;
        CurrentAccount account = (CurrentAccount) o;
        return Double.compare(account.getSolde(), getSolde()) == 0 && Double.compare(account.getCreditLine(), getCreditLine()) == 0 && getNumber().equals(account.getNumber()) && getHolder().equals(account.getHolder());
    }
}
