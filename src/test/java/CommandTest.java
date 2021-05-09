import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandTest {

    @Test
    void invalid_account_type() {
        Command command = new Command("create badacc 12345678 .1");
        assertTrue(command.invalid_or_missing_account_type);
    }

    @Test
    void valid_create_instruction_is_present() {
        Command command = new Command("create badacc 12345678 .1");
        String instruction = command.getInstruction();
        assertTrue(command.contains_valid_instruction(instruction));
    }

    @Test
    void create_issued_without_create_instruction() {
        Command c1 = new Command("savings 12345678 .1");
        assertTrue(c1.invalid_instruction);
    }

    @Test
    void valid_deposit_instruction_is_present() {
        Command command = new Command("deposit 12345678 1");
        String instruction = command.getInstruction();
        assertTrue(command.contains_valid_instruction(instruction));
    }

    @Test
    void instruction_is_invalid_or_misspelled() {
        Command command = new Command("cweate badacc 12345678 .1");
        Command nextcommand = new Command("weposit 12345678 5");
        String instruction = command.getInstruction();
        String nextinstruction = nextcommand.getInstruction();
        assertFalse(command.contains_valid_instruction(instruction) && nextcommand.contains_valid_instruction(nextinstruction));
    }

    @Test
    void payload_is_present() {
        Command nextcommand = new Command("weposit 12345678 5");
        assertTrue(nextcommand.getPayload().length == 2);
    }

    @Test
    void payload_is_absent() {
        Command command = new Command("create");
        assertTrue(command.getPayload().length == 0);
    }

    @Test
    void payload_is_invalid() {
        Command command = new Command("create invalid invalid");
        assertTrue(command.payload_is_invalid());
    }

    @Test
    void cmd_is_invalid() {
        Command c1 = new Command("create invalid command");
        Command c2 = new Command("deposit invalid command");
        assertFalse(c1.validate_command() && c2.validate_command());
    }

    @Test
    void cmd_is_empty() {
        Command c1 = new Command("");
        assertTrue(c1.command_is_empty);
    }

    @Test
    void cmd_is_jibberish() {
        Command c1 = new Command("asoidjasoidjasoidj");
        assertFalse(c1.validate_command());
    }

    @Test
    void create_cd_with_invalid_amount() {
        Command c1 = new Command("create cd 11111111 .1 999999");
        assertTrue(c1.cd_with_illegal_init_balance);
    }

    @Test
    void create_cd_with_valid_amount() {
        Command c1 = new Command("create cd 11111111 .1 5000");
        assertFalse(c1.cd_with_illegal_init_balance);
    }

    @Test
    void create_cd_with_invalid_apr() {
        Command c1 = new Command("create cd 11111111 59 5000");
        assertTrue(c1.create_with_illegal_apr_val);
    }

    @Test
    void create_cd_with_valid_apr() {
        Command c1 = new Command("create cd 11111111 .1 5000");
        assertFalse(c1.create_with_illegal_apr_val);
    }

    @Test
    void create_with_duplicate_id() {
        Bank bank = new Bank();
        bank.addAccount(new CheckingAccount(89898989));
        Command c1 = new Command("create cd 89898989 .1 999999");
        assertTrue(c1.create_attempted_with_duplicate_id);
    }

    @Test
    void create_valid_account_with_random_capitalizations() {
        Command c1 = new Command("CReate CHEcKInG 12345678 .1");
        assertTrue(c1.validate_command());
    }

    @Test
    void create_with_negative_apr() {
        Command c1 = new Command("create checking 12345678 -5");
        assertTrue(c1.create_with_illegal_apr_val);
    }

    @Test
    void create_without_account_id() {
        Command c1 = new Command("create cd .1 999999");
        assertTrue(c1.create_cd_with_invalid_arglength);
    }

    @Test
    void create_cd_with_negative_balance() {
        Command c1 = new Command("create cd 11111111 .1 -5000");
        assertTrue(c1.cd_with_illegal_init_balance);
    }

    @Test
    void create_cd_without_apr() {
        Command c1 = new Command("create cd 11111111 5000");
        assertTrue(c1.create_cd_with_invalid_arglength);
    }

    @Test
    void create_savings_checking_without_apr() {
        Command c1 = new Command("create savings 12345678");
        Command c2 = new Command("create checking 12345679");
        assertTrue(c1.create_savings_checking_with_invalid_arglength && c2.create_savings_checking_with_invalid_arglength);
    }

    @Test
    void create_without_account_type() {
        Command c1 = new Command("create 11111111 .1");
        assertTrue(c1.invalid_or_missing_account_type);
    }

    @Test
    void create_with_invalid_short_id() {
        Command c1 = new Command("create savings 1234 .1");
        assertTrue(c1.malformed_id);
    }

    @Test
    void create_with_excessively_long_id() {
        Command c1 = new Command("create savings 12345678991 .45");
        assertTrue(c1.malformed_id);
    }
}
