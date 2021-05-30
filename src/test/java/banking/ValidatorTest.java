package banking;

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
        create_cmd command = new create_cmd("create badacc 12345678 .1");
        val.validate(command);
        assertFalse(val.valid_type_given);
    }

    @Test
    void valid_create_instruction_is_present() {
        create_cmd command = new create_cmd("create badacc 12345678 .1");
        val.validate(command);
        assertTrue(val.cmd_has_valid_instruction);
    }

    @Test
    void create_issued_without_create_instruction() {
        create_cmd c1 = new create_cmd("savings 12345678 .1");
        val.validate(c1);
        assertFalse(val.cmd_has_valid_instruction);
    }

    @Test
    void instruction_is_invalid_or_misspelled() {
        create_cmd command = new create_cmd("cweate badacc 12345678 .1");
        String instruction = command.getInstruction();
        assertFalse(instruction.equals("create"));
    }

    @Test
    void payload_is_absent_create() {
        create_cmd command = new create_cmd("create");
        val.validate(command);
        assertFalse(val.cmd_has_valid_payload_size);
    }

    @Test
    void payload_is_invalid() {
        create_cmd command = new create_cmd("create invalid invalid");
        val.validate(command);
        assertFalse(val.cmd_has_valid_payload_size && val.valid_id_format);
    }

    @Test
    void cmd_is_empty() {
        create_cmd c1 = new create_cmd("");
        assertTrue(c1.command_is_empty);
    }

    @Test
    void create_cd_with_invalid_amount() {
        create_cmd c1 = new create_cmd("create cd 11111111 .1 999999");
        val.validate(c1);
        assertFalse(val.valid_init_balance);
    }

    @Test
    void create_cd_with_valid_amount() {
        create_cmd c1 = new create_cmd("create cd 11111111 .1 5000");
        val.validate(c1);
        assertTrue(val.valid_init_balance);
    }

    @Test
    void create_cd_with_invalid_apr() {
        create_cmd c1 = new create_cmd("create cd 11111111 59 5000");
        val.validate(c1);
        assertFalse(val.valid_apr_given);
    }

    @Test
    void create_cd_with_valid_apr() {
        create_cmd c1 = new create_cmd("create cd 11111111 .1 5000");
        val.validate(c1);
        assertTrue(val.valid_apr_given);
    }

    @Test
    void create_valid_account_with_random_capitalizations() {
        create_cmd c1 = new create_cmd("CReate CHEcKInG 12345678 .1");
        assertTrue(val.validate(c1));
    }

    @Test
    void create_without_account_id() {
        create_cmd c1 = new create_cmd("create cd .1 999999");
        val.validate(c1);
        assertFalse(val.valid_id_format);
    }

    @Test
    void create_cd_with_negative_balance() {
        create_cmd c1 = new create_cmd("create cd 11111111 .1 -5000");
        val.validate(c1);
        assertFalse(val.valid_init_balance);
    }

    @Test
    void create_cd_without_apr() {
        create_cmd c1 = new create_cmd("create cd 11111111 5000");
        val.validate(c1);
        assertFalse(val.valid_apr_given);
    }

    @Test
    void create_savings_checking_without_apr() {
        create_cmd c1 = new create_cmd("create savings 12345678");
        create_cmd c2 = new create_cmd("create checking 12345679");
        val.validate(c1);
        Validator v2 = new Validator(bank);
        v2.validate(c2);
        assertFalse(val.cmd_has_valid_payload_size && v2.cmd_has_valid_payload_size);
    }

    @Test
    void create_without_account_type() {
        create_cmd c1 = new create_cmd("create 11111111 .1");
        val.validate(c1);
        assertFalse(val.cmd_has_valid_payload_size);
    }

    @Test
    void create_with_invalid_short_id() {
        create_cmd c1 = new create_cmd("create savings 1234 .1");
        val.validate(c1);
        assertFalse(val.valid_id_format);
    }

    @Test
    void create_with_excessively_long_id() {
        create_cmd c1 = new create_cmd("create savings 12345678991 .45");
        val.validate(c1);
        assertFalse(val.valid_id_format);
    }

    @Test
    void cmd_is_jibberish() {
        create_cmd c1 = new create_cmd("asoidjasoidjasoidj");
        val.validate(c1);
        assertFalse(val.validate(c1));
    }

    @Test
    void valid_deposit_instruction_is_present() {
        deposit_cmd command = new deposit_cmd("deposit 12345678 1");
        assertTrue(command.getInstruction().equals("deposit"));
    }

    @Test
    void payload_is_absent_deposit() {
        deposit_cmd command = new deposit_cmd("deposit");
        val.validate(command);
        assertFalse(val.cmd_has_valid_payload_size);
    }

    @Test
    void deposit_payload_is_invalid() {
        deposit_cmd command = new deposit_cmd("deposit invalid invalid");
        val.validate(command);
        assertFalse(val.valid_id_format);
    }

    @Test
    void deposit_with_misspelled_deposit_instruction() {
        deposit_cmd c1 = new deposit_cmd("dewosit 12345678 500");
        val.validate(c1);
        assertFalse(val.cmd_has_valid_instruction);
    }

    @Test
    void deposit_without_deposit_instruction() {
        deposit_cmd c1 = new deposit_cmd("12345678 500");
        val.validate(c1);
        assertFalse(val.cmd_has_valid_instruction);
    }

    @Test
    void deposit_without_id_present() {
        deposit_cmd c1 = new deposit_cmd("deposit 500");
        val.validate(c1);
        assertFalse(val.cmd_has_valid_payload_size);
    }

    @Test
    void deposit_without_amount_present() {
        deposit_cmd c1 = new deposit_cmd("deposit 12345678");
        val.validate(c1);
        assertFalse(val.cmd_has_valid_payload_size);
    }

    @Test
    void cmd_is_invalid() {
        deposit_cmd c2 = new deposit_cmd("deposit invalid command");
        val.validate(c2);
        assertFalse(val.validate(c2));
    }
}
