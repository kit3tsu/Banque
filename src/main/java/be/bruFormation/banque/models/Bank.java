package be.bruFormation.banque.models;


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
    private Map<String, CompteCourant> clients = new HashMap<String, CompteCourant>();

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

    public Map.Entry<String, CompteCourant>[] getClients() {
        Map<String, CompteCourant> copy = new HashMap<String, CompteCourant>();
        return this.clients.entrySet().toArray(new Map.Entry[0]);
    }

    private void add(CompteCourant account) {
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
    public Optional<CompteCourant> get(String number) {
        if (containAccount(number)) {
            return Optional.ofNullable(clients.get(number));
        }
        return null;
    }

    private boolean containAccount(String number) {
        return clients.containsKey(number);
    }
}
