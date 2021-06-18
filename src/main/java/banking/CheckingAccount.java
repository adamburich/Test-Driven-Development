package banking;

public class CheckingAccount extends Account {
    private static String TYPE = "Checking";

    public CheckingAccount(int id) {
        super(id);
    }

    public CheckingAccount(int id, double apr) {
        super(id, apr);
    }

    @Override
    public String getType() {
        return TYPE;
    }


}
