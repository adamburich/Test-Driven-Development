import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<Integer, Account> accounts;

    Bank() {
        accounts = new HashMap<>();
    }

    public Map<Integer, Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account acc) {
        accounts.put(acc.getID(), acc);
    }

    public Account getAccount(int id) {
        return accounts.get(id);
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
}
