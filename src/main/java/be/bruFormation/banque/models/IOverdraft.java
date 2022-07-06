package be.bruFormation.banque.models;
@FunctionalInterface
public interface IOverdraft {
    void overdraftTriger(Account account); // lancement d'un triger pour un event
}
