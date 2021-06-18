package banking;

public class PassTimeCommandValidator {
    public boolean cmd_has_valid_instruction, cmd_has_valid_payload_size,
            passtime_payload_is_int, passtime_months_within_boundaries = false;
    private Bank bank;

    public PassTimeCommandValidator(Bank b) {
        this.bank = b;
    }

    public boolean validate(Command c) {
        String[] payload = c.getPayload();
        String shouldbe_months = payload[0];
        int shouldbe_months_int = Integer.parseInt(shouldbe_months);
        if (!shouldbe_months.matches(Validator.DIGITS)) {
            passtime_payload_is_int = true;
            if (shouldbe_months_int >= 1 && shouldbe_months_int <= 60) {
                passtime_months_within_boundaries = true;
            }
        }
        return cmd_has_valid_instruction && cmd_has_valid_payload_size && passtime_payload_is_int && passtime_months_within_boundaries;
    }
}