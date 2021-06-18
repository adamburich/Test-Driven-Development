package banking;

import java.util.ArrayList;

public abstract class Account {

    private int ID;
    private double balance = 0;
    private double APR = 0;
    private int monthsOld = 0;
    private CommandSaver transaction_tracker = new CommandSaver();

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
            default:
                break;
        }
        return null;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double bal) {
        if (bal >= 0) {
            balance = bal;
        } else {
            balance = 0;
        }
    }

    public double getAPR() {
        return APR;
    }

    public void withdraw(double amount) {
        if (this.getBalance() > 0) {
            this.setBalance(this.getBalance() - amount);
        }
        if (this.getBalance() < 0) {
            this.setBalance(0);
        }
    }

    public void deposit(double amount) {
        if (amount >= 0) {
            this.setBalance(this.getBalance() + amount);
        }
    }

    public void awardAPR(int months) {
        double accAPR = this.getAPR() / 100;
        accAPR = accAPR / 12;
        for (int i = 0; i < months; i++) {
            double new_balance = this.getBalance() + (accAPR * this.getBalance());
            this.setBalance(new_balance);
        }
    }

    public void saveTransaction(String cmd) {
        transaction_tracker.addValidCommand(cmd);
    }

    public ArrayList<String> transactionHistory() {
        return transaction_tracker.getValidCommands();
    }

    public void ageSingleMonth() {
        monthsOld += 1;
    }

    public int age() {
        return monthsOld;
    }


}
