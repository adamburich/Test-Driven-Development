package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WithdrawalCommandValidatorTest {
    WithdrawalCommandValidator wcv;
    SavingsAccount sa;
    CheckingAccount ca;
    CDAccount cd;
    Bank bank = new Bank();

    @BeforeEach
    void setup() {
        wcv = new WithdrawalCommandValidator(bank);
        cd = new CDAccount(12345678, .05, 0);
        ca = new CheckingAccount(87654321, .05);
        sa = new SavingsAccount(23456789, .05);
        bank.addAccount(cd);
        bank.addAccount(sa);
        bank.addAccount(ca);
    }

    @Test
    void attempt_to_withdraw_from_empty_cd_account_is_invalid() {
        WithdrawalCommand wc_cd = new WithdrawalCommand("withdraw 12345678 500");
        assertEquals(wcv.validate(wc_cd), false);
    }

    @Test
    void attempt_to_withdraw_from_empty_savings_account_is_invalid() {
        WithdrawalCommand wc_sa = new WithdrawalCommand("withdraw 23456789 500");
        assertEquals(wcv.validate(wc_sa), false);
    }

    @Test
    void attempt_to_withdraw_from_empty_checking_account_is_invalid() {
        WithdrawalCommand wc_ca = new WithdrawalCommand("withdraw 87654321 500");
        assertEquals(wcv.validate(wc_ca), false);
    }

    @Test
    void attempt_to_withdraw_illegal_amount_is_invalid() {
        ca.setBalance(25000);
        sa.setBalance(25000);
        cd.setBalance(25000);
        bank.passTime(12);
        WithdrawalCommand wc_cd = new WithdrawalCommand("withdraw 12345678 " + (cd.getBalance() - 1));
        WithdrawalCommand wc_sa = new WithdrawalCommand("withdraw 23456789 1001");
        WithdrawalCommand wc_ca = new WithdrawalCommand("withdraw 87654321 401");
        boolean all_false = false;
        all_false = (wcv.validate(wc_cd) == wcv.validate(wc_sa) == wcv.validate(wc_ca)) == false;
        assertEquals(all_false, true);
    }

    @Test
    void attempt_to_withdraw_legal_amount_is_valid() {
        ca.setBalance(25000);
        sa.setBalance(25000);
        cd.setBalance(25000);
        bank.passTime(12);
        WithdrawalCommand wc_cd = new WithdrawalCommand("withdraw 12345678 " + (cd.getBalance()));
        WithdrawalCommand wc_sa = new WithdrawalCommand("withdraw 23456789 1000");
        WithdrawalCommand wc_ca = new WithdrawalCommand("withdraw 87654321 400");
        boolean all_true = false;
        all_true = (wcv.validate(wc_cd) == wcv.validate(wc_sa) == wcv.validate(wc_ca)) == true;
        assertEquals(all_true, true);
    }

    @Test
    void attempt_to_withdraw_twice_from_savings_one_month_is_invalid() {
        sa.setBalance(25000);
        WithdrawalCommand wc_sa = new WithdrawalCommand("withdraw 23456789 500");
        sa.withdraw(500);
        assertFalse(wcv.validate(wc_sa));
    }

    @Test
    void attempt_to_withdraw_twice_from_savings_two_month_is_valid() {
        sa.setBalance(25000);
        WithdrawalCommand wc_sa = new WithdrawalCommand("withdraw 23456789 500");
        sa.withdraw(500);
        bank.passTime(1);
        assertTrue(wcv.validate(wc_sa));
    }
}
