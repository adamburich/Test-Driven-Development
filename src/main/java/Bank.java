import java.util.HashMap;
import java.util.Map;

public class Bank {
    private static Map<Integer, Account> accounts;
    private Accountant ben = new Accountant();

    Bank() {
        accounts = new HashMap<>();
    }

    public static Account getAccount(int id) {
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

    public void reduceAccountBalance(int id, int change) {
        if (accounts.get(id).getBalance() - change >= 0) {
            accounts.get(id).reduceBalance(change);
        }
    }

    public void increaseAccountBalance(int id, int change) {
        if (accounts.get(id).getBalance() + change >= 0) {
            accounts.get(id).addBalance(change);
        }
    }

    public void transferFunds(Account source, Account destination, double amount) {

    }

    public void passTime(int n) {

    }

    public void displayAccount(Account account) {

    }
}
