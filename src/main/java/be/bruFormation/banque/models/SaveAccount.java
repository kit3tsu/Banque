package be.bruFormation.banque.models;

import com.google.common.base.MoreObjects;

import java.time.LocalDate;

class SaveAccount extends Account {
    private String number;
    private Titulaire owner;
    private double solde;
    private LocalDate lastWithdraw;


    public SaveAccount(String number, Titulaire owner, double solde) {
        this.setNumber(number);
        this.setOwner(owner);
        this.setSolde(solde);
    }

    public SaveAccount(SaveAccount account) {
        this.number = account.getNumber();
        this.lastWithdraw = account.getLastWithdraw();
        this.owner = account.getOwner();
        this.solde = account.getSolde();
    }

    public LocalDate getLastWithdraw() {
        return lastWithdraw;
    }

    @Override
    public void withdrawal(double amount) {
        super.withdrawal(amount);
        this.lastWithdraw = LocalDate.now();
    }

    private void setSolde(double solde) {
        if (solde != 0) {
            this.solde = solde;
        }
    }

    private void setOwner(Titulaire owner) {
        this.owner = owner;
    }

    private void setNumber(String number) {
        if (this.number != null && this.number.length() > 0) {
            this.number = number;
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("number", number)
                .add("owner", owner)
                .add("solde", solde)
                .add("lastWithdraw", lastWithdraw)
                .toString();
    }
}
