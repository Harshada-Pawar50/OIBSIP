
import java.util.ArrayList;
import java.util.Scanner;

public class ATM {
    private Bank bank;
    private Account account;
    private ArrayList<Transaction> history;
    private Scanner sc;

    public ATM(Bank bank, Scanner sc) {
        this.bank = bank;
        this.sc = sc;
        this.history = new ArrayList<>();
    }

    public void login() {
        int attempts = 0;

        while (attempts < 3) {
            System.out.print("Enter User ID: ");
            String id = sc.nextLine();

            System.out.print("Enter ATM PIN: ");
            String pin = sc.nextLine();

            Account user = bank.getAccount(id);

            if (user != null && user.checkPin(pin)) {
                account = user;
                System.out.println("\nLogin Successful!");
                showMenu();
                return;
            }

            attempts++;
            System.out.println("Invalid User ID or PIN!");

            if (attempts < 3) {
                System.out.println("Attempts left: " + (3 - attempts));
            }
        }

        System.out.println("Access Denied! Too many incorrect attempts.");
    }

    private void showMenu() {
        while (true) {
            System.out.println("\n===== ATM MENU =====");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid choice!");
                continue;
            }

            switch (choice) {
                case 1:
                    showHistory();
                    break;

                case 2:
                    withdraw();
                    break;

                case 3:
                    deposit();
                    break;

                case 4:
                    transfer();
                    break;

                case 5:
                    System.out.println("Thank you for using ATM!");
                    return;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private double getAmount() {
        try {
            double amount = Double.parseDouble(sc.nextLine());

            if (Double.isFinite(amount) && amount > 0) {
                return amount;
            }
        } catch (NumberFormatException e) {
            // Invalid input
        }

        System.out.println("Please enter a valid positive amount!");
        return -1;
    }

    private void withdraw() {
        System.out.print("Enter withdrawal amount: Rs. ");
        double amount = getAmount();

        if (amount <= 0) return;

        if (account.withdraw(amount)) {
            System.out.println("Withdrawal Successful!");
            System.out.println("Remaining Balance: Rs. " +
                               account.getBalance());

            history.add(new Transaction("Withdraw: Rs. " + amount));
        } else {
            System.out.println("Insufficient Funds!");
        }
    }

    private void deposit() {
        System.out.print("Enter deposit amount: Rs. ");
        double amount = getAmount();

        if (amount <= 0) return;

        account.deposit(amount);

        System.out.println("Deposit Successful!");
        System.out.println("Updated Balance: Rs. " +
                           account.getBalance());

        history.add(new Transaction("Deposit: Rs. " + amount));
    }

    private void transfer() {
        System.out.print("Enter receiver Account ID: ");
        String receiverId = sc.nextLine();

        System.out.print("Enter transfer amount: Rs. ");
        double amount = getAmount();

        if (amount <= 0) return;

        if (bank.transfer(account.getAccountId(), receiverId, amount)) {
            System.out.println("Transfer Successful!");
            System.out.println("Remaining Balance: Rs. " +
                               account.getBalance());

            history.add(new Transaction(
                "Transferred Rs. " + amount + " to Account " + receiverId
            ));
        } else {
            System.out.println("Transfer Failed! Check account or balance.");
        }
    }

    private void showHistory() {
        System.out.println("\n===== TRANSACTION HISTORY =====");

        if (history.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }

        for (Transaction transaction : history) {
            transaction.display();
        }
    }
}
