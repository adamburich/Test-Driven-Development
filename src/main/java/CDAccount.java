public class CDAccount extends Account {
    private static String TYPE = "CD";
    private static int ID;
    private int balance;

    public CDAccount(int id) {
        super(id);
    }

    public CDAccount(int id, int b) {
        super(id, b);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
