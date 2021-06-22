package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateCommandValidatorTest {
    CreateCommandValidator ccv;
    Bank bank = new Bank();

    @BeforeEach
    void setup() {
        ccv = new CreateCommandValidator(bank);
    }

    @Test
    void create_with_negative_apr() {
        boolean cc = ccv.validate(new CreateCommand("create checking 12345678 -.01"));
        assertFalse(cc);
    }

    @Test
    void create_with_too_high_apr() {
        boolean cc = ccv.validate(new CreateCommand("create checking 12345678 10.01"));
        assertFalse(cc);
    }

    @Test
    void create_with_cap_apr() {
        boolean cc = ccv.validate(new CreateCommand("create checking 12345678 10"));
        assertTrue(cc);
    }

    @Test
    void create_with_valid_high_apr() {
        boolean cc = ccv.validate(new CreateCommand("create checking 12345678 9.9"));
        assertTrue(cc);
    }

    @Test
    void create_with_valid_low_apr() {
        boolean cc = ccv.validate(new CreateCommand("create checking 12345678 .01"));
        assertTrue(cc);
    }

    @Test
    void create_with_valid_zero_apr() {
        boolean cc = ccv.validate(new CreateCommand("create checking 12345678 0"));
        assertTrue(cc);
    }

    @Test
    void create_cd_with_negative_init() {
        boolean cc = ccv.validate(new CreateCommand("create cd 12345678 -1 5"));
        assertFalse(cc);
    }

    @Test
    void create_cd_with_zero_init() {
        boolean cc = ccv.validate(new CreateCommand("create cd 12345678 1 0"));
        assertTrue(cc);
    }

    @Test
    void create_cd_with_low_legal_init() {
        boolean cc = ccv.validate(new CreateCommand("create cd 12345678 1 1"));
        assertTrue(cc);
    }

    @Test
    void create_cd_with_cap_init() {
        boolean cc = ccv.validate(new CreateCommand("create cd 12345678 1 10000"));
        assertTrue(cc);
    }

    @Test
    void create_cd_with_too_high_init() {
        boolean cc = ccv.validate(new CreateCommand("create cd 12345678 1 10001"));
        assertFalse(cc);
    }

    @Test
    void create_cd_with_valid_high_init() {
        boolean cc = ccv.validate(new CreateCommand("create cd 12345678 1 9999"));
        assertTrue(cc);
    }
}
