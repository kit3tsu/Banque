package be.bruFormation.banque.models;

import com.google.common.base.MoreObjects;

import java.time.LocalDate;

class SaveAccount extends Account {
    private LocalDate lastWithdraw;


    public SaveAccount(String number, Holder holder, double solde) {
        super(number,holder,solde);
    }

    public SaveAccount(SaveAccount account) {
        super(account);
        this.lastWithdraw = account.getLastWithdraw();
    }

    public LocalDate getLastWithdraw() {
        return lastWithdraw;
    }

    @Override
    public void withdrawal(double amount) {
        super.withdrawal(amount);
        this.lastWithdraw = LocalDate.now();
    }
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("lastWithdraw", lastWithdraw)
                .toString();
    }
}
