package banking;

public class WithdrawalCommandValidator {
    public boolean withdrawal_target_is_int, withdrawal_amount_is_double, withdrawing_legal_amount,
            withdrawing_from_nonempty_account, not_flagged, withdrawing_from_real_account = false;

    private Bank bank;

    public WithdrawalCommandValidator(Bank b) {
        this.bank = b;
    }

    public boolean validate(Command c) {
        String[] payload = c.getPayload();
        String account_id = payload[0];
        String amount_str = payload[1];
        withdrawal_target_is_int = !account_id.matches(Validator.DIGITS);
        withdrawal_amount_is_double = !amount_str.matches(Validator.DIGITS);
        if (withdrawal_target_is_int && withdrawal_amount_is_double && (bank.getAccount(Integer.parseInt(account_id)) != null)) {
            withdrawing_from_real_account = true;
            Account account = bank.getAccount(Integer.parseInt(account_id));
            if (account.getBalance() > 0) {
                withdrawing_from_nonempty_account = true;
                if (account instanceof SavingsAccount) {
                    withdrawing_legal_amount = Double.parseDouble(amount_str) <= 1000;
                    not_flagged = !((SavingsAccount) account).withdrawalUsed();
                } else if (account instanceof CheckingAccount) {
                    withdrawing_legal_amount = Double.parseDouble(amount_str) <= 400;
                } else if (account instanceof CDAccount) {
                    not_flagged = account.age() >= 12;
                    withdrawing_legal_amount = Double.parseDouble(amount_str) >= account.getBalance();
                }
            }
        }
        return withdrawal_target_is_int && withdrawal_amount_is_double && withdrawing_legal_amount && withdrawing_from_nonempty_account && withdrawing_from_real_account;
    }
}
