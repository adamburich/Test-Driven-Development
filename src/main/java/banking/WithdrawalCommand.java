package banking;

import java.util.Arrays;

public class WithdrawalCommand extends Command {
    public WithdrawalCommand(String c) {
        super(c);
        this.valid_instruction = "withdraw";
        Integer withdraw_payload_len[] = {2};
        this.valid_payload_length = Arrays.asList(withdraw_payload_len);
    }
}