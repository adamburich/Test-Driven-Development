package banking;

import java.util.Arrays;

public class withdrawal_cmd extends cmd {
    public withdrawal_cmd(String c) {
        super(c);
        this.valid_instruction = "withdraw";
        Integer withdraw_payload_len[] = {2};
        this.valid_payload_length = Arrays.asList(withdraw_payload_len);
    }
}