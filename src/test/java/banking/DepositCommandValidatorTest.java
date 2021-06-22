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
    void deposit_zero_to_cd_is_valid() {
        DepositCommand dc = new DepositCommand("deposit 87654321 0");
        assertTrue(dcv.validate(dc));
    }

    @Test
    void deposit_zero_to_savings_is_valid() {
        DepositCommand next = new DepositCommand("deposit 23456789 0");
        assertTrue(dcv.validate(next));
    }

    @Test
    void deposit_negative_one_to_checking_invalid() {
        DepositCommand dc = new DepositCommand("deposit 87654321 -1");
        assertFalse(dcv.validate(dc));
    }

    @Test
    void deposit_negative_one_to_savings_invalid() {
        DepositCommand next = new DepositCommand("deposit 23456789 -1");
        assertFalse(dcv.validate(next));
    }

    @Test
    void deposit_one_to_checking_valid() {
        DepositCommand dc = new DepositCommand("deposit 87654321 1");
        assertTrue(dcv.validate(dc));
    }

    @Test
    void deposit_one_to_savings_valid() {
        DepositCommand next = new DepositCommand("deposit 23456789 1");
        assertTrue(dcv.validate(next));
    }

    @Test
    void deposit_checking_under_cap_is_valid() {
        DepositCommand dc = new DepositCommand("deposit 87654321 999");
        assertTrue(dcv.validate(dc));
    }

    @Test
    void deposit_savings_under_cap_is_valid() {
        DepositCommand next = new DepositCommand("deposit 23456789 2499");
        assertTrue(dcv.validate(next));
    }

    @Test
    void deposit_checking_cap_is_valid() {
        DepositCommand dc = new DepositCommand("deposit 87654321 1000");
        assertTrue(dcv.validate(dc));
    }

    @Test
    void deposit_savings_cap_is_valid() {
        DepositCommand next = new DepositCommand("deposit 23456789 2500");
        assertTrue(dcv.validate(next));
    }

    @Test
    void deposit_checking_over_cap_is_invalid() {
        DepositCommand dc = new DepositCommand("deposit 87654321 1001");
        assertFalse(dcv.validate(dc));
    }

    @Test
    void deposit_savings_over_cap_is_invalid() {
        DepositCommand next = new DepositCommand("deposit 23456789 2501");
        assertFalse(dcv.validate(next));
    }
}
