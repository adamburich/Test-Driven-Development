package banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandProcessorTest {
    Bank bank = new Bank();
    CommandProcessor processor = new CommandProcessor(bank);

    @Test
    void issued_valid_create_checking() {
        CreateCommand cmd = new CreateCommand("create checking 12345678 1.0");
        processor.issue_command(cmd);
        assertTrue(bank.getAccount(12345678) != null && bank.getAccount(12345678).getAPR() == 1.0 && bank.getAccount(12345678).getType().equalsIgnoreCase("checking"));
    }

    @Test
    void issued_valid_create_cd() {
        CreateCommand cmd = new CreateCommand("create cd 23456789 1.0 500");
        processor.issue_command(cmd);
        assertTrue(bank.getAccount(23456789) != null && bank.getAccount(23456789).getAPR() == 1.0 && bank.getAccount(23456789).getType().equalsIgnoreCase("cd"));
    }

    @Test
    void issued_valid_create_savings() {
        CreateCommand cmd = new CreateCommand("create savings 34567890 1.0");
        processor.issue_command(cmd);
        assertTrue(bank.getAccount(34567890) != null && bank.getAccount(34567890).getAPR() == 1.0 && bank.getAccount(34567890).getType().equalsIgnoreCase("savings"));
    }

    @Test
    void issued_valid_deposit_to_empty_account() {
        CreateCommand create = new CreateCommand("create checking 12345678 1.0");
        processor.issue_command(create);
        DepositCommand deposit = new DepositCommand("deposit 12345678 100");
        processor.issue_command(deposit);
        assertTrue(bank.getAccount(12345678).getBalance() == 100);
    }

    @Test
    void issued_valid_deposit_to_account_with_existing_balance() {
        CreateCommand create = new CreateCommand("create checking 12345678 1.0");
        processor.issue_command(create);
        DepositCommand deposit = new DepositCommand("deposit 12345678 100");
        processor.issue_command(deposit);
        double bal = bank.getAccount(12345678).getBalance();
        DepositCommand deposit_again = new DepositCommand("deposit 12345678 100");
        processor.issue_command(deposit_again);
        assertTrue(bank.getAccount(12345678).getBalance() == bal + 100);

    }

    @Test
    void issue_valid_passtime_cmd() {
        bank.addAccount(new CheckingAccount(12345678, 5));
        bank.getAccount(12345678).setBalance(3000);
        PassCommand pass = new PassCommand("pass 12");
        processor.issue_command(pass);
        assertTrue(bank.getAccount(12345678).getBalance() > 3000);
    }

    @Test
    void transfer_removes_from_source_adds_to_target() {
        bank.addAccount(new CheckingAccount(12345678, 1));
        bank.getAccount(12345678).setBalance(1000);
        bank.addAccount(new SavingsAccount(23456789, 1));
        bank.transferFunds(bank.getAccount(12345678), bank.getAccount(23456789), 500);
        assertTrue(bank.getAccount(12345678).getBalance() == 500 && bank.getAccount(23456789).getBalance() == 500);
    }

    @Test
    void early_withdrawal_from_cd_fails() {
        Account a = new CDAccount(12345678, 1, 5000);
        bank.addAccount(a);
        processor.issue_command(new WithdrawalCommand("withdraw 12345678 5000"));
        assertTrue(a.getBalance() == 5000);
    }

    @Test
    void on_time_withdrawal_from_cd_succeeds() {
        Account a = new CDAccount(12345678, 1, 5000);
        bank.addAccount(a);
        bank.passTime(12);
        WithdrawalCommand w = new WithdrawalCommand("withdraw 12345678 15000");
        processor.issue_command(w);
        assertTrue(a.getBalance() == 0);
    }

    @Test
    void issue_valid_withdrawal_command() {
        Account a = new CheckingAccount(12345678, 1);
        bank.addAccount(a);
        a.setBalance(5000);
        Command w = new Command("withdraw 12345678 150");
        w = w.identify_type();
        processor.issue_command(w);
        assertTrue(a.getBalance() == 4850);
    }

    @Test
    void issue_untyped_command_identifies_and_issues_command() {
        Account a = new CheckingAccount(12345678, 1);
        bank.addAccount(a);
        a.setBalance(5000);
        Command w = new Command("withdraw 12345678 150");
        processor.issue_command(w);
        assertTrue(a.getBalance() == 4850);
    }
}
