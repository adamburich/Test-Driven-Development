package banking;

public class TransferCommandProcessor {

    private Bank bank;

    public TransferCommandProcessor(Bank b) {
        this.bank = b;
    }

    public void issue_command(TransferCommand cmd) {
        String[] payload = cmd.getPayload();
        double amount;
        if (bank.getAccount(Integer.parseInt(payload[0])).getBalance() < Double.parseDouble(payload[2])) {
            amount = bank.getAccount(Integer.parseInt(payload[0])).getBalance();
        } else {
            amount = Double.parseDouble(payload[2]);
        }
        bank.transferFunds(bank.getAccount(Integer.parseInt(payload[0])), bank.getAccount(Integer.parseInt(payload[1])), amount);
        bank.getAccount(Integer.parseInt(payload[0])).saveTransaction(cmd.init_string);
        bank.getAccount(Integer.parseInt(payload[1])).saveTransaction(cmd.init_string);
    }
}
