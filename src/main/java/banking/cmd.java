package banking;

import java.util.Arrays;
import java.util.List;

public class cmd {
    public boolean command_is_empty = false;
    public List<Integer> valid_payload_length = null;
    public String valid_instruction = null;
    private String instruction = null;
    private String[] payload = null;
    private String init_string = null;

    public cmd(String c) {
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

    public cmd identify_type() {
        if (instruction.equalsIgnoreCase("deposit")) {
            return new dcmd(init_string);
        } else if (instruction.equalsIgnoreCase("create")) {
            return new ccmd(init_string);
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
