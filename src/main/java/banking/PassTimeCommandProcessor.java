package banking;

public class PassTimeCommandProcessor {

    private Bank bank;

    public PassTimeCommandProcessor(Bank b) {
        this.bank = b;
    }

    public void issue_command(PassCommand cmd) {
        String[] payload = cmd.getPayload();
        int num = Integer.parseInt(payload[0]);

        bank.passTime(num);
    }
}
