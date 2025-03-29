import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 100;
    private static final int MAX_ATTEMPTS = 10;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int roundsWon = 0;
        boolean playAgain = true;

        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("Try to guess the number between " + MIN_NUMBER + " and " + MAX_NUMBER + ".");

        while (playAgain) {
            int numberToGuess = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;
            int attemptsLeft = MAX_ATTEMPTS;
            boolean numberGuessed = false;

            System.out.println("\nNew Round! You have " + MAX_ATTEMPTS + " attempts to guess the number.");

            while (attemptsLeft > 0) {
                System.out.print("Enter your guess: ");
                int userGuess;
                if (scanner.hasNextInt()) {
                    userGuess = scanner.nextInt();
                } else {
                    System.out.println("Invalid input. Please enter an integer between " + MIN_NUMBER + " and " + MAX_NUMBER + ".");
                    scanner.next(); 
                    continue;
                }

                if (userGuess < MIN_NUMBER || userGuess > MAX_NUMBER) {
                    System.out.println("Please guess a number between " + MIN_NUMBER + " and " + MAX_NUMBER + ".");
                    continue;
                }

                attemptsLeft--;

                if (userGuess < numberToGuess) {
                    System.out.println("Too low! Attempts left: " + attemptsLeft);
                } else if (userGuess > numberToGuess) {
                    System.out.println("Too high! Attempts left: " + attemptsLeft);
                } else {
                    numberGuessed = true;
                    roundsWon++;
                    System.out.println("Congratulations! You've guessed the number " + numberToGuess + " correctly.");
                    System.out.println("Rounds won: " + roundsWon);
                    break;
                }
            }

            if (!numberGuessed) {
                System.out.println("Sorry, you've used all attempts. The number was: " + numberToGuess);
            }
            System.out.print("Do you want to play again? (yes/no): ");
            String response = scanner.next().trim().toLowerCase();
            playAgain = response.equals("yes");
        }

        System.out.println("Thank you for playing! You won " + roundsWon + " rounds.");
        scanner.close();
    }
}