package banking;

import java.util.Arrays;

public class PassCommand extends Command {
    public PassCommand(String passTimeCommand) {
        super(passTimeCommand);
        this.valid_instruction = "pass";
        Integer passtime_payload_len[] = {1};
        this.valid_payload_length = Arrays.asList(passtime_payload_len);
    }
}