package be.bruFormation.banque.util;

import be.bruFormation.banque.Main;

import java.util.Objects;

public class DBConnection {
    public DBConnection() {
        String DB_PATH = Objects.requireNonNull(Main.class.getClassLoader().getResource("banque.sqlite3")).toString();
    }
}
