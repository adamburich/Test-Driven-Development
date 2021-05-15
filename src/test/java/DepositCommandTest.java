import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepositCommandTest {

    @Test
    void valid_deposit_instruction_is_present() {
        DepositCommand command = new DepositCommand("deposit 12345678 1");
        String instruction = command.getInstruction();
        assertTrue(command.contains_valid_instruction(instruction));
    }

    @Test
    void instruction_is_invalid_or_misspelled() {
        DepositCommand nextcommand = new DepositCommand("weposit 12345678 5");
        String nextinstruction = nextcommand.getInstruction();
        assertFalse(nextcommand.contains_valid_instruction(nextinstruction));
    }

    @Test
    void payload_is_present() {
        DepositCommand nextcommand = new DepositCommand("weposit 12345678 5");
        assertTrue(nextcommand.validate_deposit_payload_arg_length());
    }

    @Test
    void payload_is_absent_deposit() {
        DepositCommand command = new DepositCommand("deposit");
        assertFalse(command.validate_deposit_payload_arg_length());
    }

    @Test
    void payload_is_invalid() {
        DepositCommand command = new DepositCommand("deposit invalid invalid");
        assertTrue(command.deposit_payload_is_invalid());
    }


    @Test
    void cmd_is_empty() {
        DepositCommand c2 = new DepositCommand("");
        assertTrue(c2.command_is_empty);
    }

    @Test
    void deposit_with_misspelled_deposit_instruction() {
        DepositCommand c1 = new DepositCommand("dewosit 12345678 500");
        assertTrue(c1.invalid_instruction);
    }

    @Test
    void deposit_without_deposit_instruction() {
        DepositCommand c1 = new DepositCommand("12345678 500");
        assertTrue(c1.invalid_instruction);
    }

    @Test
    void deposit_without_id_present() {
        DepositCommand c1 = new DepositCommand("deposit 500");
        assertTrue(c1.deposit_payload_is_invalid());
    }

    @Test
    void deposit_without_amount_present() {
        DepositCommand c1 = new DepositCommand("deposit 12345678");
        assertTrue(c1.deposit_payload_is_invalid());
    }

    @Test
    void deposit_attempted_with_negative_amount() {
        Bank bank = new Bank();
        bank.addAccount(new CheckingAccount(12345678));
        bank.addAccount(new SavingsAccount(89898989));
        DepositCommand c1 = new DepositCommand("deposit 12345678 -500");
        DepositCommand c2 = new DepositCommand("deposit 89898989 -500");
        assertTrue(c1.attempted_to_deposit_negative && c2.attempted_to_deposit_negative);
    }

    @Test
    void deposit_to_nonexistant_account() {
        DepositCommand c1 = new DepositCommand("deposit 41414141 500");
        assertTrue(c1.attempt_to_access_nonexistant_account);
    }

    @Test
    void deposit_with_random_capitalization() {
        Bank bank = new Bank();
        bank.addAccount(new CheckingAccount(11112222));
        DepositCommand c1 = new DepositCommand("dEpoSit 11112222 500");
        assertTrue(c1.validate_command());
    }

    @Test
    void valid_deposit_to_valid_account() {
        Bank bank = new Bank();
        bank.addAccount(new CheckingAccount(11113333));
        DepositCommand c1 = new DepositCommand("deposit 11113333 500");
        assertTrue(c1.validate_command());
    }

    @Test
    void deposit_too_much_to_checking() {
        Bank bank = new Bank();
        bank.addAccount(new CheckingAccount(11114444));
        DepositCommand c1 = new DepositCommand("deposit 11114444 9999");
        assertTrue(c1.deposit_to_checking_is_illegal_amount);
    }

    @Test
    void deposit_too_much_to_savings() {
        Bank bank = new Bank();
        bank.addAccount(new SavingsAccount(11115555));
        DepositCommand c1 = new DepositCommand("deposit 11115555 50505");
        assertTrue(c1.deposit_to_savings_is_illegal_amount);
    }

    @Test
    void attempt_deposit_to_cd_account() {
        Bank bank = new Bank();
        bank.addAccount(new CDAccount(55554444));
        DepositCommand c1 = new DepositCommand("deposit 55554444 1000");
        assertTrue(c1.attempted_deposit_to_cd_account);
    }

    @Test
    void cmd_is_invalid() {
        DepositCommand c2 = new DepositCommand("deposit invalid command");
        assertFalse(c2.validate_command());
    }

    @Test
    void cmd_is_jibberish() {
        DepositCommand c1 = new DepositCommand("asoidjasoidjasoidj");
        assertFalse(c1.validate_command());
    }
}
