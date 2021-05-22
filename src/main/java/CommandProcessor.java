public class CommandProcessor {
    Bank bank;

    public CommandProcessor(Bank b) {
        bank = b;
    }

    public void issue_command(ccmd cmd) {
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

    public void issue_command(dcmd cmd) {
        String[] payload = cmd.getPayload();
        int id = Integer.parseInt(payload[0]);
        double deposit = Double.parseDouble(payload[1]);

        bank.increaseAccountBalance(id, deposit);
    }

    public void issue_command(cmd c) {
        if (c instanceof ccmd) {
            issue_command((ccmd) c);
        } else if (c instanceof dcmd) {
            issue_command((dcmd) c);
        }
    }
}
