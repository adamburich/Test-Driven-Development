package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferCommandProcessorTest {
    TransferCommandProcessor tcp;
    Bank bank = new Bank();

    @BeforeEach
    void setup() {
        tcp = new TransferCommandProcessor(bank);
    }

    @Test
    void transfer_event_occurs_on_command_issue() {
        CheckingAccount ca = new CheckingAccount(12345678, .05);
        SavingsAccount sa = new SavingsAccount(23456789, .05);
        bank.addAccount(ca);
        bank.addAccount(sa);
        ca.deposit(500);
        sa.deposit(500);
        tcp.issue_command(new TransferCommand("transfer 12345678 23456789 400"));
        assertEquals(sa.getBalance(), 900);
    }
}
