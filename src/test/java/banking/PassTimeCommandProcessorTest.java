package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassTimeCommandProcessorTest {
    PassTimeCommandProcessor ptcp;
    Bank bank = new Bank();

    @BeforeEach
    void setup() {
        ptcp = new PassTimeCommandProcessor(bank);
    }

    @Test
    void time_passes_on_pass_time_command_issue() {
        CheckingAccount ca = new CheckingAccount(12345678, .05);
        SavingsAccount sa = new SavingsAccount(23456789, .05);
        bank.addAccount(ca);
        bank.addAccount(sa);
        ca.deposit(500);
        sa.deposit(500);
        ptcp.issue_command(new PassCommand("pass 12"));
        assertTrue(ca.getBalance() > 500 && sa.getBalance() > 500);
    }

    @Test
    void empty_account_gets_closed_after_time_passes() {
        CheckingAccount ca = new CheckingAccount(12345678, .05);
        bank.addAccount(ca);
        ptcp.issue_command(new PassCommand("pass 1"));
        assertTrue(bank.getAccounts().size() == 0);
    }

    @Test
    void account_below_100_loses_25_per_month() {
        CheckingAccount ca = new CheckingAccount(12345678, 0);
        bank.addAccount(ca);
        ca.setBalance(75);
        ptcp.issue_command(new PassCommand("pass 1"));
        SavingsAccount sa = new SavingsAccount(23456789, 0);
        bank.addAccount(sa);
        sa.setBalance(75);
        ptcp.issue_command(new PassCommand("pass 1"));
        assertTrue(ca.getBalance() == 25 && sa.getBalance() == 50);
    }
}
