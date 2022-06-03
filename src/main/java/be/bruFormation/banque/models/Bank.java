package be.bruFormation.banque.models;


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @attribute name String
 * @attribute client Map<Titulaire,List<CompteCourant>>
 * @invariant name != null && nom.length > 0 en Lecture seul
 * @invariant clients != null
 */
public class Bank {
    private String name;
    private Map<String, CurrentAccount> clients = new HashMap<String, CurrentAccount>();

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
    public Map.Entry<String, CurrentAccount>[] getClients() {
        Map<String, CurrentAccount> copy = new HashMap<String, CurrentAccount>();
        for (Map.Entry<String, CurrentAccount> entry : this.clients.entrySet()) {
            copy.put(entry.getKey(), new CurrentAccount(entry.getValue()));
        }
        return this.clients.entrySet().toArray(new Map.Entry[0]);
    }

    private void add(CurrentAccount account) {
        if (!this.containAccount(account.getNumber())) return;
        clients.put(account.getNumber(), account);
    }

    private void remove(String number) {
        if (!containAccount(number)) return;
        clients.remove(number);
    }

    /**
     * Methode permettant la recupération d'un compte si il existe
     *
     * @param number
     * @return compte tq existe un compte avec le numero number si non null
     */
    public Optional<CurrentAccount> get(String number) {
        if (containAccount(number)) {
            return Optional.ofNullable(clients.get(number));
        }
        return null;
    }

    private boolean containAccount(String number) {
        return clients.containsKey(number);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("clients", clients)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bank)) return false;
        Bank bank = (Bank) o;
        return Objects.equal(getName(), bank.getName()) && Objects.equal(getClients(), bank.getClients());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName(), getClients());
    }
}
