package banking;

import java.util.ArrayList;
import java.util.List;

public class MasterControl {
    Bank bank;
    Validator validator;
    CommandProcessor processor;
    CommandSaver storage;

    public MasterControl(Bank bank, Validator validator, CommandProcessor processor, CommandSaver storage) {
        this.bank = bank;
        this.validator = validator;
        this.processor = processor;
        this.storage = storage;
    }

    public List<String> start(List<String> input) {
        for (String str : input) {
            validator = new Validator(bank);
            Command comm = new Command(str);
            comm = comm.identify_type();
            if (validator.validate(comm)) {
                processor.issue_command(comm);
            } else {
                storage.addInvalidCommand(str);
            }
        }
        List<String> out = new ArrayList<String>();
        out.addAll(bank.output());
        out.addAll(storage.getInvalidCommands());
        return out;
    }


}
