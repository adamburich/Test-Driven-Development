package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MasterControlTest {
    MasterControl controller;
    List<String> input = new ArrayList<>();

    private void assertSingleCommand(String cmd, List<String> actual) {
        assertEquals(1, actual.size());
        assertEquals(cmd, actual.get(0));
    }

    @BeforeEach
    void setup() {
        input = new ArrayList<>();
        Bank bank = new Bank();
        controller = new MasterControl(bank, new Validator(bank), new CommandProcessor(bank), new CommandSaver());
    }

    @Test
    void invalid_creation_of_duplicate_account_id() {
        input.add("create checking 12345678 1.0");
        input.add("create checking 12345678 1.0");

        List<String> actual = controller.start(input);

        assertSingleCommand("create checking 12345678 1.0", actual);
    }


}
