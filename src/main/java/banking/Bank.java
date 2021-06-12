package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bank {
    public Map<Integer, Account> accounts;

    Bank() {
        accounts = new HashMap<>();

    }

    public Account getAccount(int id) {
        try {
            return accounts.get(id);
        } catch (NullPointerException n) {
            return null;
        }
    }

    public Map<Integer, Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account acc) {
        accounts.put(acc.getID(), acc);
    }

    public void deleteAccount(int id) {
        accounts.remove(id);
    }

    public void reduceAccountBalance(int id, double change) {
        if (accounts.get(id).getBalance() - change >= 0 && accounts.get(id).getBalance() > 0) {
            accounts.get(id).withdraw(change);
        }
    }

    public void increaseAccountBalance(int id, double change) {
        if (accounts.get(id).getBalance() + change >= 0) {
            accounts.get(id).deposit(change);
        }
    }

    public void transferFunds(Account source, Account destination, double amount) {
        accounts.get(source.getID()).withdraw(amount);
        accounts.get(destination.getID()).deposit(amount);
    }

    public void passTime(int months) {
        for (Map.Entry<Integer, Account> acc : accounts.entrySet()) {
            acc.getValue().awardAPR(months);
            acc.getValue().ageSingleMonth();
        }
    }

    public String displayAccount(Account account) {
        DecimalFormat formatter = new DecimalFormat("0.00");
        formatter.setRoundingMode(RoundingMode.FLOOR);
        return account.getType() + " " + account.getID() + " " + formatter.format(account.getBalance()) + " " + formatter.format(account.getAPR());
    }

    public ArrayList<String> output() {
        ArrayList<String> out = new ArrayList<String>();
        ArrayList<Integer> keys = new ArrayList<Integer>(accounts.keySet());
        for (int i = keys.size() - 1; i >= 0; i--) {
            if (accounts.get(keys.get(i)).getBalance() > 0) {
                out.add(displayAccount(accounts.get(keys.get(i))));
                if (accounts.get(keys.get(i)).transactionHistory().size() > 0) {
                    for (String str : accounts.get(keys.get(i)).transactionHistory()) {
                        out.add(str);
                    }
                }
            }
        }
        return out;
    }
}
