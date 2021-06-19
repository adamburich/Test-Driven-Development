package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WithdrawalCommandProcessorTest {
    WithdrawalCommandProcessor wcp;
    Bank bank = new Bank();

    @BeforeEach
    void setup() {
        wcp = new WithdrawalCommandProcessor(bank);
    }

    @Test
    void withdrawal_event_occurs_on_command_issue_checking() {
        CheckingAccount ca = new CheckingAccount(12345678, .05);
        bank.addAccount(ca);
        ca.deposit(500);
        wcp.issue_command(new WithdrawalCommand("withdraw 12345678 250"));
        assertEquals(ca.getBalance(), 250);
    }

    @Test
    void withdrawal_event_occurs_on_command_issue_savings() {
        SavingsAccount sa = new SavingsAccount(12345678, .05);
        bank.addAccount(sa);
        sa.deposit(500);
        wcp.issue_command(new WithdrawalCommand("withdraw 12345678 250"));
        assertEquals(sa.getBalance(), 250);
    }

    @Test
    void withdrawal_event_occurs_on_command_issue_CD() {
        CDAccount cd = new CDAccount(12345678, .05, 500);
        bank.addAccount(cd);
        bank.passTime(12);
        wcp.issue_command(new WithdrawalCommand("withdraw 12345678 5000"));
        System.out.println(cd.getBalance());
        assertEquals(cd.getBalance(), 0);
    }
}
