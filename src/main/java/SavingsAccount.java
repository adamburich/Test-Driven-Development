public class SavingsAccount extends Account {
    private static String TYPE = "SAVINGS";
    private static int ID;
    private int balance;

    public SavingsAccount(int id) {
        super(id);
    }

    public SavingsAccount(int id, int b) {
        super(id, b);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
