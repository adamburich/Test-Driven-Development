package banking;

public class WithdrawalCommandValidator {
    public boolean withdrawal_target_is_int, withdrawal_amount_is_double, savings_monthly_withdrawal_not_used, withdrawing_legal_amount,
            withdrawing_from_nonempty_account, withdrawing_from_cd_account_of_age, withdrawing_from_real_account = false;

    private Bank bank;

    public WithdrawalCommandValidator(Bank b) {
        this.bank = b;
    }

    public boolean validate(Command c) {
        String[] payload = c.getPayload();
        String account_id = payload[0];
        String amount_str = payload[1];
        Double amount = Double.parseDouble(amount_str);
        if (!account_id.matches(Validator.DIGITS)) {
            withdrawal_target_is_int = true;
        }
        if (!amount_str.matches(Validator.DIGITS)) {
            withdrawal_amount_is_double = true;
        }
        if (withdrawal_target_is_int && withdrawal_amount_is_double) {
            if (bank.getAccount(Integer.parseInt(account_id)) != null) {
                withdrawing_from_real_account = true;
                Account account = bank.getAccount(Integer.parseInt(account_id));
                if (account.getBalance() > 0) {
                    withdrawing_from_nonempty_account = true;
                    if (account instanceof SavingsAccount) {
                        if (amount <= 1000) {
                            withdrawing_legal_amount = true;
                        }
                        if (!((SavingsAccount) account).withdrawalUsed()) {
                            savings_monthly_withdrawal_not_used = true;
                        }
                        return withdrawing_legal_amount && savings_monthly_withdrawal_not_used;
                    } else if (account instanceof CheckingAccount) {
                        if (amount <= 400) {
                            withdrawing_legal_amount = true;
                        }
                        return withdrawing_legal_amount;
                    } else if (account instanceof CDAccount) {
                        if (account.age() >= 12) {
                            withdrawing_from_cd_account_of_age = true;
                        }
                        if (amount >= account.getBalance()) {
                            withdrawing_legal_amount = true;
                        }
                        return withdrawing_from_cd_account_of_age && withdrawing_legal_amount;
                    }
                }

            }
        }
        return withdrawal_target_is_int && withdrawal_amount_is_double && savings_monthly_withdrawal_not_used && withdrawing_legal_amount && withdrawing_from_nonempty_account && withdrawing_from_cd_account_of_age && withdrawing_from_real_account;
    }
}
