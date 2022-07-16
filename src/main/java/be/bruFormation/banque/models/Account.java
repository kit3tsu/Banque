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
public abstract class Account {
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
    //region Getter Setter
    public String getNumber() {
        return number;
    }
    private void setNumber(String number) {
//        if (number.length() == 19) {
//            this.number = number;
//        }
        this.number = number;
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
    //endregion
    public boolean numberHasChanged(String number){
        String oldNumber = this.getNumber();
        this.setNumber(number);
        return !(oldNumber.equals(this.getNumber()));
    }
    public void generateNumber(String bankCode){
        String iban ;
        String countryCode = "BE";
        String bban =getBBan(bankCode);
        String controlKey = generateControlKey(countryCode,bban);
        iban = countryCode + controlKey + bban;
       iban = splitIban(iban);
        this.setNumber(iban);
    }

    private String splitIban(String iban) {
        iban =iban.replaceAll("(.{4})", "$1 ");
        return iban.substring(0,iban.length()-1);
    }

    private String getBBan(String bankCode){
        //String bban = Integer.toString(this.hashCode()) + bankCode ;
        //return bban;
        return "510007547" + bankCode;
    }

    public String generateControlKey(String countryCode, String bban) {
        String countryCodeNumber = "";
        String controlKey = bban;
        for (int i = 0  ;  i < countryCode.length(); i++) {
            char c = countryCode.charAt(i);
            int posCesarNine = ((int) c - (int) 'A') + 10;
            countryCodeNumber += Integer.toString(posCesarNine) ;
        }
        controlKey += countryCodeNumber +"00";
        long control = Long.parseLong(controlKey);
        control %=97;
        control = 98 - control;
        if(control >= 10){
            return Long.toString(control);
        }else {
            return "0"+Long.toString(control);
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
    //region Override methode from Class Object
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
    //endregion
}
