package be.bruFormation.banque.utils;

public interface Observable {
    void addObserver();
    void removeObserver();
    void notifyObservers();
}
