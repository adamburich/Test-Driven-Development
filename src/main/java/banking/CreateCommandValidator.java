package banking;

import java.util.Arrays;
import java.util.List;

public class CreateCommandValidator {
    private final List<String> VALID_ACCOUNT_TYPES = Arrays.asList(
            "checking",
            "savings",
            "cd"
    );
    public boolean valid_id_format,
            valid_apr_given, valid_init_balance, valid_type_given, id_is_not_duplicate = false;
    private Bank bank;

    public CreateCommandValidator(Bank b) {
        this.bank = b;
    }

    public boolean validate(Command c) {
        String[] payload = c.getPayload();
        String shouldbe_type = payload[0].toLowerCase();
        String shouldbe_id = payload[1];
        String shouldbe_apr = payload[2];
        String shouldbe_balance = null;
        if (payload.length == 4) {
            shouldbe_balance = payload[3];
        }
        valid_type_given = VALID_ACCOUNT_TYPES.contains(shouldbe_type);
        if (!shouldbe_id.matches(Validator.DIGITS) && shouldbe_id.length() == 8) {
            int id_int = Integer.parseInt(shouldbe_id);
            valid_id_format = true;
            id_is_not_duplicate = (bank.getAccount(id_int) == null);
        }
        valid_apr_given = !shouldbe_apr.matches(Validator.DIGITS) && (Double.parseDouble(shouldbe_apr) >= 0 && Double.parseDouble(shouldbe_apr) <= 10);
        if (shouldbe_type.equals("cd") && (shouldbe_balance != null && !shouldbe_balance.matches(Validator.DIGITS) && (Double.parseDouble(shouldbe_balance) >= 0 && Double.parseDouble(shouldbe_balance) <= 10000))) {
            valid_init_balance = true;
        } else if (!shouldbe_type.equals("cd") && shouldbe_balance == null) {
            valid_init_balance = true;
        }
        return id_is_not_duplicate && valid_type_given && valid_id_format && valid_apr_given && valid_init_balance;
    }
}
