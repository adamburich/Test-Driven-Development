package banking;

public class CDAccount extends Account {
    private static String TYPE = "Cd";

    public CDAccount(int id, double apr, double bal) {
        super(id, apr, bal);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void awardAPR(int months) {
        double accAPR = this.getAPR() / 100;
        accAPR = accAPR / 12;
        for (int j = 0; j < months; j++) {
            for (int i = 0; i < 4; i++) {
                double new_balance = this.getBalance() + (accAPR * this.getBalance());
                this.setBalance(new_balance);
            }
        }
    }

    @Override
    public void withdraw(double amount) {
        if (this.age() >= 12) {
            if (this.getBalance() > 0) {
                this.setBalance(this.getBalance() - amount);
            }
            if (this.getBalance() < 0) {
                this.setBalance(0);
            }
        }
    }

}
