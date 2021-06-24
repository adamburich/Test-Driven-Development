package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositCommandProcessorTest {
    DepositCommandProcessor dcp;
    Bank bank = new Bank();

    @BeforeEach
    void setup() {
        dcp = new DepositCommandProcessor(bank);
    }

    @Test
    void deposit_event_occurs_on_command_issue() {
        CheckingAccount ca = new CheckingAccount(12345678, .05);
        bank.addAccount(ca);
        dcp.issue_command(new DepositCommand("deposit 12345678 900"));
        assertEquals(ca.getBalance(), 900);
    }
}
