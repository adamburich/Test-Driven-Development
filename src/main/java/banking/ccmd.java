package banking;

import java.util.Arrays;

public class ccmd extends cmd {

    public ccmd(String c) {
        super(c);
        this.valid_instruction = "create";
        Integer create_payload_len[] = {3, 4};
        this.valid_payload_length = Arrays.asList(create_payload_len);
    }
}
