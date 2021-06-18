package banking;

import java.util.Arrays;

public class TransferCommand extends Command {
    public TransferCommand(String transferCommand) {
        super(transferCommand);
        this.valid_instruction = "transfer";
        Integer transfer_payload_len[] = {3};
        this.valid_payload_length = Arrays.asList(transfer_payload_len);
    }
}
