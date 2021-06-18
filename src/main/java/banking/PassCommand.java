package banking;

import java.util.Arrays;

public class PassCommand extends Command {
    public PassCommand(String passTimeCommand) {
        super(passTimeCommand);
        this.valid_instruction = "pass";
        this.valid_payload_length = Arrays.asList(1);
    }
}