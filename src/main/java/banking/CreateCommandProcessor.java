package banking;

public class CreateCommandProcessor {

    private Bank bank;

    public CreateCommandProcessor(Bank b) {
        this.bank = b;
    }

    public void issue_command(CreateCommand cmd) {
        Account account = null;
        String[] payload = cmd.getPayload();
        String type = payload[0];
        int id = Integer.parseInt(payload[1]);
        double apr = Double.parseDouble(payload[2]);
        double balance = 0;
        if (payload.length == 4) {
            balance = Double.parseDouble(payload[3]);
        }
        if (type.equalsIgnoreCase("checking")) {
            account = new CheckingAccount(id, apr);
        } else if (type.equalsIgnoreCase("savings")) {
            account = new SavingsAccount(id, apr);
        } else if (type.equalsIgnoreCase("cd")) {
            account = new CDAccount(id, apr, balance);
        }
        if (account != null && bank.getAccount(id) == null) {
            bank.addAccount(account);
        }
    }
}
