package banking;

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

    /**
     * public void reduceAccountBalance(int id, double change) {
     * if (accounts.get(id).getBalance() - change >= 0 && accounts.get(id).getBalance() > 0) {
     * accounts.get(id).withdraw(change);
     * }
     * }
     * <p>
     * public void increaseAccountBalance(int id, double change) {
     * if (accounts.get(id).getBalance() + change >= 0) {
     * accounts.get(id).deposit(change);
     * }
     * }
     */
    public void transferFunds(Account source, Account destination, double amount) {
        accounts.get(source.getID()).withdraw(amount);
        accounts.get(destination.getID()).deposit(amount);
    }

    public void passTime(int months) {
        for (Map.Entry<Integer, Account> acc : accounts.entrySet()) {
            for (int i = 0; i < months; i++) {
                acc.getValue().awardAPR(months);
                acc.getValue().ageSingleMonth();
                if (acc.getValue() instanceof SavingsAccount) {
                    ((SavingsAccount) acc.getValue()).monthly_withdrawal_used = false;
                }
            }

        }
    }

    public String displayAccount(Account account) {
        return account.getType() + " " + account.getID() + " " + account.getBalance() + " " + account.getAPR();
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
