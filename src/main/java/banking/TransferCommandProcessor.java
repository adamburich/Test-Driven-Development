package banking;

public class TransferCommandProcessor {

    private Bank bank;

    public TransferCommandProcessor(Bank b) {
        this.bank = b;
    }

    public void issue_command(Command cmd) {
        String[] payload = cmd.getPayload();
        bank.transferFunds(bank.getAccount(Integer.parseInt(payload[0])), bank.getAccount(Integer.parseInt(payload[1])), Double.parseDouble(payload[2]));
        bank.getAccount(Integer.parseInt(payload[1])).saveTransaction(cmd.init_string);
    }
}
