package be.bruFormation.banque.models;

import com.google.common.base.Objects;

/**
 * Mutable class representing an account that can go negative
 * FA: Account{number, solde}
 *
 * @attribute <b>number</b> {@link String String} => Format IBAN BEXX XXXX XXXX XXXX
 * @attribute <b>solde</b> {@link Double double}
 * @attribute <b>holder</b> {@link Holder Holder}
 *
 * @invariant number != null and numero.length = 19
 * @invariant holder != null
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
//        if (number.length() == 19) {
//            this.number = number;
//        }
        this.number = number;
    }
    public boolean numberHasChanged(String number){
        String oldNumber = this.getNumber();
        this.setNumber(number);
        return !(oldNumber.equals(this.getNumber()));
    }
    public void generateNumber(int bankCode){
        String iban ;
        String countryCode = "BE";
        long bban =getBBan(bankCode);
        String controlKey = generateControlKey(countryCode,bban);
        iban = countryCode + controlKey + Long.toString(bban);
        this.setNumber(iban);
    }

    public long getBBan(int bankCode){
        long bban = (long) (this.hashCode() + (bankCode * Math.pow(10,9)));
        return bban;
    }

    public String generateControlKey(String countryCode, long bban) {
        int countryCodeNumber = 0;
        int cpt = countryCode.length() ;
        int space = (int) Math.pow(100,cpt);
        long controlKey = bban * space;
        for (int i = 0  ;  i < countryCode.length(); i++) {
            char c = countryCode.charAt(i);
            int posCesarNine = ((int) c - (int) 'A') + 10;
            int offset =(int) Math.pow(100,cpt-1);
            countryCodeNumber += posCesarNine *offset;
            cpt --;
        }
        controlKey +=countryCodeNumber;
        controlKey *=100;
        controlKey %=97;
        controlKey = 98 - controlKey;

        if(controlKey >= 10){
            return Long.toString(controlKey);
        }else {
            return "0"+Long.toString(controlKey);
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
    /**
     * Procedure for withdrawing an amount from the account balance
     * @param amount amount > 0
     */
    public void withdrawal(double amount) {
        if (amount <= 0) return;
        if (this.solde - amount < 0) return;
        this.solde -= amount;
    }
    /**
     * Procedure for depositing an amount to the account balance
     * @param amount amount > 0
     */
    public void deposit(double amount) {
        if (amount <= 0) return;
        this.solde += amount;
    }
    public double sumAccount(Account secondAccount){
        return  this.solde + secondAccount.getSolde();
    }
//TODO generte IBAN
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("number= ").append(number).append(", ");
        builder.append("solde= ").append(solde).append(", ");

        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Double.compare(account.getSolde(), getSolde()) == 0 && Objects.equal(getNumber(), account.getNumber()) && Objects.equal(getHolder(), account.getHolder());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getNumber(), getHolder(), getSolde());
    }
}
