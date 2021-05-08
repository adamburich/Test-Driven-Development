import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandTest {
    String command_string;
    Command cmd;
    String instruction;
    String payload;


    @BeforeEach
    void setup() {
        cmd = new Command(command_string);
        instruction = cmd.get_instruction();
        payload = cmd.get_payload();
    }

    @Test
    void instruction_is_invalid() {
    }
}
