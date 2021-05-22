package banking;

public abstract class Account {

    private int ID;
    private double balance = 0;
    private double APR = 0;

    public Account(int id) {
        ID = id;
    }

    public Account(int id, double apr) {
        ID = id;
        APR = apr;
    }

    public Account(int id, double apr, double bal) {
        ID = id;
        if (bal >= 0) {
            balance = bal;
        }
        APR = apr;
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
                return new CheckingAccount(this.ID, this.APR);
            case "SAVINGS":
                return new SavingsAccount(this.ID, this.APR);
            case "CD":
                return new CDAccount(this.ID, this.APR, this.balance);
        }
        return null;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double bal) {
        if (bal >= 0) {
            balance = bal;
        }
    }

    public double getAPR() {
        return APR;
    }

    public void reduceBalance(double by) {
        if (this.getBalance() - by >= 0) {
            this.setBalance(this.getBalance() - by);
        }
    }

    public void addBalance(double to) {
        if (to >= 0) {
            this.setBalance(this.getBalance() + to);
        }
    }

    public void awardAPR() {
        double accAPR = APR / 100;
        accAPR = accAPR / 12;
        accAPR = accAPR * this.getBalance();
        this.setBalance((int) accAPR);
    }


}
