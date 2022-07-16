package be.bruFormation.banque.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class AccountTest {

    Account current;
    Account save;
    @Mock
    Account fake;
    Holder holder;
    String numberC ;
    String numberS ;
    double soldC ;
    double soldS ;


    @BeforeEach
    void setUp() {
        holder = new Holder("Ricardo","Rutabare");
        numberC = "BE1234 1234 1234 1234";
        numberS = "BE1234 5678 9123 4567";
        soldC =  100;
        soldS = 1000;
        current = new CurrentAccount(numberC,holder, soldC);
        save = new SaveAccount(numberS,holder,soldS);
    }
//    @BeforeEach
//    void fakeAccount(){
//        fake = new CurrentAccount("666",holder,6666);
//        Mockito.lenient().when(fake.getBBan(510)).thenReturn(510_007_547_061);
//    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void testWithdrawalAll(){
        current.withdrawal(soldC);
        assertEquals(0,current.getSolde());
    }
    @Test
    void testWithdrawalMoreThanSolde(){
        current.withdrawal(soldC + 1);
        assertEquals(soldC,current.getSolde());
    }
    @Test
    void testWithdrawal(){
        current.withdrawal(soldC - 1);
        assertEquals(1,current.getSolde());
    }

    @Test
    void testDeposit(){
        current.deposit(soldC);
        assertEquals(200,current.getSolde());
    }
    @Test
    void testDepositNegative(){
        current.deposit(- 1);
        assertEquals(soldC,current.getSolde());
    }

    @Test
    void testGenerateNumber(){
        current.generateNumber("061");
        assertEquals("BE62 5100 0754 7061",current.getNumber());
    }

}