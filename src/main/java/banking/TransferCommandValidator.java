package banking;

public class TransferCommandValidator {

    public boolean transfer_source_is_int, transfer_target_is_int, transfer_source_account_exists, withdrawal_and_deposit_validate,
            transfer_target_account_exists, transfer_among_legal_types, transfer_amount_is_double = false;
    private Bank bank;

    private WithdrawalCommandValidator withdrawalCommandValidator = new WithdrawalCommandValidator(bank);
    private DepositCommandValidator depositCommandValidator = new DepositCommandValidator(bank);

    public TransferCommandValidator(Bank b) {
        this.bank = b;
    }

    public boolean validate(Command c) {
        String[] payload = c.getPayload();
        String transfer_source = payload[0];
        String transfer_target = payload[1];
        String amount_string = payload[2];
        String withdraw_cmd_string = "withdraw " + transfer_source + " " + amount_string;
        String deposit_cmd_string = "deposit " + transfer_target + " " + amount_string;
        if (!transfer_source.matches(".*[a-z].*")) {
            transfer_source_is_int = true;
        }
        if (!transfer_target.matches(".*[a-z].*")) {
            transfer_target_is_int = true;
        }
        if (!amount_string.matches(".*[a-z].*")) {
            transfer_amount_is_double = true;
        }
        if (transfer_amount_is_double && transfer_target_is_int && transfer_source_is_int) {
            if (bank.getAccount(Integer.parseInt(transfer_source)) != null) {
                transfer_source_account_exists = true;
            }
            if (bank.getAccount(Integer.parseInt(transfer_target)) != null) {
                transfer_target_account_exists = true;
            }
            if (transfer_source_account_exists && transfer_target_account_exists) {
                if (!(bank.getAccount(Integer.parseInt(transfer_target)) instanceof CDAccount) && !(bank.getAccount(Integer.parseInt(transfer_source)) instanceof CDAccount)) {
                    transfer_among_legal_types = true;
                    WithdrawalCommand withdrawal = new WithdrawalCommand(withdraw_cmd_string);
                    DepositCommand deposit = new DepositCommand(deposit_cmd_string);
                    if (withdrawalCommandValidator.validate(withdrawal) && depositCommandValidator.validate(deposit)) {
                        withdrawal_and_deposit_validate = true;
                    }
                }
            }
        }
        return withdrawal_and_deposit_validate && transfer_among_legal_types && transfer_amount_is_double && transfer_target_is_int && transfer_source_is_int && transfer_source_account_exists && transfer_target_account_exists;
    }
}
