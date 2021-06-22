package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountTest {

    public static final int ID = 12345678;
    public static final String TYPE = "Checking";
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
        account = account.assignType("Savings");
        assertEquals(account.getType(), "Savings");
    }

    @Test
    void account_assigned_checking_is_checking_type() {
        account = account.assignType("Checking");
        assertEquals(account.getType(), "Checking");
    }

    @Test
    void account_assigned_cd_is_cd_type() {
        account = account.assignType("Cd");
        assertEquals(account.getType(), "Cd");
    }

    @Test
    void savings_account_is_savings_type() {
        account = new SavingsAccount(ID, 0);
        assertEquals(account.getType(), "Savings");
    }

    @Test
    void checking_account_is_checking_type() {
        account = new CheckingAccount(ID, 0);
        assertEquals(account.getType(), "Checking");
    }

    @Test
    void cd_account_is_cd_type() {
        account = new CDAccount(ID, 0.0, 0);
        assertEquals(account.getType(), "Cd");
    }

    @Test
    void balance_above_zero() {
        account.setBalance(500);
        assertTrue(account.getBalance() > 0);
    }

    @Test
    void deposit_cash() {
        int deposit = 750;
        double initial = account.getBalance();
        account.deposit(deposit);
        assertEquals(initial + 750, account.getBalance());
    }

    @Test
    void deposit_to_checking() {
        int deposit = 750;
        account.assignType("Checking");
        account.setBalance(0);
        account.deposit(deposit);
        assertEquals(deposit, account.getBalance());
    }

    @Test
    void deposit_to_savings() {
        int deposit = 750;
        account.assignType("Savings");
        account.setBalance(0);
        account.deposit(deposit);
        assertEquals(deposit, account.getBalance());
    }

    @Test
    void deposit_to_cd() {
        int deposit = 750;
        account.assignType("Cd");
        account.setBalance(0);
        account.deposit(deposit);
        assertEquals(deposit, account.getBalance());
    }

    @Test
    void withdraw_cash() {
        int withdrawal = 500;
        account.setBalance(750);
        int initial = 750;
        account.withdraw(withdrawal);
        assertEquals(initial - withdrawal, account.getBalance());
    }

    @Test
    void withdraw_from_savings() {
        int withdrawal = 500;
        account.assignType("Savings");
        account.setBalance(1000);
        account.withdraw(withdrawal);
        assertEquals(500, account.getBalance());
    }

    @Test
    void withdraw_from_checking() {
        int withdrawal = 500;
        account.assignType("Checking");
        account.setBalance(1000);
        account.withdraw(withdrawal);
        assertEquals(500, account.getBalance());
    }

    @Test
    void withdraw_from_cd() {
        int withdrawal = 500;
        account.assignType("Cd");
        account.setBalance(1000);
        account.withdraw(withdrawal);
        assertEquals(500, account.getBalance());
    }

    @Test
    void attempt_overdraft() {
        int withdrawal = 500;
        account.setBalance(250);
        account.withdraw(withdrawal);
        assertEquals(0, account.getBalance());
    }

    @Test
    void attempt_set_below_zero_results_zero() {
        account.setBalance(-1);
        assertEquals(0, account.getBalance());
    }

    @Test
    void attempt_set_zero_results_zero() {
        account.setBalance(0);
        assertEquals(0, account.getBalance());
    }

    @Test
    void attempt_set_balance_one() {
        account.setBalance(1);
        assertEquals(1, account.getBalance());
    }

    @Test
    void attempt_withdraw_one_withdraws_one() {
        account.setBalance(500);
        account.withdraw(1);
        assertEquals(account.getBalance(), 499);
    }

    @Test
    void attempt_withdraw_negative_one_withdraws_none() {
        account.setBalance(500);
        account.withdraw(1);
        assertEquals(account.getBalance(), 499);
    }

    @Test
    void attempt_withdraw_none_withdraws_none() {
        account.setBalance(500);
        account.withdraw(0);
        assertEquals(account.getBalance(), 500);
    }

}