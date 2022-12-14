package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountTest {

    public static final int ID = 12345678;
    public static final String TYPE = "Checking";
    Account account;
    Bank bank;

    @BeforeEach
    void setUp() {
        account = new CheckingAccount(ID);
        bank = new Bank();
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
    void attempt_withdraw_none_withdraws_none() {
        account.setBalance(500);
        account.withdraw(0);
        assertEquals(account.getBalance(), 500);
    }

    @Test
    void attempt_negative_setbalance_sets_zero_instead() {
        account.setBalance(-1);
        assertEquals(account.getBalance(), 0);
    }

    @Test
    void attempt_zero_setbalance_sets_zero() {
        account.setBalance(0);
        assertEquals(account.getBalance(), 0);
    }

    @Test
    void attempt_one_setbalance_sets_one() {
        account.setBalance(1);
        assertEquals(account.getBalance(), 1);
    }

    @Test
    void attempt_withdraw_from_zero_withdraws_zero() {
        account.setBalance(0);
        account.withdraw(500);
        assertEquals(account.getBalance(), 0);
    }

    @Test
    void attempt_withdraw_excess_of_balance_empties_account() {
        account.setBalance(1);
        account.withdraw(10);
        assertEquals(account.getBalance(), 0);
    }

    @Test
    void displayBalance_rounds_to_two_decimals() {
        Account a1 = new SavingsAccount(ID, 1);
        a1.setBalance(5000.1235678);
        assertTrue(a1.displayBalance().equals("5000.12"));
    }

    @Test
    void displayAPR_rounds_to_two_decimals() {
        Account a1 = new SavingsAccount(ID, 1.234567);
        assertTrue(a1.displayAPR().equals("1.23"));
    }

    @Test
    void get_balance_below_zero_sets_zero() {
        Account a1 = new SavingsAccount(ID, 1.25);
        a1.setBalance(-1);
        assertEquals(a1.getBalance(), 0);
    }

    @Test
    void get_balance_at_zero_stays_zero() {
        Account a1 = new SavingsAccount(ID, 1.25);
        a1.setBalance(0);
        assertEquals(a1.getBalance(), 0);
    }

    @Test
    void get_balance_at_one_stays_one() {
        Account a1 = new SavingsAccount(ID, 1.25);
        a1.setBalance(1);
        assertEquals(a1.getBalance(), 1);
    }

    @Test
    void transaction_history_outputs_correctly() {
        CommandProcessor processor = new CommandProcessor(bank);
        Account checking1 = new CheckingAccount(ID, .1);
        Account checking2 = new CheckingAccount(ID + 1, .1);
        bank.addAccount(checking1);
        bank.addAccount(checking2);
        checking1.setBalance(1000);
        checking2.setBalance(500);
        processor.issue_command(new TransferCommand("transfer 12345678 12345679 300"));
        List<String> checking1_history = checking1.transactionHistory();
        List<String> checking2_history = checking2.transactionHistory();
        assertEquals(checking1_history, checking2_history);
    }

}