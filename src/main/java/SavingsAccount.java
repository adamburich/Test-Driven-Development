public class SavingsAccount extends Account {
    private static String TYPE = "SAVINGS";
    private static int ID;
    private double balance = 0;
    private double APR = 0;

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


}
