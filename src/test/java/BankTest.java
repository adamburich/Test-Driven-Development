import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankTest {
    Bank bank;

    int ID = 12345678;
    String TYPE;
    int BALANCE = 0;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    @Test
    void bank_has_no_clients_initially() {
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void add_empty_account_to_bank() {
        bank.addAccount(new Account(ID));
        assertEquals(0, bank.getAccount(ID).getBalance());
        assertEquals(null, bank.getAccount(ID).getType());
    }

    @Test
    void add_account_with_balance_to_bank() {
        bank.addAccount(new Account(ID, BALANCE));
        assertEquals(ID, bank.getAccounts().get(ID).getID());
    }

    @Test
    void add_checking_account_to_bank() {
        bank.addAccount(new CheckingAccount(ID, BALANCE));
        assertEquals("CHECKING", bank.getAccount(ID).getType());
    }

    @Test
    void add_savings_account_to_bank() {
        bank.addAccount(new SavingsAccount(ID, BALANCE));
        assertEquals("SAVINGS", bank.getAccount(ID).getType());
    }

    @Test
    void add_cd_account_to_bank() {
        bank.addAccount(new CDAccount(ID, BALANCE));
        assertEquals("CD", bank.getAccount(ID).getType());
    }

    @Test
    void add_two_accounts_to_bank() {
        bank.addAccount(new Account(ID, BALANCE));
        bank.addAccount(new Account(ID + 1, BALANCE));
        assertEquals(ID + 1, bank.getAccounts().get(ID + 1).getID());
    }

    @Test
    void delete_account() {
        bank.addAccount(new Account(ID));
        bank.deleteAccount(ID);
        assertTrue(bank.getAccount(ID) == null);
    }

    @Test
    void withdraw_from_account() {
        bank.addAccount(new Account(ID, 500));
        bank.reduceAccountBalance(ID, 250);
        assertEquals(250, bank.getAccount(ID).getBalance());
    }

    @Test
    void withdraw_from_checking_account() {
        bank.addAccount(new CheckingAccount(ID, BALANCE + 100));
        bank.reduceAccountBalance(ID, 100);
        assertEquals(BALANCE, bank.getAccount(ID).getBalance());
    }

    @Test
    void withdraw_from_cd_account() {
        bank.addAccount(new SavingsAccount(ID, BALANCE + 100));
        bank.reduceAccountBalance(ID, 100);
        assertEquals(BALANCE, bank.getAccount(ID).getBalance());
    }

    @Test
    void withdraw_from_savings_account() {
        bank.addAccount(new CDAccount(ID, BALANCE + 100));
        bank.reduceAccountBalance(ID, 100);
        assertEquals(BALANCE, bank.getAccount(ID).getBalance());
    }

    @Test
    void deposit_to_account() {
        bank.addAccount(new Account(ID));
        bank.increaseAccountBalance(ID, 250);
        assertEquals(250, bank.getAccount(ID).getBalance());
    }

    @Test
    void deposit_to_checking_account() {
        bank.addAccount(new CheckingAccount(ID, BALANCE));
        bank.increaseAccountBalance(ID, 100);
        assertEquals(BALANCE + 100, bank.getAccount(ID).getBalance());
    }

    @Test
    void deposit_to_savings_account() {
        bank.addAccount(new SavingsAccount(ID, BALANCE));
        bank.increaseAccountBalance(ID, 100);
        assertEquals(BALANCE + 100, bank.getAccount(ID).getBalance());
    }

    @Test
    void deposit_to_cd_account() {
        bank.addAccount(new CDAccount(ID, BALANCE));
        bank.increaseAccountBalance(ID, 100);
        assertEquals(BALANCE + 100, bank.getAccount(ID).getBalance());
    }

    @Test
    void attempt_reduce_below_zero() {
        bank.addAccount(new Account(ID));
        bank.getAccount(ID).reduceBalance(500);
        assertTrue(0 <= bank.getAccount(ID).getBalance());
    }

    @Test
    void attempt_increase_negatively_below_zero() {
        bank.addAccount(new Account(ID));
        bank.getAccount(ID).addBalance(-500);
        assertTrue(0 <= bank.getAccount(ID).getBalance());
    }

    @Test
    void attempt_initialization_below_zero() {
        bank.addAccount(new Account(ID, -5000));
        assertTrue(0 <= bank.getAccount(ID).getBalance());
    }
}