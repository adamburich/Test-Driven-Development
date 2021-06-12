package banking;

import java.util.Arrays;

public class transfer_cmd extends cmd {
    public transfer_cmd(String c) {
        super(c);
        this.valid_instruction = "transfer";
        Integer transfer_payload_len[] = {3};
        this.valid_payload_length = Arrays.asList(transfer_payload_len);
    }
}
