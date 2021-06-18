package banking;

import java.util.Arrays;

public class TransferCommand extends Command {
    public TransferCommand(String transferCommand) {
        super(transferCommand);
        this.valid_instruction = "transfer";
        this.valid_payload_length = Arrays.asList(3);
    }
}
