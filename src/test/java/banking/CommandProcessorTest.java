package banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandProcessorTest {
    Bank bank = new Bank();
    CommandProcessor processor = new CommandProcessor(bank);

    @Test
    void issued_valid_create_checking() {
        create_cmd cmd = new create_cmd("create checking 12345678 1.0");
        processor.issue_command(cmd);
        assertTrue(bank.getAccount(12345678) != null && bank.getAccount(12345678).getAPR() == 1.0 && bank.getAccount(12345678).getType().equalsIgnoreCase("checking"));
    }

    @Test
    void issued_valid_create_cd() {
        create_cmd cmd = new create_cmd("create cd 23456789 1.0 500");
        processor.issue_command(cmd);
        assertTrue(bank.getAccount(23456789) != null && bank.getAccount(23456789).getAPR() == 1.0 && bank.getAccount(23456789).getType().equalsIgnoreCase("cd"));
    }

    @Test
    void issued_valid_create_savings() {
        create_cmd cmd = new create_cmd("create savings 34567890 1.0");
        processor.issue_command(cmd);
        assertTrue(bank.getAccount(34567890) != null && bank.getAccount(34567890).getAPR() == 1.0 && bank.getAccount(34567890).getType().equalsIgnoreCase("savings"));
    }

    @Test
    void issued_valid_deposit_to_empty_account() {
        create_cmd create = new create_cmd("create checking 12345678 1.0");
        processor.issue_command(create);
        deposit_cmd deposit = new deposit_cmd("deposit 12345678 100");
        processor.issue_command(deposit);
        assertTrue(bank.getAccount(12345678).getBalance() == 100);
    }

    @Test
    void issued_valid_deposit_to_account_with_existing_balance() {
        create_cmd create = new create_cmd("create checking 12345678 1.0");
        processor.issue_command(create);
        deposit_cmd deposit = new deposit_cmd("deposit 12345678 100");
        processor.issue_command(deposit);
        double bal = bank.getAccount(12345678).getBalance();
        deposit_cmd deposit_again = new deposit_cmd("deposit 12345678 100");
        processor.issue_command(deposit_again);
        assertTrue(bank.getAccount(12345678).getBalance() == bal + 100);

    }

    @Test
    void issue_valid_passtime_cmd(){
        bank.addAccount(new CheckingAccount(12345678, 5));
        bank.getAccount(12345678).setBalance(3000);
        passtime_cmd pass = new passtime_cmd("pass 12");
        processor.issue_command(pass);
        assertTrue(bank.getAccount(12345678).getBalance() > 3000);
    }
}
