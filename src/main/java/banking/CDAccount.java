package banking;

public class CDAccount extends Account {
    private static String TYPE = "CD";
    private static int ID;
    private double balance = 0;
    private double APR = 0;

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


}
