package banking;

public class SavingsAccount extends Account {
    private static String TYPE = "Savings";
    private static int ID;
    private double balance = 0;
    private double APR = 0;
    private boolean monthly_withdrawal_used = false;

    public SavingsAccount(int id) {
        super(id);
    }

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
        monthly_withdrawal_used = true;
        if (this.getBalance() > 0 && this.getBalance() - amount >= 0) {
            this.setBalance(this.getBalance() - amount);
        }
        if (this.getBalance() < 0) {
            this.setBalance(0);
        }
    }


}
