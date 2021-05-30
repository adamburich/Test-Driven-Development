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
            passtime_payload_is_int, passtime_months_within_boundaries = false;

    private Bank bank;

    public Validator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(cmd c) {
        if (c.getInstruction().equalsIgnoreCase(c.getValidInstruction())) {
            cmd_has_valid_instruction = true;
            if (c.getValidPayloadLength().contains(c.getPayload().length)) {
                cmd_has_valid_payload_size = true;
                String[] payload = c.getPayload();
                if (c instanceof create_cmd) {
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
                } else if (c instanceof deposit_cmd) {
                    String shouldbe_id = payload[0];
                    String shouldbe_amount = payload[1];
                    if (!shouldbe_id.matches(".*[a-z].*") && shouldbe_id.length() == 8) {
                        valid_id_format = true;
                        Account target = bank.getAccount(Integer.parseInt(shouldbe_id));
                        if (target != null) {
                            accessing_existing_id = true;
                        }
                        if (target instanceof CDAccount) {
                            account_supports_deposits = true;
                        } else if (target instanceof CheckingAccount) {
                            if (Integer.parseInt(shouldbe_amount) >= 0 && Integer.parseInt(shouldbe_amount) <= 1000) {
                                valid_deposit_amount = true;
                            }
                        } else if (target instanceof SavingsAccount) {
                            if (Integer.parseInt(shouldbe_amount) >= 0 && Integer.parseInt(shouldbe_amount) <= 2500) {
                                valid_deposit_amount = true;
                            }
                        }
                    }
                    return cmd_has_valid_instruction && cmd_has_valid_payload_size && valid_id_format && accessing_existing_id && valid_deposit_amount && account_supports_deposits;
                } else if (c instanceof passtime_cmd){
                    String shouldbe_months = payload[0];
                    int shouldbe_months_int = Integer.parseInt(shouldbe_months);
                    if (!shouldbe_months.matches(".*[a-z].*")){
                        passtime_payload_is_int = true;
                        if (shouldbe_months_int >= 1 && shouldbe_months_int <= 60){
                            passtime_months_within_boundaries = true;
                        }
                    }
                    return cmd_has_valid_instruction && cmd_has_valid_payload_size && passtime_payload_is_int && passtime_months_within_boundaries;
                }
            }
        }
        return false;
    }

}
