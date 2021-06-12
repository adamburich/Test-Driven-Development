package banking;

public class CDAccount extends Account {
    private static String TYPE = "Cd";
    private static int ID;
    private double balance = 0;

    public CDAccount(int id) {
        super(id);
    }

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

}
