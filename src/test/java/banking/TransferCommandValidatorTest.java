package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TransferCommandValidatorTest {
    TransferCommandValidator tcv;
    Bank bank = new Bank();

    @BeforeEach
    void setup() {
        tcv = new TransferCommandValidator(bank);
    }

    @Test
    void valid_and_invalid_are_distinguishable() {
        CheckingAccount ca = new CheckingAccount(12345678, .05);
        bank.addAccount(ca);
        ca.setBalance(1000);
        CheckingAccount otherca = new CheckingAccount(23456789, .05);
        bank.addAccount(otherca);
        ca.deposit(500);
        boolean valid = tcv.validate(new TransferCommand("transfer 12345678 23456789 50"));
        boolean invalid = tcv.validate(new TransferCommand("transfer 1 2 3"));
        assertEquals(valid, !invalid);
    }

    @Test
    void transfer_involving_CD_is_invalid() {
        CDAccount acc = new CDAccount(87654321, 1, 1000);
        bank.addAccount(acc);
        CheckingAccount other = new CheckingAccount(12345678, 1);
        bank.addAccount(other);
        other.setBalance(500);
        boolean cd_source = tcv.validate(new TransferCommand("transfer 87654321 12345678 100"));
        boolean cd_target = tcv.validate(new TransferCommand("transfer 12345678 87654321 100"));
        assertFalse(cd_source && cd_target);
    }
}
