package banking;

import java.util.Arrays;

public class DepositCommand extends Command {
    public DepositCommand(String c) {
        super(c);
        this.valid_instruction = "deposit";
        Integer deposit_payload_len[] = {2};
        this.valid_payload_length = Arrays.asList(deposit_payload_len);
    }
}
