package be.bruFormation.banque.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SaveAccountTest {
    Holder holder = new Holder("Rutabare","Ricardo");
    private Account save = new SaveAccount("123",holder,1000);

    @BeforeEach
    void setUp() {
    }

    @Test
    void withdrawal() {
    }
}