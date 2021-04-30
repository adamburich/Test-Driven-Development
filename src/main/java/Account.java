public class Account {

    private int ID;
    private int balance;

    public Account(int id) {
        ID = id;
    }

    public Account(int id, int bal) {
        ID = id;
        if (bal >= 0) {
            balance = bal;
        }
    }

    public int getID() {
        return ID;
    }

    public String getType() {
        return null;
    }

    public Account assignType(String type) {
        switch (type.toUpperCase()) {
            case "CHECKING":
                return new CheckingAccount(this.ID, this.balance);
            case "SAVINGS":
                return new SavingsAccount(this.ID, this.balance);
            case "CD":
                return new CDAccount(this.ID, this.balance);
        }
        return null;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int bal) {
        if (bal >= 0) {
            balance = bal;
        }
    }

    public void reduceBalance(int by) {
        if (this.getBalance() - by >= 0) {
            this.setBalance(this.getBalance() - by);
        }
    }

    public void addBalance(int to) {
        if (to >= 0) {
            this.setBalance(this.getBalance() + to);
        }
    }
}