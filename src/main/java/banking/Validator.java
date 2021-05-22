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
            accessing_existing_id, account_supports_deposits, valid_deposit_amount = false;

    private Bank bank;

    public Validator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(cmd c) {
        if (c.getInstruction().equalsIgnoreCase(c.getValidInstruction())) {
            cmd_has_valid_instruction = true;
            if (c.getValidPayloadLength().contains(c.getPayload().length)) {
                cmd_has_valid_payload_size = true;
                if (c instanceof ccmd) {
                    String shouldbe_type = c.getPayload()[0];
                    String shouldbe_id = c.getPayload()[1];
                    String shouldbe_apr = c.getPayload()[2];
                    String shouldbe_balance = null;
                    if (c.getPayload().length == 4) {
                        shouldbe_balance = c.getPayload()[3];
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
                } else if (c instanceof dcmd) {
                    String shouldbe_id = c.getPayload()[0];
                    String shouldbe_amount = c.getPayload()[1];
                    if (!shouldbe_id.matches(".*[a-z].*") && shouldbe_id.length() == 8) {
                        valid_id_format = true;
                        if (bank.getAccount(Integer.parseInt(shouldbe_id)) != null) {
                            accessing_existing_id = true;
                        }
                        if (!bank.getAccount(Integer.parseInt(shouldbe_id)).getType().equals("cd")) {
                            account_supports_deposits = true;
                        } else if (bank.getAccount(Integer.parseInt(shouldbe_id)).getType().equals("checking")) {
                            if (Integer.parseInt(shouldbe_amount) >= 0 && Integer.parseInt(shouldbe_amount) <= 1000) {
                                valid_deposit_amount = true;
                            }
                        } else if (bank.getAccount(Integer.parseInt(shouldbe_id)).getType().equals("savings")) {
                            if (Integer.parseInt(shouldbe_amount) >= 0 && Integer.parseInt(shouldbe_amount) <= 2500) {
                                valid_deposit_amount = true;
                            }
                        }
                    }
                    return cmd_has_valid_instruction && cmd_has_valid_payload_size && valid_id_format && accessing_existing_id && valid_deposit_amount && account_supports_deposits;
                }
            }
        }
        return false;
    }

}
