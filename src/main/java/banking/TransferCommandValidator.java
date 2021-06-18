package banking;

public class TransferCommandValidator {

    public boolean transfer_source_is_int, transfer_target_is_int, transfer_source_account_exists, withdrawal_and_deposit_validate,
            transfer_target_account_exists, transfer_among_legal_types, transfer_amount_is_double = false;
    private Bank bank;

    private WithdrawalCommandValidator withdrawalCommandValidator;
    private DepositCommandValidator depositCommandValidator;

    public TransferCommandValidator(Bank b) {
        this.bank = b;
        this.withdrawalCommandValidator = new WithdrawalCommandValidator(bank);
        this.depositCommandValidator = new DepositCommandValidator(bank);
    }

    public boolean validate(Command c) {
        String[] payload = c.getPayload();
        String transfer_source = payload[0];
        String transfer_target = payload[1];
        String amount_string = payload[2];
        String withdraw_cmd_string = "withdraw " + transfer_source + " " + amount_string;
        String deposit_cmd_string = "deposit " + transfer_target + " " + amount_string;
        transfer_source_is_int = !transfer_source.matches(Validator.DIGITS);
        transfer_target_is_int = !transfer_target.matches(Validator.DIGITS);
        transfer_amount_is_double = !amount_string.matches(Validator.DIGITS);
        if (transfer_amount_is_double && transfer_target_is_int && transfer_source_is_int) {
            transfer_source_account_exists = (bank.getAccount(Integer.parseInt(transfer_source)) != null);
            transfer_target_account_exists = (bank.getAccount(Integer.parseInt(transfer_target)) != null);
            if (transfer_source_account_exists && transfer_target_account_exists && (!(bank.getAccount(Integer.parseInt(transfer_target)) instanceof CDAccount) && !(bank.getAccount(Integer.parseInt(transfer_source)) instanceof CDAccount))) {
                transfer_among_legal_types = true;
                WithdrawalCommand withdrawal = new WithdrawalCommand(withdraw_cmd_string);
                DepositCommand deposit = new DepositCommand(deposit_cmd_string);
                if (withdrawalCommandValidator.validate(withdrawal) && depositCommandValidator.validate(deposit)) {
                    withdrawal_and_deposit_validate = true;
                }

            }
        }
        return withdrawal_and_deposit_validate && transfer_among_legal_types && transfer_amount_is_double && transfer_target_is_int && transfer_source_is_int && transfer_source_account_exists && transfer_target_account_exists;
    }
}
