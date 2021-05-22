import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTest {
    Validator val;
    Bank bank = new Bank();

    @BeforeEach
    void setup() {
        val = new Validator(bank);
    }

    @Test
    void invalid_account_type() {
        ccmd command = new ccmd("create badacc 12345678 .1");
        val.validate(command);
        assertFalse(val.valid_type_given);
    }

    @Test
    void valid_create_instruction_is_present() {
        ccmd command = new ccmd("create badacc 12345678 .1");
        val.validate(command);
        assertTrue(val.cmd_has_valid_instruction);
    }

    @Test
    void create_issued_without_create_instruction() {
        ccmd c1 = new ccmd("savings 12345678 .1");
        val.validate(c1);
        assertFalse(val.cmd_has_valid_instruction);
    }

    @Test
    void instruction_is_invalid_or_misspelled() {
        ccmd command = new ccmd("cweate badacc 12345678 .1");
        String instruction = command.getInstruction();
        assertFalse(instruction.equals("create"));
    }

    @Test
    void payload_is_absent_create() {
        ccmd command = new ccmd("create");
        val.validate(command);
        assertFalse(val.cmd_has_valid_payload_size);
    }

    @Test
    void payload_is_invalid() {
        ccmd command = new ccmd("create invalid invalid");
        val.validate(command);
        assertFalse(val.cmd_has_valid_payload_size && val.valid_id_format);
    }

    @Test
    void cmd_is_empty() {
        ccmd c1 = new ccmd("");
        assertTrue(c1.command_is_empty);
    }

    @Test
    void create_cd_with_invalid_amount() {
        ccmd c1 = new ccmd("create cd 11111111 .1 999999");
        val.validate(c1);
        assertFalse(val.valid_init_balance);
    }

    @Test
    void create_cd_with_valid_amount() {
        ccmd c1 = new ccmd("create cd 11111111 .1 5000");
        val.validate(c1);
        assertTrue(val.valid_init_balance);
    }

    @Test
    void create_cd_with_invalid_apr() {
        ccmd c1 = new ccmd("create cd 11111111 59 5000");
        val.validate(c1);
        assertFalse(val.valid_apr_given);
    }

    @Test
    void create_cd_with_valid_apr() {
        ccmd c1 = new ccmd("create cd 11111111 .1 5000");
        val.validate(c1);
        assertTrue(val.valid_apr_given);
    }

    @Test
    void create_valid_account_with_random_capitalizations() {
        ccmd c1 = new ccmd("CReate CHEcKInG 12345678 .1");
        assertTrue(val.validate(c1));
    }

    @Test
    void create_without_account_id() {
        ccmd c1 = new ccmd("create cd .1 999999");
        val.validate(c1);
        assertFalse(val.valid_id_format);
    }

    @Test
    void create_cd_with_negative_balance() {
        ccmd c1 = new ccmd("create cd 11111111 .1 -5000");
        val.validate(c1);
        assertFalse(val.valid_init_balance);
    }

    @Test
    void create_cd_without_apr() {
        ccmd c1 = new ccmd("create cd 11111111 5000");
        val.validate(c1);
        assertFalse(val.valid_apr_given);
    }

    @Test
    void create_savings_checking_without_apr() {
        ccmd c1 = new ccmd("create savings 12345678");
        ccmd c2 = new ccmd("create checking 12345679");
        val.validate(c1);
        Validator v2 = new Validator(bank);
        v2.validate(c2);
        assertFalse(val.cmd_has_valid_payload_size && v2.cmd_has_valid_payload_size);
    }

    @Test
    void create_without_account_type() {
        ccmd c1 = new ccmd("create 11111111 .1");
        val.validate(c1);
        assertFalse(val.cmd_has_valid_payload_size);
    }

    @Test
    void create_with_invalid_short_id() {
        ccmd c1 = new ccmd("create savings 1234 .1");
        val.validate(c1);
        assertFalse(val.valid_id_format);
    }

    @Test
    void create_with_excessively_long_id() {
        ccmd c1 = new ccmd("create savings 12345678991 .45");
        val.validate(c1);
        assertFalse(val.valid_id_format);
    }

    @Test
    void cmd_is_jibberish() {
        ccmd c1 = new ccmd("asoidjasoidjasoidj");
        val.validate(c1);
        assertFalse(val.validate(c1));
    }

    @Test
    void valid_deposit_instruction_is_present() {
        dcmd command = new dcmd("deposit 12345678 1");
        assertTrue(command.getInstruction().equals("deposit"));
    }

    @Test
    void payload_is_absent_deposit() {
        dcmd command = new dcmd("deposit");
        val.validate(command);
        assertFalse(val.cmd_has_valid_payload_size);
    }

    @Test
    void deposit_payload_is_invalid() {
        dcmd command = new dcmd("deposit invalid invalid");
        val.validate(command);
        assertFalse(val.valid_id_format);
    }

    @Test
    void deposit_with_misspelled_deposit_instruction() {
        dcmd c1 = new dcmd("dewosit 12345678 500");
        val.validate(c1);
        assertFalse(val.cmd_has_valid_instruction);
    }

    @Test
    void deposit_without_deposit_instruction() {
        dcmd c1 = new dcmd("12345678 500");
        val.validate(c1);
        assertFalse(val.cmd_has_valid_instruction);
    }

    @Test
    void deposit_without_id_present() {
        dcmd c1 = new dcmd("deposit 500");
        val.validate(c1);
        assertFalse(val.cmd_has_valid_payload_size);
    }

    @Test
    void deposit_without_amount_present() {
        dcmd c1 = new dcmd("deposit 12345678");
        val.validate(c1);
        assertFalse(val.cmd_has_valid_payload_size);
    }

    @Test
    void cmd_is_invalid() {
        dcmd c2 = new dcmd("deposit invalid command");
        val.validate(c2);
        assertFalse(val.validate(c2));
    }
}
