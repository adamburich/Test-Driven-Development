import java.util.Arrays;
import java.util.List;

public abstract class Command {

    private final List<String> VALID_INSTRUCTIONS = Arrays.asList(
            "create",
            "deposit"
    );

    private final List<String> VALID_ACCOUNT_TYPES = Arrays.asList(
            "checking",
            "savings",
            "cd"
    );

    private String instruction;
    private String[] payload;

    /**
     * public Command(String cmd) {
     * if (cmd.equals("")) {
     * command_is_empty = true;
     * return;
     * }
     * try {
     * cmd = cmd.toLowerCase();
     * String[] parts = cmd.split(" ");
     * instruction = parts[0];
     * payload = Arrays.copyOfRange(parts, 1, parts.length);
     * } catch (ArrayIndexOutOfBoundsException e) {
     * <p>
     * }
     * this.validate_command();
     * }
     */
    public boolean contains_valid_instruction(String str) {
        if (VALID_INSTRUCTIONS.contains(str)) {
            return true;
        } else {
            return false;
        }
    }

    public String getInstruction() {
        return instruction;
    }

    public String[] getPayload() {
        return payload;
    }

    public void give_valid_command_to_accountant() {

    }

}
