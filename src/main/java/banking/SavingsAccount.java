package banking;

public class SavingsAccount extends Account {
    private static String TYPE = "Savings";
    public boolean monthly_withdrawal_used = false;

    public SavingsAccount(int id, double apr) {
        super(id, apr);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public boolean withdrawalUsed() {
        return monthly_withdrawal_used;
    }

    @Override
    public void withdraw(double amount) {
        if (!withdrawalUsed()) {
            //this.setBalance(this.getBalance() - amount);
            super.withdraw(amount);
            monthly_withdrawal_used = true;
        }
    }


}
