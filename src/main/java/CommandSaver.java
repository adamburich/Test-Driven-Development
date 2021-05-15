import java.util.ArrayList;

public class CommandSaver {
    private ArrayList<String> invalid_commands = new ArrayList<String>();

    public CommandSaver() {
    }

    public ArrayList<String> getInvalidCommands() {
        return invalid_commands;
    }

    public void addInvalidCommand(String invalid_command) {
        invalid_commands.add(invalid_command);
    }
}
