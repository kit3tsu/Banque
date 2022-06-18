package be.bruFormation.banque;

import be.bruFormation.banque.Repository.AccountRepository;
import be.bruFormation.banque.Repository.HolderRepository;
import be.bruFormation.banque.models.Account;
import be.bruFormation.banque.models.CurrentAccount;
import be.bruFormation.banque.models.SaveAccount;
import be.bruFormation.banque.utils.Config;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    //public static final String DB_PATH = Objects.requireNonNull(Main.class.getClassLoader().getResource("banque.sqlite3")).toString();

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        HolderRepository holderRepository = new HolderRepository();
        AccountRepository accountRepository = new AccountRepository();
        List<Account> accountList = accountRepository.findAllAccount();
        System.out.println(accountList);
    }

}
