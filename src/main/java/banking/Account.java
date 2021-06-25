package banking;

import java.text.DecimalFormat;
import java.util.ArrayList;

public abstract class Account {

    DecimalFormat formatter = new DecimalFormat("0.00");
    private int ID;
    private String TYPE;
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

    public int getID() {
        return ID;
    }

    public String getType() {
        return TYPE;
    }

    public Account assignType(String typ) {
        switch (typ.toUpperCase()) {
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
        if (balance < 0) {
            setBalance(0);
            return getBalance();
        } else {
            return balance;
        }
    }

    public void setBalance(double bal) {
        balance = bal;
    }

    public String displayAPR() {
        return formatter.format(APR);
    }

    public String displayBalance() {
        return formatter.format(balance);
    }

    public double getAPR() {
        return APR;
    }

    public void withdraw(double amount) {
        this.setBalance(this.getBalance() - amount);
    }

    public void deposit(double amount) {
        this.setBalance(this.getBalance() + amount);
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
