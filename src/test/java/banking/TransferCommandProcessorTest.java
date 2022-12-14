package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransferCommandProcessorTest {
    TransferCommandProcessor tcp;
    Bank bank = new Bank();

    @BeforeEach
    void setup() {
        tcp = new TransferCommandProcessor(bank);
    }

    @Test
    void transfer_event_occurs_on_command_issue() {
        CheckingAccount ca = new CheckingAccount(12345678, .05);
        SavingsAccount sa = new SavingsAccount(23456789, .05);
        bank.addAccount(ca);
        bank.addAccount(sa);
        ca.deposit(401);
        sa.deposit(500);
        tcp.issue_command(new TransferCommand("transfer 12345678 23456789 400"));
        assertEquals(sa.getBalance() == 900, ca.getBalance() == 1);
    }

    @Test
    void transfer_doesnt_create_magic_money() {
        CheckingAccount c1 = new CheckingAccount(12345678, 1);
        CheckingAccount c2 = new CheckingAccount(12345679, 1);
        bank.addAccount(c1);
        bank.addAccount(c2);
        c1.setBalance(300);
        tcp.issue_command(new TransferCommand("transfer 12345678 12345679 500"));
        assertEquals(c2.getBalance() == 300, c1.getBalance() == 0);
    }

    @Test
    void transfer_whole_balance_works() {
        CheckingAccount c1 = new CheckingAccount(12345678, 1);
        CheckingAccount c2 = new CheckingAccount(12345679, 1);
        bank.addAccount(c1);
        bank.addAccount(c2);
        c1.setBalance(300);
        tcp.issue_command(new TransferCommand("transfer 12345678 12345679 300"));
        assertEquals(c2.getBalance() == 300, c1.getBalance() == 0);

    }

    @Test
    void transfer_appears_in_source_transaction_history() {
        CheckingAccount c1 = new CheckingAccount(12345678, 1);
        CheckingAccount c2 = new CheckingAccount(12345679, 1);
        bank.addAccount(c1);
        bank.addAccount(c2);
        c1.setBalance(300);
        tcp.issue_command(new TransferCommand("transfer 12345678 12345679 300"));
        assertTrue(c1.transactionHistory().size() > 0);

    }

    @Test
    void transfer_appears_in_target_transaction_history() {
        CheckingAccount c1 = new CheckingAccount(12345678, 1);
        CheckingAccount c2 = new CheckingAccount(12345679, 1);
        bank.addAccount(c1);
        bank.addAccount(c2);
        c1.setBalance(300);
        tcp.issue_command(new TransferCommand("transfer 12345678 12345679 300"));
        assertTrue(c2.transactionHistory().size() > 0);

    }


}
