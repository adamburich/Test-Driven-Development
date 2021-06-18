package banking;

public class DepositCommandProcessor {

    private Bank bank;

    public DepositCommandProcessor(Bank b) {
        this.bank = b;
    }

    public void issue_command(DepositCommand cmd) {
        String[] payload = cmd.getPayload();
        int id = Integer.parseInt(payload[0]);
        double deposit = Double.parseDouble(payload[1]);

        bank.getAccount(id).deposit(deposit);
        bank.getAccount(id).saveTransaction(cmd.init_string);
    }

}
