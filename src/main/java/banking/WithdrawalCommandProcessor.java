package banking;

public class WithdrawalCommandProcessor {

    private Bank bank;

    public WithdrawalCommandProcessor(Bank b) {
        this.bank = b;
    }

    public void issue_command(WithdrawalCommand cmd) {
        String[] payload = cmd.getPayload();
        bank.getAccount(Integer.parseInt(payload[0])).withdraw(Double.parseDouble(payload[1]));
    }
}
