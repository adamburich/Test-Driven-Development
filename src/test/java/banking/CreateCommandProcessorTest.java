package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class CreateCommandProcessorTest {
    CreateCommandProcessor ccp;
    Bank bank = new Bank();

    @BeforeEach
    void setup() {
        ccp = new CreateCommandProcessor(bank);
    }

    @Test
    void create_savings_event_occurs_on_command_issue() {
        ccp.issue_command(new CreateCommand("create savings 12345678 1"));
        assertFalse(bank.getAccount(12345678) == null);
    }

    @Test
    void create_checking_event_occurs_on_command_issue() {
        ccp.issue_command(new CreateCommand("create checking 87654321 1"));
        assertFalse(bank.getAccount(87654321) == null);
    }

    @Test
    void create_cd_event_occurs_on_command_issue() {
        ccp.issue_command(new CreateCommand("create cd 23456789 500 1"));
        assertFalse(bank.getAccount(23456789) == null);
    }
}
