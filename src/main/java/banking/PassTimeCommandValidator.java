package banking;

public class PassTimeCommandValidator {

    public boolean cmd_has_valid_instruction, cmd_has_valid_payload_size,
            passtime_payload_is_int, passtime_months_within_boundaries = false;

    public PassTimeCommandValidator() {
        //Doesn't need fill
    }

    public boolean validate(Command c) {
        String[] payload = c.getPayload();
        String shouldbe_months = payload[0];
        if (!shouldbe_months.matches(Validator.DIGITS)) {
            passtime_payload_is_int = true;
            if (Integer.parseInt(shouldbe_months) >= 1 && Integer.parseInt(shouldbe_months) <= 60) {
                passtime_months_within_boundaries = true;
            }
        }
        return cmd_has_valid_instruction && cmd_has_valid_payload_size && passtime_payload_is_int && passtime_months_within_boundaries;
    }
}
