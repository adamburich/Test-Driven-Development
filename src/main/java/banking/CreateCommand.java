package banking;

import java.util.Arrays;

public class CreateCommand extends Command {

    public CreateCommand(String createCommand) {
        super(createCommand);
        this.valid_instruction = "create";
        Integer create_payload_len[] = {3, 4};
        this.valid_payload_length = Arrays.asList(create_payload_len);
    }
}
