package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {
    Bank bank;

    int ID = 12345678;
    String TYPE;
    int BALANCE = 0;
    double APR = 1.0;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    @Test
    void bank_has_no_clients_initially() {
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void add_account_with_balance_to_bank() {
        bank.addAccount(new CheckingAccount(ID, BALANCE));
        assertEquals(ID, bank.getAccounts().get(ID).getID());
    }

    @Test
    void add_checking_account_to_bank() {
        bank.addAccount(new CheckingAccount(ID, BALANCE));
        assertEquals("Checking", bank.getAccount(ID).getType());
    }

    @Test
    void add_savings_account_to_bank() {
        bank.addAccount(new SavingsAccount(ID, BALANCE));
        assertEquals("Savings", bank.getAccount(ID).getType());
    }

    @Test
    void add_cd_account_to_bank() {
        bank.addAccount(new CDAccount(ID, APR, BALANCE));
        assertEquals("Cd", bank.getAccount(ID).getType());
    }

    @Test
    void add_two_accounts_to_bank() {
        bank.addAccount(new CheckingAccount(ID, BALANCE));
        bank.addAccount(new CheckingAccount(ID + 1, BALANCE));
        assertEquals(ID + 1, bank.getAccounts().get(ID + 1).getID());
    }

    @Test
    void delete_account() {
        bank.addAccount(new CheckingAccount(ID));
        bank.deleteAccount(ID);
        assertTrue(bank.getAccount(ID) == null);
    }

    /**
     * @Test void withdraw_from_checking_account() {
     * bank.addAccount(new CheckingAccount(ID, BALANCE + 100));
     * bank.reduceAccountBalance(ID, 100);
     * assertEquals(BALANCE, bank.getAccount(ID).getBalance());
     * }
     * @Test void withdraw_from_cd_account() {
     * bank.addAccount(new SavingsAccount(ID, BALANCE + 100));
     * bank.reduceAccountBalance(ID, 100);
     * assertEquals(BALANCE, bank.getAccount(ID).getBalance());
     * }
     */
    @Test
    void withdraw_from_savings_account() {
        bank.addAccount(new SavingsAccount(ID, APR));
        bank.getAccount(ID).setBalance(BALANCE + 100);
        bank.getAccount(ID).withdraw(100);
        assertEquals(BALANCE, bank.getAccount(ID).getBalance());
    }

    @Test
    void deposit_to_account() {
        bank.addAccount(new CheckingAccount(ID));
        bank.getAccount(ID).deposit(250);
        assertEquals(250, bank.getAccount(ID).getBalance());
    }

    @Test
    void deposit_to_checking_account() {
        bank.addAccount(new CheckingAccount(ID, BALANCE));
        bank.getAccount(ID).deposit(100);
        assertEquals(BALANCE + 100, bank.getAccount(ID).getBalance());
    }

    @Test
    void deposit_to_savings_account() {
        bank.addAccount(new SavingsAccount(ID, BALANCE));
        bank.getAccount(ID).deposit(100);
        assertEquals(BALANCE + 100, bank.getAccount(ID).getBalance());
    }

    @Test
    void deposit_to_cd_account() {
        bank.addAccount(new CDAccount(ID, APR, BALANCE));
        bank.getAccount(ID).deposit(100);
        assertEquals(BALANCE + 100, bank.getAccount(ID).getBalance());
    }

    @Test
    void attempt_reduce_below_zero() {
        bank.addAccount(new CheckingAccount(ID));
        bank.getAccount(ID).withdraw(500);
        assertTrue(0 <= bank.getAccount(ID).getBalance());
    }

    @Test
    void attempt_increase_negatively_below_zero() {
        bank.addAccount(new CheckingAccount(ID));
        bank.getAccount(ID).deposit(-500);
        assertTrue(0 <= bank.getAccount(ID).getBalance());
    }

    @Test
    void attempt_initialization_below_zero() {
        bank.addAccount(new CheckingAccount(ID, -5000));
        assertTrue(0 <= bank.getAccount(ID).getBalance());
    }

    @Test
    void account_does_not_exist_in_bank() {
        Account result = bank.getAccount(88888888);
        assertNull(result);
    }

    @Test
    void account_already_exists_in_bank() {
        bank.addAccount(new CheckingAccount(ID));
        Account result = bank.getAccount(ID);
        assertTrue(result != null);
    }

    @Test
    void time_passing_increases_balance() {
        Account addme = new CheckingAccount(ID, 5);
        addme.setBalance(3000);
        bank.addAccount(addme);
        bank.passTime(5);
        assertTrue(addme.getBalance() > 3000);
    }

    @Test
    void higher_apr_returns_higher_balance_after_same_time_with_same_init_balance() {
        Account a1 = new CheckingAccount(ID, 1);
        Account a2 = new CheckingAccount(ID + 1, 7);
        a1.setBalance(3000);
        a2.setBalance(3000);
        bank.addAccount(a1);
        bank.addAccount(a2);
        bank.passTime(12);
        assertTrue(a2.getBalance() > a1.getBalance());
    }

    @Test
    void cd_gets_more_money_over_year_with_same_APR() {
        Account a1 = new CheckingAccount(ID, 1);
        Account a2 = new CDAccount(ID + 1, 1, 3000);
        a1.setBalance(3000);
        bank.addAccount(a1);
        bank.addAccount(a2);
        bank.passTime(12);
        assertTrue(a1.getBalance() < a2.getBalance());
    }

    @Test
    void attempt_two_withdraws_in_one_month_savings() {
        Account a1 = new SavingsAccount(ID, 1);
        bank.addAccount(a1);
        a1.setBalance(5000);
        a1.withdraw(500);
        a1.withdraw(300);
        assertTrue(a1.getBalance() == 4500);
    }

    @Test
    void attempt_two_withdraws_in_two_month_savings() {
        Account a1 = new SavingsAccount(ID, 1);
        bank.addAccount(a1);
        a1.setBalance(5000);
        a1.withdraw(500);
        bank.passTime(1);
        a1.withdraw(300);
        assertTrue(a1.getBalance() < 4500);
    }

    @Test
    void withdraw_all_from_cd_of_age() {
        Account a1 = new SavingsAccount(ID, 1);
        bank.addAccount(a1);
        a1.setBalance(5000);
        bank.passTime(15);
        a1.withdraw(99999);
        assertTrue(a1.getBalance() == 0);
    }

    @Test
    void display_account_rounds_to_two_decimals() {
        Account a1 = new SavingsAccount(ID, 1);
        a1.setBalance(5000.1235678);
        assertTrue((bank.displayAccount(a1).contains("5000.12")) && !(bank.displayAccount(a1).contains("5000.123")));
    }
/**
 @Test void reduce_account_balance_reduces_correctly() {
 Account a1 = new SavingsAccount(ID, 1);
 bank.addAccount(a1);
 a1.setBalance(5000);
 bank.reduceAccountBalance(ID, 500);
 assertEquals(a1.getBalance(), 4500);
 }

 @Test void increase_account_balance_increases_correctly() {
 Account a1 = new SavingsAccount(ID, 1);
 bank.addAccount(a1);
 a1.setBalance(5000);
 bank.increaseAccountBalance(ID, 500);
 assertEquals(a1.getBalance(), 5500);
 }
 */
}
