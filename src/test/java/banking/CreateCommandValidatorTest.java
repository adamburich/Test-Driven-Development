package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateCommandValidatorTest {
    CreateCommandValidator val;
    Bank bank = new Bank();

    @BeforeEach
    void setup() {
        val = new CreateCommandValidator(bank);
    }

    @Test
    void create_invalid_account_type() {
        CreateCommand command = new CreateCommand("create badacc 12345678 .1");
        val.validate(command);
        assertFalse(val.valid_type_given);
    }

    @Test
    void valid_create_is_present() {
        CreateCommand command = new CreateCommand("create savings 12345678 .1");
        assertTrue(val.validate(command));
    }

    @Test
    void instruction_is_invalid_or_misspelled() {
        CreateCommand command = new CreateCommand("cweate badacc 12345678 .1");
        String instruction = command.getInstruction();
        assertFalse(instruction.equals("create"));
    }

    @Test
    void cmd_is_empty() {
        CreateCommand c1 = new CreateCommand("");
        assertTrue(c1.command_is_empty);
    }

    @Test
    void create_cd_with_invalid_amount() {
        CreateCommand c1 = new CreateCommand("create cd 11111111 .1 999999");
        assertFalse(val.validate(c1));
    }

    @Test
    void create_cd_with_valid_amount() {
        CreateCommand c1 = new CreateCommand("create cd 11111111 .1 5000");
        assertTrue(val.validate(c1));
    }

    @Test
    void create_cd_with_invalid_apr() {
        CreateCommand c1 = new CreateCommand("create cd 11111111 59 5000");
        assertFalse(val.validate(c1));
    }

    @Test
    void create_cd_with_valid_apr() {
        CreateCommand c1 = new CreateCommand("create cd 11111111 .1 5000");
        assertTrue(val.validate(c1));
    }

    @Test
    void create_valid_account_with_random_capitalizations() {
        CreateCommand c1 = new CreateCommand("CReate CHEcKInG 12345678 .1");
        assertTrue(val.validate(c1));
    }

    @Test
    void create_without_account_id() {
        CreateCommand c1 = new CreateCommand("create cd .1 999999");
        assertFalse(val.validate(c1));
    }

    @Test
    void create_cd_with_negative_balance() {
        CreateCommand c1 = new CreateCommand("create cd 11111111 .1 -5000");
        assertFalse(val.validate(c1));
    }

    @Test
    void create_cd_without_apr() {
        CreateCommand c1 = new CreateCommand("create cd 11111111 5000");
        assertFalse(val.validate(c1));
    }

    @Test
    void create_with_invalid_short_id() {
        CreateCommand c1 = new CreateCommand("create savings 1234 .1");
        assertFalse(val.validate(c1));
    }

    @Test
    void create_with_excessively_long_id() {
        CreateCommand c1 = new CreateCommand("create savings 12345678991 .45");
        assertFalse(val.validate(c1));
    }

    @Test
    void create_with_negative_apr() {
        assertFalse(val.validate(new CreateCommand("create checking 12345678 -.01")));
    }

    @Test
    void create_with_too_high_apr() {
        assertFalse(val.validate(new CreateCommand("create checking 12345678 10.01")));
    }

    @Test
    void create_with_cap_apr() {
        assertTrue(val.validate(new CreateCommand("create checking 12345678 10")));
    }

    @Test
    void create_with_valid_high_apr() {
        assertTrue(val.validate(new CreateCommand("create checking 12345678 9.9")));
    }

    @Test
    void create_with_valid_low_apr() {
        assertTrue(val.validate(new CreateCommand("create checking 12345678 .01")));
    }

    @Test
    void create_with_valid_zero_apr() {
        assertTrue(val.validate(new CreateCommand("create checking 12345678 0")));
    }

    @Test
    void create_cd_with_negative_init() {
        assertFalse(val.validate(new CreateCommand("create cd 12345678 -1 5")));
    }

    @Test
    void create_cd_with_zero_init() {
        assertTrue(val.validate(new CreateCommand("create cd 12345678 1 0")));
    }

    @Test
    void create_cd_with_low_legal_init() {
        assertTrue(val.validate(new CreateCommand("create cd 12345678 1 1")));
    }

    @Test
    void create_cd_with_cap_init() {
        assertTrue(val.validate(new CreateCommand("create cd 12345678 1 10000")));
    }

    @Test
    void create_cd_with_too_high_init() {
        assertFalse(val.validate(new CreateCommand("create cd 12345678 1 10001")));
    }

    @Test
    void create_cd_with_valid_high_init() {
        assertTrue(val.validate(new CreateCommand("create cd 12345678 1 9999")));
    }

    @Test
    void attempt_create_with_letter_number_ID() {
        assertFalse(val.validate(new CreateCommand("create checking ABC12345 0.5")));
    }

    @Test
    void attempt_create_with_nine_digit_ID_is_invalid() {
        assertFalse(val.validate(new CreateCommand("create checking 123456789 .1")));
    }

    @Test
    void attempt_create_with_seven_digit_ID_is_invalid() {
        assertFalse(val.validate(new CreateCommand("create checking 1234567 .1")));
    }
}
