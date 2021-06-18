package banking;

import java.util.Arrays;
import java.util.List;

public class CreateCommandValidator {
    private final List<String> VALID_ACCOUNT_TYPES = Arrays.asList(
            "checking",
            "savings",
            "cd"
    );
    public boolean cmd_has_valid_instruction, cmd_has_valid_payload_size, valid_id_format,
            valid_apr_given, valid_init_balance, valid_type_given, id_is_not_duplicate = false;
    private Bank bank;

    public CreateCommandValidator(Bank b) {
        this.bank = b;
    }

    public boolean validate(Command c) {
        String[] payload = c.getPayload();
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
    }
}
