package banking;

public class CommandProcessor {
    Bank bank;

    public CommandProcessor(Bank b) {
        bank = b;
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

    public void issue_command(DepositCommand cmd) {
        String[] payload = cmd.getPayload();
        int id = Integer.parseInt(payload[0]);
        double deposit = Double.parseDouble(payload[1]);

        bank.getAccount(id).deposit(deposit);
        bank.getAccount(id).saveTransaction(cmd.init_string);
    }

    public void issue_command(PassCommand cmd) {
        String[] payload = cmd.getPayload();
        int num = Integer.parseInt(payload[0]);

        bank.passTime(num);
    }

    public void issue_command(TransferCommand cmd) {
        String[] payload = cmd.getPayload();
        bank.transferFunds(bank.getAccount(Integer.parseInt(payload[0])), bank.getAccount(Integer.parseInt(payload[1])), Double.parseDouble(payload[2]));
        bank.getAccount(Integer.parseInt(payload[1])).saveTransaction(cmd.init_string);
    }

    public void issue_command(WithdrawalCommand cmd) {
        String[] payload = cmd.getPayload();
        bank.getAccount(Integer.parseInt(payload[0])).withdraw(Double.parseDouble(payload[1]));
    }

    public void issue_command(Command c) {
        if (c instanceof CreateCommand) {
            issue_command((CreateCommand) c);
        } else if (c instanceof DepositCommand) {
            issue_command((DepositCommand) c);
        } else if (c instanceof PassCommand) {
            issue_command((PassCommand) c);
        } else if (c instanceof TransferCommand) {
            issue_command((TransferCommand) c);
        } else if (c instanceof WithdrawalCommand) {
            issue_command((WithdrawalCommand) c);
        }
    }
}
