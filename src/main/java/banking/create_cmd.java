package banking;

import java.util.Arrays;

public class create_cmd extends cmd {

    public create_cmd(String c) {
        super(c);
        this.valid_instruction = "create";
        Integer create_payload_len[] = {3, 4};
        this.valid_payload_length = Arrays.asList(create_payload_len);
    }
}
