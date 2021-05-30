package banking;

import java.util.Arrays;

public class deposit_cmd extends cmd {
    public deposit_cmd(String c) {
        super(c);
        this.valid_instruction = "deposit";
        Integer deposit_payload_len[] = {2};
        this.valid_payload_length = Arrays.asList(deposit_payload_len);
    }
}
