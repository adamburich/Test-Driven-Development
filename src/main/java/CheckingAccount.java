public class CheckingAccount extends Account {
    private static String TYPE = "CHECKING";
    private static int ID;
    private int balance;

    public CheckingAccount(int id) {
        super(id);
    }

    public CheckingAccount(int id, int b) {
        super(id, b);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
