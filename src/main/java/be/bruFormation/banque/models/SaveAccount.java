package be.bruFormation.banque.models;

import com.google.common.base.MoreObjects;

import java.time.LocalDate;
/**
 * Mutable class representing an account that can go negative
 * FA: SaveAccount{number, balance, dateLastWithdrawn}
 *
 * @attribute dateLastWithdrawn double
 *
 * @see be.bruFormation.banque.models.Account
 */

public class SaveAccount extends Account {
    private LocalDate dateLastWithdrawn;
    public SaveAccount(String number, Holder holder, double solde) {
        super(number,holder,solde);
    }
    public SaveAccount(SaveAccount account) {
        super(account);
        this.dateLastWithdrawn = account.getLastWithdraw();
    }
    public LocalDate getLastWithdraw() {
        return dateLastWithdrawn;
    }
    /**
     * @see Account#withdrawal 
     */
    @Override
    public void withdrawal(double amount) {
        super.withdrawal(amount);
        this.dateLastWithdrawn = LocalDate.now();
    }
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("lastWithdraw", dateLastWithdrawn)
                .toString();
    }
}
