package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassTimeCommandValidatorTest {
    PassTimeCommandValidator ptcv;
    SavingsAccount sa;
    CheckingAccount ca;
    CDAccount cd;
    Bank bank = new Bank();

    @BeforeEach
    void setup() {
        ptcv = new PassTimeCommandValidator();
        cd = new CDAccount(12345678, .05, 0);
        ca = new CheckingAccount(87654321, .05);
        sa = new SavingsAccount(23456789, .05);
        bank.addAccount(cd);
        bank.addAccount(sa);
        bank.addAccount(ca);
    }

    @Test
    void pass_min_valid_is_valid() {
        PassCommand pc = new PassCommand("pass 1");
        assertTrue(ptcv.validate(pc));
    }

    @Test
    void pass_max_valid_is_valid() {
        PassCommand pc = new PassCommand("pass 60");
        assertTrue(ptcv.validate(pc));
    }

    @Test
    void pass_below_valid_is_invalid() {
        PassCommand pc = new PassCommand("pass 0");
        assertFalse(ptcv.validate(pc));
    }

    @Test
    void pass_above_valid_is_invalid() {
        PassCommand pc = new PassCommand("pass 61");
        assertFalse(ptcv.validate(pc));
    }

    @Test
    void pass_decimal_is_invalid() {
        PassCommand pc = new PassCommand("pass 6.0");
        assertFalse(ptcv.validate(pc));
    }
}
