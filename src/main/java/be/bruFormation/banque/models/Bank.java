package be.bruFormation.banque.models;

import be.bruFormation.banque.utils.Observer;
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
public class Bank implements Observer {
    private String name;
    private String swiftCode;
    private String bankCode;
    private final Map<String,Account> ACCOUNTS = new HashMap<String,Account>();
    public Bank(String name) {
        this.setName(name);
        generateBankCode();
        this.swiftCode = generateSwiftCode();
    }
    public SaveAccount creatSaveAccount(Holder holder, double sold){
        return new SaveAccount(this.bankCode, holder,sold);
    }
    public CurrentAccount creatCurrentAccount(Holder holder, double sold){
        return new CurrentAccount(this.bankCode, holder,sold);
    }
    // TODO add copy constructor
    private String generateSwiftCode() {
        String countryCode = "BE";
        String locationCode = "BR";
        String agencyCode = Integer.toString(this.hashCode()).substring(0,3);
        return bankCode+countryCode+locationCode+agencyCode;
    }
    public String getName() {
        return name;
    }
    private void setName(String name) {
        if (name != null) return;
        if (name.length() <= 0) return;
        this.name = name;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void generateBankCode() {
        this.bankCode = Integer.toString(this.hashCode()).substring(3,7);
    }

    public Map.Entry<String,Account>[] getAccounts() {
        Map<String,Account> copy = new HashMap<String,Account>();
        for (Map.Entry<String,Account> entry : this.ACCOUNTS.entrySet()) {
            if(entry.getValue() instanceof CurrentAccount){
                copy.put(entry.getKey(), new CurrentAccount((CurrentAccount) entry.getValue()));
            } else if (entry.getValue() instanceof SaveAccount) {
                copy.put(entry.getKey(), new SaveAccount((SaveAccount) entry.getValue()));
            }
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

    public String getSwiftCode() {
        return  this.swiftCode;
    }

    @Override
    public void update(Object o) {
        if(o instanceof Account){
            Account account = (Account) o;
            // TODO notify holder that his account have ...
        }
    }
}
