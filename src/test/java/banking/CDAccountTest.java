package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CDAccountTest {
    public static final int ID = 12345678;
    public static final double APR = .05;
    public static final double BALANCE = 500;
    public static final String TYPE = "CD";
    Bank bank = new Bank();
    Account account;

    @BeforeEach
    void setup() {
        account = new CDAccount(ID, APR, BALANCE);
        bank.addAccount(account);
    }

    @Test
    void account_initialized_with_500_dollars_has_balance_500() {
        assertEquals(bank.getAccount(ID).getBalance(), 500);
    }

    @Test
    void apr_increases_balance_correctly() {
        bank.passTime(12);
        //Calculation by hand + calculator shows the result to be 512.1449028799084
        double correct_balance = 512.1449028799084;
        assertEquals(bank.getAccount(ID).getBalance(), correct_balance);
    }
}
