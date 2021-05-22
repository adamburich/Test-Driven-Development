import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandSaverTest {
    @Test
    void added_invalid_command_appears_in_invalid_commands() {
        CommandSaver savethis = new CommandSaver();
        String invalid_command = "this is an invalid command";
        savethis.addInvalidCommand(invalid_command);
        assertTrue(savethis.getInvalidCommands().contains(invalid_command));
    }
}
