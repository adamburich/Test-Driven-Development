package banking;

import java.util.Arrays;

public class WithdrawalCommand extends Command {
    public WithdrawalCommand(String withdrawalCommand) {
        super(withdrawalCommand);
        this.valid_instruction = "withdraw";
        this.valid_payload_length = Arrays.asList(2);
    }
}