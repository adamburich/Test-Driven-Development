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
    void payload_is_absent_create() {
        CreateCommand command = new CreateCommand("create");
        assertFalse(val.validate(command));
    }

    @Test
    void payload_is_invalid() {
        CreateCommand command = new CreateCommand("create invalid invalid");
        assertFalse(val.validate(command));
    }

    @Test
    void deposit_without_deposit_instruction() {
        DepositCommand c1 = new DepositCommand("12345678 500");
        assertFalse(val.validate(c1));
    }

    @Test
    void deposit_without_id_present() {
        DepositCommand c1 = new DepositCommand("deposit 500");
        assertFalse(val.validate(c1));
    }

    @Test
    void deposit_without_amount_present() {
        DepositCommand c1 = new DepositCommand("deposit 12345678");
        assertFalse(val.validate(c1));
    }

    @Test
    void payload_is_absent_deposit() {
        DepositCommand command = new DepositCommand("deposit");
        assertFalse(val.validate(command));
    }

    @Test
    void cmd_is_jibberish() {
        CreateCommand c1 = new CreateCommand("asoidjasoidjasoidj");
        assertFalse(val.validate(c1));
    }

    @Test
    void create_issued_without_create_instruction() {
        CreateCommand c1 = new CreateCommand("savings 12345678 .1");
        assertFalse(val.validate(c1));
    }

    @Test
    void create_savings_checking_without_apr() {
        CreateCommand c1 = new CreateCommand("create savings 12345678");
        CreateCommand c2 = new CreateCommand("create checking 12345679");
        assertFalse(val.validate(c1) && val.validate(c2));
    }

    @Test
    void create_without_account_type() {
        CreateCommand c1 = new CreateCommand("create 11111111 .1");
        assertFalse(val.validate(c1));
    }

    @Test
    void command_with_random_extra_spacing_invalid() {
        CreateCommand c1 = new CreateCommand("create savings     12345678 .1");
        assertFalse(val.validate(c1));
    }

    @Test
    void command_with_extra_spacing_at_end_valid() {
        CreateCommand c1 = new CreateCommand("create savings 12345678 .1     ");
        assertTrue(val.validate(c1));
    }

    @Test
    void command_with_numbers_in_instruction_invalid() {
        Command c1 = new Command("cre1ate savings 12345678 .14");
        assertFalse(val.validate(c1));
    }

    @Test
    void command_with_letters_in_account_id_invalid() {
        bank.addAccount(new CheckingAccount(12345678, 1));
        bank.getAccount(12345678).setBalance(500);
        CreateCommand c1 = new CreateCommand("create savings 123AB678 .14");
        assertFalse(val.validate(c1));
    }

    @Test
    void command_with_letters_in_balance_invalid() {
        bank.addAccount(new CheckingAccount(12345678, 1));
        bank.getAccount(12345678).setBalance(500);
        WithdrawalCommand c1 = new WithdrawalCommand("withdraw 12345678 12A");
        assertFalse(val.validate(c1));
    }

    @Test
    void command_with_letters_in_apr_invalid() {
        CreateCommand c1 = new CreateCommand("create savings 12345678 .1A34");
        assertFalse(val.validate(c1));
    }

    @Test
    void valid_create_command_is_valid() {
        CreateCommand c1 = new CreateCommand("create savings 12345678 .14");
        assertTrue(val.validate(c1));
    }

    @Test
    void invalid_create_command_is_invalid() {
        CreateCommand c = new CreateCommand("Create savings 1234ABC2 .1A4");
        assertFalse(val.validate(c));
    }

    @Test
    void invalid_transfer_command_is_invalid() {
        TransferCommand t = new TransferCommand("transfer 123EFGH8 234ABCD9 1A0");
        assertFalse(val.validate(t));
    }

}
