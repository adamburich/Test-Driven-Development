package banking;

public class PassTimeCommandValidator {
    public boolean cmd_has_valid_instruction, cmd_has_valid_payload_size, valid_id_format,
            valid_apr_given, valid_init_balance, valid_type_given, id_is_not_duplicate,
            accessing_existing_id, account_supports_deposits, valid_deposit_amount,
            passtime_payload_is_int, passtime_months_within_boundaries, withdrawal_target_is_int,
            withdrawal_amount_is_double, savings_monthly_withdrawal_not_used, withdrawing_legal_amount,
            withdrawing_from_nonempty_account, withdrawing_from_cd_account_of_age, withdrawing_from_real_account,
            transfer_source_is_int, transfer_target_is_int, transfer_source_account_exists,
            transfer_target_account_exists, transfer_among_legal_types, transfer_amount_is_double = false;
    private Bank bank;

    public PassTimeCommandValidator(Bank b) {
        this.bank = b;
    }

    public boolean validate(Command c) {
        String[] payload = c.getPayload();
        String shouldbe_months = payload[0];
        int shouldbe_months_int = Integer.parseInt(shouldbe_months);
        if (!shouldbe_months.matches(".*[a-z].*")) {
            passtime_payload_is_int = true;
            if (shouldbe_months_int >= 1 && shouldbe_months_int <= 60) {
                passtime_months_within_boundaries = true;
            }
        }
        return cmd_has_valid_instruction && cmd_has_valid_payload_size && passtime_payload_is_int && passtime_months_within_boundaries;
    }
}
