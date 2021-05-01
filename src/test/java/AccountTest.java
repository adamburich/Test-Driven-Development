import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    public static final int ID = 12345678;
    public static final String TYPE = "CHECKING";
    Account account;

    @BeforeEach
    void setUp() {
        account = new CheckingAccount(ID);
    }


    @Test
    void account_has_type() {
        account = account.assignType(TYPE);
        assertEquals(TYPE, account.getType());
    }

    @Test
    void account_assigned_savings_is_savings_type() {
        account = account.assignType("SAVINGS");
        assertEquals(account.getType(), "SAVINGS");
    }

    @Test
    void account_assigned_checking_is_checking_type() {
        account = account.assignType("CHECKING");
        assertEquals(account.getType(), "CHECKING");
    }

    @Test
    void account_assigned_cd_is_cd_type() {
        account = account.assignType("CD");
        assertEquals(account.getType(), "CD");
    }

    @Test
    void savings_account_is_savings_type() {
        account = new SavingsAccount(ID, 0);
        assertEquals(account.getType(), "SAVINGS");
    }

    @Test
    void checking_account_is_checking_type() {
        account = new CheckingAccount(ID, 0);
        assertEquals(account.getType(), "CHECKING");
    }

    @Test
    void cd_account_is_cd_type() {
        account = new CDAccount(ID, 0);
        assertEquals(account.getType(), "CD");
    }

    @Test
    void balance_above_zero() {
        account.setBalance(500);
        assertTrue(account.getBalance() > 0);
    }

    @Test
    void deposit_cash() {
        int deposit = 750;
        int initial = account.getBalance();
        account.addBalance(deposit);
        assertEquals(initial + 750, account.getBalance());
    }

    @Test
    void deposit_to_checking() {
        int deposit = 750;
        account.assignType("CHECKING");
        account.setBalance(0);
        account.addBalance(deposit);
        assertEquals(deposit, account.getBalance());
    }

    @Test
    void deposit_to_savings() {
        int deposit = 750;
        account.assignType("SAVINGS");
        account.setBalance(0);
        account.addBalance(deposit);
        assertEquals(deposit, account.getBalance());
    }

    @Test
    void deposit_to_cd() {
        int deposit = 750;
        account.assignType("CD");
        account.setBalance(0);
        account.addBalance(deposit);
        assertEquals(deposit, account.getBalance());
    }

    @Test
    void withdraw_cash() {
        int withdrawal = 500;
        account.setBalance(750);
        int initial = 750;
        account.reduceBalance(withdrawal);
        assertEquals(initial - withdrawal, account.getBalance());
    }

    @Test
    void withdraw_from_savings() {
        int withdrawal = 500;
        account.assignType("SAVINGS");
        account.setBalance(1000);
        account.reduceBalance(withdrawal);
        assertEquals(500, account.getBalance());
    }

    @Test
    void withdraw_from_checking() {
        int withdrawal = 500;
        account.assignType("CHECKING");
        account.setBalance(1000);
        account.reduceBalance(withdrawal);
        assertEquals(500, account.getBalance());
    }

    @Test
    void withdraw_from_cd() {
        int withdrawal = 500;
        account.assignType("CD");
        account.setBalance(1000);
        account.reduceBalance(withdrawal);
        assertEquals(500, account.getBalance());
    }

    @Test
    void attempt_overdraft() {
        int withdrawal = 500;
        account.setBalance(250);
        int initial = 250;
        account.reduceBalance(withdrawal);
        assertEquals(initial, account.getBalance());
    }

}