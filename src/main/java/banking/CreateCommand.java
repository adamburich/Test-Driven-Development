package banking;

import java.util.Arrays;

public class CreateCommand extends Command {

    public CreateCommand(String createCommand) {
        super(createCommand);
        this.valid_instruction = "create";
        this.valid_payload_length = Arrays.asList(new Integer[]{3, 4});
    }
}
