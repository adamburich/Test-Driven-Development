import java.util.Arrays;
import java.util.List;

public class DepositCommand extends Command {

    private final String VALID_INSTRUCTION = "deposit";
    private final List<String> VALID_ACCOUNT_TYPES = Arrays.asList(
            "checking",
            "savings",
            "cd"
    );
    public boolean
            invalid_or_missing_account_type,
            malformed_id,
            attempt_to_access_nonexistant_account,
            invalid_instruction,
            command_is_empty,
            attempted_deposit_to_cd_account,
            deposit_to_savings_is_illegal_amount,
            deposit_to_checking_is_illegal_amount,
            deposit_attempted_to_nonexistant_account,
            malformed_deposit_amount,
            attempted_to_deposit_negative,
            create_attempted_with_duplicate_id
                    = false;
    private String instruction;
    private String[] payload;

    public DepositCommand(String cmd) {
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

    private boolean validate_deposit() {
        if (validate_deposit_payload_arg_length() && validate_account_id_usage(payload[0])) {
            if (deposit_follows_deposit_rules()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean deposit_follows_deposit_rules() {
        Account account = Bank.getAccount(Integer.parseInt(payload[0]));
        if (account == null) {
            deposit_attempted_to_nonexistant_account = true;
            return false;
        } else {
            String account_type = account.getType().toLowerCase();
            if (payload[1].matches(".*[a-z].*")) {
                malformed_deposit_amount = true;
                return false;
            } else {
                Double deposit_amount = Double.parseDouble(payload[1]);
                if (account_type.equals("savings")) {
                    if (deposit_amount <= 2500 && deposit_amount >= 0) {
                        return true;
                    } else if (deposit_amount < 0) {
                        attempted_to_deposit_negative = true;
                        return false;
                    } else {
                        deposit_to_savings_is_illegal_amount = true;
                        return false;
                    }
                } else if (account_type.equals("checking")) {
                    if (deposit_amount <= 1000 && deposit_amount >= 0) {
                        return true;
                    } else if (deposit_amount < 0) {
                        attempted_to_deposit_negative = true;
                        return false;
                    } else {
                        deposit_to_checking_is_illegal_amount = true;
                        return false;
                    }
                } else if (account_type.equals("cd")) {
                    attempted_deposit_to_cd_account = true;
                    return false;
                } else {
                    return false;
                }
            }
        }

    }

    public boolean validate_deposit_payload_arg_length() {
        if (payload.length == 2) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deposit_payload_is_invalid() {
        return invalid_or_missing_account_type || malformed_id || attempt_to_access_nonexistant_account || !this.validate_deposit_payload_arg_length();
    }

    @Override
    public boolean contains_valid_instruction(String str) {
        if (str.equals(VALID_INSTRUCTION)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validate_command() {
        if (payload.length > 0) {
            if (instruction.equals("deposit")) {
                return this.validate_deposit();
            } else {
                invalid_instruction = true;
                return false;
            }
        } else {
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

    @Override
    public String[] getPayload() {
        return payload;
    }

    @Override
    public String getInstruction() {
        return instruction;
    }
}
