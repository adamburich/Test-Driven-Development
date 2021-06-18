package banking;

import java.util.ArrayList;

public class CommandSaver {
    private ArrayList<String> invalid_commands = new ArrayList<String>();
    private ArrayList<String> valid_commands = new ArrayList<String>();

    public CommandSaver() {
        //Does not need to be filled
    }

    public ArrayList<String> getInvalidCommands() {
        return invalid_commands;
    }

    public ArrayList<String> getValidCommands() {
        return valid_commands;
    }

    public void addValidCommand(String valid_command) {
        valid_commands.add(valid_command);
    }

    public void addInvalidCommand(String invalid_command) {
        invalid_commands.add(invalid_command);
    }
}
