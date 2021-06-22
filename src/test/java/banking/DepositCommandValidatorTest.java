package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepositCommandValidatorTest {
    DepositCommandValidator dcv;
    SavingsAccount sa;
    CheckingAccount ca;
    CDAccount cd;
    Bank bank = new Bank();

    @BeforeEach
    void setup() {
        dcv = new DepositCommandValidator(bank);
        ca = new CheckingAccount(87654321, .05);
        sa = new SavingsAccount(23456789, .05);
        bank.addAccount(sa);
        bank.addAccount(ca);
    }

    @Test
    void deposit_zero_is_valid() {
        DepositCommand dc = new DepositCommand("deposit 87654321 0");
        DepositCommand next = new DepositCommand("deposit 2345678 0");
        assertTrue(dcv.validate(dc));
        assertTrue(dcv.validate(next));
    }

    @Test
    void deposit_negative_one_invalid() {
        DepositCommand dc = new DepositCommand("deposit 87654321 -1");
        DepositCommand next = new DepositCommand("deposit 2345678 -1");
        assertFalse(dcv.validate(dc));
        assertFalse(dcv.validate(next));
    }

    @Test
    void deposit_one_valid() {
        DepositCommand dc = new DepositCommand("deposit 87654321 1");
        DepositCommand next = new DepositCommand("deposit 2345678 1");
        assertTrue(dcv.validate(dc));
        assertTrue(dcv.validate(next));
    }

    @Test
    void deposit_under_cap_is_valid() {
        DepositCommand dc = new DepositCommand("deposit 87654321 999");
        DepositCommand next = new DepositCommand("deposit 2345678 2499");
        assertTrue(dcv.validate(dc));
        assertTrue(dcv.validate(next));
    }

    @Test
    void deposit_cap_is_valid() {
        DepositCommand dc = new DepositCommand("deposit 87654321 1000");
        DepositCommand next = new DepositCommand("deposit 2345678 2500");
        assertTrue(dcv.validate(dc));
        assertTrue(dcv.validate(next));
    }

    @Test
    void deposit_over_cap_is_invalid() {
        DepositCommand dc = new DepositCommand("deposit 87654321 1001");
        DepositCommand next = new DepositCommand("deposit 2345678 2501");
        assertFalse(dcv.validate(dc));
        assertFalse(dcv.validate(next));
    }
}
