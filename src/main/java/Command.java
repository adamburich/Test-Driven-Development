import java.util.Arrays;
import java.util.List;

public class Command {

    private final List<String> VALID_INSTRUCTIONS = Arrays.asList(
            "create",
            "deposit"
    );

    private final List<String> VALID_ACCOUNT_TYPES = Arrays.asList(
            "checking",
            "savings",
            "cd"
    );

    public boolean invalid_or_missing_account_type = false;
    public boolean create_attempted_with_duplicate_id = false;
    public boolean malformed_id = false;
    public boolean attempt_to_access_nonexistant_account = false;
    public boolean malformed_initial_balance = false;
    public boolean cd_with_illegal_init_balance = false;
    public boolean create_savings_checking_with_invalid_arglength = false;
    public boolean create_has_malformed_apr = false;
    public boolean create_cd_with_invalid_arglength = false;
    public boolean create_with_illegal_apr_val = false;
    public boolean invalid_instruction = false;
    public boolean command_is_empty = false;


    private String instruction;
    private String[] payload;

    public Command(String cmd) {
        if (cmd.equals("")) {
            command_is_empty = true;
            return;
        }
        try {
            cmd = cmd.toLowerCase();
            String[] parts = cmd.split(" ");
            instruction = parts[0];
            payload = Arrays.copyOfRange(parts, 1, parts.length);
        } catch (ArrayIndexOutOfBoundsException e) {

        }
        this.validate_command();
    }

    public void give_valid_command_to_accountant() {

    }

    public boolean validate_command() {
        if (payload.length > 0) {
            if (instruction.equals("create")) {
                if (validate_create_payload_arg_length() && validate_account_type(payload[0]) && validate_account_id_usage(payload[1]) && validate_apr(payload[2])) {
                    if (payload[0].equals("cd")) {
                        if (validate_initial_cd_balance(payload[3])) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return true;
                    }
                } else {
                    return false;
                }
            } else if (instruction.equals("deposit")) {
                return true;
            } else {
                invalid_instruction = true;
                return false;
            }
        } else {
            return false;
        }

    }

    public boolean validate_create_payload_arg_length() {
        if (payload[0].equals("savings") || payload[0].equals("checking")) {
            if (payload.length != 3) {
                create_savings_checking_with_invalid_arglength = true;
                return false;
            } else {
                return true;
            }
        } else if (payload[0].equals("cd")) {
            if (payload.length != 4) {
                create_cd_with_invalid_arglength = true;
                return false;
            } else {
                return true;
            }
        } else {
            invalid_or_missing_account_type = true;
            return false;
        }
    }

    public boolean validate_account_type(String type) {
        if (VALID_ACCOUNT_TYPES.contains(type)) {
            return true;
        } else {
            invalid_or_missing_account_type = true;
            return false;
        }
    }

    public boolean validate_account_id_usage(String id) {
        if (id.length() == 8 && !id.matches(".*[a-z].*")) {
            int acc_id = Integer.parseInt(id);
            if (instruction.equals("create")) {
                if (Bank.getAccount(acc_id) == null) {
                    return true;
                } else {
                    create_attempted_with_duplicate_id = true;
                    return false;
                }
            } else {
                if (Bank.getAccount(acc_id) == null) {
                    attempt_to_access_nonexistant_account = true;
                    return false;
                } else {
                    return true;
                }
            }
        } else {
            malformed_id = true;
            return false;
        }
    }

    public boolean validate_initial_cd_balance(String bal) {
        if (!bal.matches(".*[a-z].*")) {
            double balance = Double.parseDouble(bal);
            if (balance >= 1000 && balance <= 10000) {
                return true;
            } else {
                cd_with_illegal_init_balance = true;
                return false;
            }
        } else {
            malformed_initial_balance = true;
            return false;
        }
    }

    public boolean validate_apr(String apr_str) {
        if (!apr_str.matches(".*[a-z].*")) {
            double apr = Double.parseDouble(apr_str);
            if (apr > 0 && apr <= 10) {
                return true;
            } else {
                create_with_illegal_apr_val = true;
                return false;
            }
        } else {
            create_has_malformed_apr = true;
            return false;
        }
    }

    public boolean deposit_command_has_valid_payload() {
        return false;
    }

    public boolean contains_valid_instruction(String str) {
        if (VALID_INSTRUCTIONS.contains(str)) {
            return true;
        } else {
            return false;
        }
    }

    public String getInstruction() {
        return instruction;
    }

    public boolean payload_is_invalid() {
        return invalid_or_missing_account_type || malformed_id || create_attempted_with_duplicate_id || attempt_to_access_nonexistant_account
                || malformed_initial_balance || cd_with_illegal_init_balance || create_savings_checking_with_invalid_arglength || create_has_malformed_apr || create_cd_with_invalid_arglength
                || create_with_illegal_apr_val;
    }

    public String[] getPayload() {
        return payload;
    }

}
