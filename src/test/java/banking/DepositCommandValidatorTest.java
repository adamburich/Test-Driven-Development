package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepositCommandValidatorTest {
    DepositCommandValidator val;
    SavingsAccount sa;
    CheckingAccount ca;
    Bank bank = new Bank();

    @BeforeEach
    void setup() {
        val = new DepositCommandValidator(bank);
        ca = new CheckingAccount(87654321, .05);
        sa = new SavingsAccount(23456789, .05);
        bank.addAccount(sa);
        bank.addAccount(ca);
    }

    @Test
    void deposit_zero_to_cd_is_valid() {
        DepositCommand dc = new DepositCommand("deposit 87654321 0");
        assertTrue(val.validate(dc));
    }

    @Test
    void deposit_zero_to_savings_is_valid() {
        DepositCommand next = new DepositCommand("deposit 23456789 0");
        assertTrue(val.validate(next));
    }

    @Test
    void deposit_negative_one_to_checking_invalid() {
        DepositCommand dc = new DepositCommand("deposit 87654321 -1");
        assertFalse(val.validate(dc));
    }

    @Test
    void deposit_negative_one_to_savings_invalid() {
        DepositCommand next = new DepositCommand("deposit 23456789 -1");
        assertFalse(val.validate(next));
    }

    @Test
    void deposit_one_to_checking_valid() {
        DepositCommand dc = new DepositCommand("deposit 87654321 1");
        assertTrue(val.validate(dc));
    }

    @Test
    void deposit_one_to_savings_valid() {
        DepositCommand next = new DepositCommand("deposit 23456789 1");
        assertTrue(val.validate(next));
    }

    @Test
    void deposit_checking_under_cap_is_valid() {
        DepositCommand dc = new DepositCommand("deposit 87654321 999");
        assertTrue(val.validate(dc));
    }

    @Test
    void deposit_savings_under_cap_is_valid() {
        DepositCommand next = new DepositCommand("deposit 23456789 2499");
        assertTrue(val.validate(next));
    }

    @Test
    void deposit_checking_cap_is_valid() {
        DepositCommand dc = new DepositCommand("deposit 87654321 1000");
        assertTrue(val.validate(dc));
    }

    @Test
    void deposit_savings_cap_is_valid() {
        DepositCommand next = new DepositCommand("deposit 23456789 2500");
        assertTrue(val.validate(next));
    }

    @Test
    void deposit_checking_over_cap_is_invalid() {
        DepositCommand dc = new DepositCommand("deposit 87654321 1001");
        assertFalse(val.validate(dc));
    }

    @Test
    void deposit_savings_over_cap_is_invalid() {
        DepositCommand next = new DepositCommand("deposit 23456789 2501");
        assertFalse(val.validate(next));
    }

    @Test
    void valid_deposit_instruction_is_present() {
        DepositCommand command = new DepositCommand("deposit 12345678 1");
        assertTrue(command.getInstruction().equals("deposit"));
    }

    @Test
    void deposit_payload_is_invalid() {
        DepositCommand command = new DepositCommand("deposit invalid invalid");
        assertFalse(val.validate(command));
    }

    @Test
    void deposit_with_misspelled_deposit_instruction() {
        DepositCommand c1 = new DepositCommand("dewosit 12345678 500");
        assertFalse(val.validate(c1));
    }

    @Test
    void cmd_is_invalid() {
        DepositCommand c2 = new DepositCommand("deposit invalid command");
        assertFalse(val.validate(c2));
    }
}
