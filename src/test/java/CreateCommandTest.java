import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateCommandTest {

    @Test
    void invalid_account_type() {
        CreateCommand command = new CreateCommand("create badacc 12345678 .1");
        assertTrue(command.invalid_or_missing_account_type);
    }

    @Test
    void valid_create_instruction_is_present() {
        CreateCommand command = new CreateCommand("create badacc 12345678 .1");
        String instruction = command.getInstruction();
        assertTrue(command.contains_valid_instruction(instruction));
    }

    @Test
    void create_issued_without_create_instruction() {
        CreateCommand c1 = new CreateCommand("savings 12345678 .1");
        assertTrue(c1.invalid_instruction);
    }

    @Test
    void instruction_is_invalid_or_misspelled() {
        CreateCommand command = new CreateCommand("cweate badacc 12345678 .1");
        String instruction = command.getInstruction();
        assertFalse(command.contains_valid_instruction(instruction));
    }

    @Test
    void payload_is_absent_create() {
        CreateCommand command = new CreateCommand("create");
        assertFalse(command.validate_create_payload_arg_length());
    }

    @Test
    void payload_is_invalid() {
        CreateCommand command = new CreateCommand("create invalid invalid");
        assertTrue(command.create_payload_is_invalid());
    }


    @Test
    void cmd_is_empty() {
        CreateCommand c1 = new CreateCommand("");
        assertTrue(c1.command_is_empty);
    }


    @Test
    void create_cd_with_invalid_amount() {
        CreateCommand c1 = new CreateCommand("create cd 11111111 .1 999999");
        assertTrue(c1.cd_with_illegal_init_balance);
    }

    @Test
    void create_cd_with_valid_amount() {
        CreateCommand c1 = new CreateCommand("create cd 11111111 .1 5000");
        assertFalse(c1.cd_with_illegal_init_balance);
    }

    @Test
    void create_cd_with_invalid_apr() {
        CreateCommand c1 = new CreateCommand("create cd 11111111 59 5000");
        assertTrue(c1.create_with_illegal_apr_val);
    }

    @Test
    void create_cd_with_valid_apr() {
        CreateCommand c1 = new CreateCommand("create cd 11111111 .1 5000");
        assertFalse(c1.create_with_illegal_apr_val);
    }

    @Test
    void create_with_duplicate_id() {
        Bank bank = new Bank();
        bank.addAccount(new CheckingAccount(89898989));
        CreateCommand c1 = new CreateCommand("create cd 89898989 .1 999999");
        assertTrue(c1.create_attempted_with_duplicate_id);
    }

    @Test
    void create_valid_account_with_random_capitalizations() {
        CreateCommand c1 = new CreateCommand("CReate CHEcKInG 12345678 .1");
        assertTrue(c1.validate_command());
    }

    @Test
    void create_with_negative_apr() {
        CreateCommand c1 = new CreateCommand("create checking 12345678 -5");
        assertTrue(c1.create_with_illegal_apr_val);
    }

    @Test
    void create_without_account_id() {
        CreateCommand c1 = new CreateCommand("create cd .1 999999");
        assertTrue(c1.create_cd_with_invalid_arglength);
    }

    @Test
    void create_cd_with_negative_balance() {
        CreateCommand c1 = new CreateCommand("create cd 11111111 .1 -5000");
        assertTrue(c1.cd_with_illegal_init_balance);
    }

    @Test
    void create_cd_without_apr() {
        CreateCommand c1 = new CreateCommand("create cd 11111111 5000");
        assertTrue(c1.create_cd_with_invalid_arglength);
    }

    @Test
    void create_savings_checking_without_apr() {
        CreateCommand c1 = new CreateCommand("create savings 12345678");
        CreateCommand c2 = new CreateCommand("create checking 12345679");
        assertTrue(c1.create_savings_checking_with_invalid_arglength && c2.create_savings_checking_with_invalid_arglength);
    }

    @Test
    void create_without_account_type() {
        CreateCommand c1 = new CreateCommand("create 11111111 .1");
        assertTrue(c1.invalid_or_missing_account_type);
    }

    @Test
    void create_with_invalid_short_id() {
        CreateCommand c1 = new CreateCommand("create savings 1234 .1");
        assertTrue(c1.malformed_id);
    }

    @Test
    void create_with_excessively_long_id() {
        CreateCommand c1 = new CreateCommand("create savings 12345678991 .45");
        assertTrue(c1.malformed_id);
    }

    @Test
    void cmd_is_jibberish() {
        CreateCommand c1 = new CreateCommand("asoidjasoidjasoidj");
        assertFalse(c1.validate_command());
    }
}
