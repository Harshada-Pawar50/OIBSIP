
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        ArrayList<String> scores = new ArrayList<>();

        int round = 1;
        String playAgain = "yes";

        while (playAgain.equalsIgnoreCase("yes")) {
            int secretNumber = random.nextInt(100) + 1;
            int maxAttempts = 7;
            int attempts = 0;
            boolean guessed = false;

            System.out.println("\n--- Round " + round + " ---");
            System.out.println("Guess a number between 1 and 100.");
            System.out.println("You have " + maxAttempts + " attempts.");

            while (attempts < maxAttempts && !guessed) {
                System.out.print("Enter your guess: ");

                if (!sc.hasNextInt()) {
                    System.out.println("Please enter a valid whole number.");
                    sc.next();
                    continue;
                }

                int guess = sc.nextInt();

                if (guess < 1 || guess > 100) {
                    System.out.println("Enter a number from 1 to 100.");
                    continue;
                }

                attempts++;

                if (guess < secretNumber) {
                    System.out.println("Too Low!");
                } else if (guess > secretNumber) {
                    System.out.println("Too High!");
                } else {
                    System.out.println("Correct!");
                    guessed = true;
                }

                System.out.println("Attempts used: " + attempts + "/" + maxAttempts);
            }

            if (guessed) {
                System.out.println("You guessed it in " + attempts + " attempts.");
                scores.add("Round " + round + " — guessed in " + attempts + " attempts");
            } else {
                System.out.println("You Lost!");
                System.out.println("The number was: " + secretNumber);
                scores.add("Round " + round + " — not guessed");
            }

            System.out.print("Play Again? (yes/no): ");
            playAgain = sc.next().trim();
            round++;
        }

        System.out.println("\n--- Score Summary ---");
        for (String score : scores) {
            System.out.println(score);
        }

        System.out.println("Thanks for playing!");
        sc.close();
    }
}
