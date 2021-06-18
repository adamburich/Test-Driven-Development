package banking;

import java.util.Arrays;

public class DepositCommand extends Command {
    public DepositCommand(String depositCommand) {
        super(depositCommand);
        this.valid_instruction = "deposit";
        this.valid_payload_length = Arrays.asList(2);
    }
}
