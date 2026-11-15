
import java.util.HashMap;

public class Bank {
    private HashMap<String, Account> accounts = new HashMap<>();

    public Bank() {
        accounts.put("101", new Account("101", "1234", 5000));
        accounts.put("102", new Account("102", "5678", 3000));
    }

    public Account getAccount(String accountId) {
        return accounts.get(accountId);
    }

    public boolean transfer(String senderId, String receiverId,
                            double amount) {
        Account sender = accounts.get(senderId);
        Account receiver = accounts.get(receiverId);

        if (sender == null || receiver == null ||
            sender == receiver || amount <= 0 ||
            sender.getBalance() < amount) {
            return false;
        }

        sender.withdraw(amount);
        receiver.deposit(amount);
        return true;
    }
}
