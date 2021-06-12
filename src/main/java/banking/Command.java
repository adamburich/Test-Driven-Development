package banking;

import java.util.Arrays;
import java.util.List;

public class Command {
    public boolean command_is_empty = false;
    public List<Integer> valid_payload_length = null;
    public String valid_instruction = null;
    public String init_string = null;
    private String instruction = null;
    private String[] payload = null;

    public Command(String c) {
        init_string = c;
        if (c.equals("")) {
            command_is_empty = true;
            return;
        }
        try {
            c = c.toLowerCase();
            String[] parts = c.split(" ");
            instruction = parts[0];
            payload = Arrays.copyOfRange(parts, 1, parts.length);
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    public Command identify_type() {
        if (instruction.equalsIgnoreCase("deposit")) {
            return new DepositCommand(init_string);
        } else if (instruction.equalsIgnoreCase("create")) {
            return new CreateCommand(init_string);
        } else if (instruction.equalsIgnoreCase("pass")) {
            return new PassCommand(init_string);
        } else if (instruction.equalsIgnoreCase("transfer")) {
            return new TransferCommand(init_string);
        } else if (instruction.equalsIgnoreCase("withdraw")) {
            return new WithdrawalCommand(init_string);
        } else {
            return null;
        }
    }

    public String getInstruction() {
        return instruction;
    }

    public String[] getPayload() {
        return payload;
    }

    public String getValidInstruction() {
        return valid_instruction;
    }

    public List<Integer> getValidPayloadLength() {
        return valid_payload_length;
    }
}
