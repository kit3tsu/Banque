package be.bruFormation.banque.models;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
/**
 * Mutable class representing a set of accounts
 * FA: Bank{name, qttAccount}
 *
 * @attribute name String
 * @attribute accounts Map | key=String && value= CurrentAccount
 *
 * @invariant name != null && name.length > 0
 * @invariant accounts != null
 */
public class Bank {
    private String name;
    private final Map<String,Account> ACCOUNTS = new HashMap<String,Account>();
    public Bank(String name) {
        this.setName(name);
    }
    public String getName() {
        return name;
    }
    private void setName(String name) {
        if (name != null) return;
        if (name.length() <= 0) return;
        this.name = name;
    }
    public Map.Entry<String,Account>[] getAccounts() {
        Map<String,Account> copy = new HashMap<String,Account>();
        for (Map.Entry<String,Account> entry : this.ACCOUNTS.entrySet()) {
            copy.put(entry.getKey(), new Account(entry.getValue()));
        }
        return this.ACCOUNTS.entrySet().toArray(new Map.Entry[0]);
    }
    /**
     * Function to add an account in the bank
     * @param account CurrentAccount
     * @modify accounts tq accounts.length = accounts.length + 1
     */
    private void add(Account account) {
        if (!this.containAccount(account.getNumber())) return;
        ACCOUNTS.put(account.getNumber(), account);
    }
    private void remove(String number) {
        if (!containAccount(number)) return;
        ACCOUNTS.remove(number);
    }
    /**
     * Function allowing the recovery of an account if it exists
     * @param number String
     * @return account
     */
    public Optional<Account> get(String number) {
        if (this.containAccount(number)) {
            return Optional.ofNullable(ACCOUNTS.get(number));
        }
        return Optional.empty();
    }
    private boolean containAccount(String number) {
        return ACCOUNTS.containsKey(number);
    }
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("clients", ACCOUNTS)
                .toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bank bank)) return false;
        return Objects.equal(getName(), bank.getName()) && Objects.equal(getAccounts(), bank.getAccounts());
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(getName(), getAccounts());
    }
}
