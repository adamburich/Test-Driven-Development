package banking;

import java.util.Arrays;
import java.util.List;

public class Validator {

    private final List<String> VALID_ACCOUNT_TYPES = Arrays.asList(
            "checking",
            "savings",
            "cd"
    );
    public boolean cmd_has_valid_instruction, cmd_has_valid_payload_size, valid_id_format,
            valid_apr_given, valid_init_balance, valid_type_given, id_is_not_duplicate,
            accessing_existing_id, account_supports_deposits, valid_deposit_amount,
            passtime_payload_is_int, passtime_months_within_boundaries, withdrawal_target_is_int,
            withdrawal_amount_is_double, savings_monthly_withdrawal_not_used, withdrawing_legal_amount,
            withdrawing_from_nonempty_account, withdrawing_from_cd_account_of_age, withdrawing_from_real_account,
            transfer_source_is_int, transfer_target_is_int, transfer_source_account_exists,
            transfer_target_account_exists, transfer_among_legal_types, transfer_amount_is_double = false;

    private Bank bank;

    public Validator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(Command c) {
        if (c.getInstruction().equalsIgnoreCase(c.getValidInstruction())) {
            cmd_has_valid_instruction = true;
            if (c.getValidPayloadLength().contains(c.getPayload().length)) {
                cmd_has_valid_payload_size = true;
                String[] payload = c.getPayload();
                if (c instanceof CreateCommand) {
                    String shouldbe_type = payload[0];
                    String shouldbe_id = payload[1];
                    String shouldbe_apr = payload[2];
                    String shouldbe_balance = null;

                    if (payload.length == 4) {
                        shouldbe_balance = payload[3];
                    }
                    if (VALID_ACCOUNT_TYPES.contains(shouldbe_type.toLowerCase())) {
                        valid_type_given = true;
                        if (!shouldbe_id.matches(".*[a-z].*") && shouldbe_id.length() == 8) {
                            valid_id_format = true;
                            if (bank.getAccount(Integer.parseInt(shouldbe_id)) == null) {
                                id_is_not_duplicate = true;
                            }
                        }
                        if (!shouldbe_apr.matches(".*[a-z].*") && (Double.parseDouble(shouldbe_apr) >= 0 && Double.parseDouble(shouldbe_apr) <= 10)) {
                            valid_apr_given = true;
                        }
                        if (shouldbe_type.equals("cd")) {
                            if (shouldbe_balance != null && !shouldbe_balance.matches(".*[a-z].*") && (Double.parseDouble(shouldbe_balance) >= 0 && Double.parseDouble(shouldbe_balance) <= 10000)) {
                                valid_init_balance = true;
                            }
                        } else {
                            if (shouldbe_balance != null) {
                                cmd_has_valid_payload_size = false;
                            } else {
                                valid_init_balance = true;
                            }
                        }
                    }
                    return id_is_not_duplicate && cmd_has_valid_instruction && cmd_has_valid_payload_size && valid_type_given && valid_id_format && valid_apr_given && valid_init_balance;
                } else if (c instanceof DepositCommand) {
                    String shouldbe_id = payload[0];
                    String shouldbe_amount = payload[1];
                    if (!shouldbe_id.matches(".*[a-z].*") && shouldbe_id.length() == 8) {
                        valid_id_format = true;
                        Account target = bank.getAccount(Integer.parseInt(shouldbe_id));
                        if (target != null) {
                            accessing_existing_id = true;
                        }
                        if (!(target instanceof CDAccount)) {
                            account_supports_deposits = true;
                        }
                        if (target instanceof CheckingAccount) {
                            if (Integer.parseInt(shouldbe_amount) >= 0 && Integer.parseInt(shouldbe_amount) <= 1000) {
                                valid_deposit_amount = true;
                            }
                        }
                        if (target instanceof SavingsAccount) {
                            if (Integer.parseInt(shouldbe_amount) >= 0 && Integer.parseInt(shouldbe_amount) <= 2500) {
                                valid_deposit_amount = true;
                            }
                        }
                    }
                    return cmd_has_valid_instruction && cmd_has_valid_payload_size && valid_id_format && accessing_existing_id && valid_deposit_amount && account_supports_deposits;
                } else if (c instanceof PassCommand) {
                    String shouldbe_months = payload[0];
                    int shouldbe_months_int = Integer.parseInt(shouldbe_months);
                    if (!shouldbe_months.matches(".*[a-z].*")) {
                        passtime_payload_is_int = true;
                        if (shouldbe_months_int >= 1 && shouldbe_months_int <= 60) {
                            passtime_months_within_boundaries = true;
                        }
                    }
                    return cmd_has_valid_instruction && cmd_has_valid_payload_size && passtime_payload_is_int && passtime_months_within_boundaries;
                } else if (c instanceof WithdrawalCommand) {
                    String account_id = payload[0];
                    String amount_str = payload[1];
                    Double amount = Double.parseDouble(amount_str);
                    if (!account_id.matches(".*[a-z].*")) {
                        withdrawal_target_is_int = true;
                    }
                    if (!amount_str.matches(".*[a-z].*")) {
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
                } else if (c instanceof TransferCommand) {
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
                                if (validate(withdrawal) && validate(deposit)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

}
