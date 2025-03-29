import java.util.*;
import java.util.concurrent.*;

class QuizQuestion {
    String question;
    String[] options;
    int correctAnswer; 
    public QuizQuestion(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public void displayQuestion() {
        System.out.println(question);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    public boolean isCorrect(int answer) {
        return answer == correctAnswer + 1;
    }
}

class Quiz {
    private List<QuizQuestion> questions;
    private int score;

    public Quiz() {
        questions = new ArrayList<>();
        score = 0;
        loadQuestions();
    }

    private void loadQuestions() {
        questions.add(new QuizQuestion("What is the capital of France?", new String[]{"Berlin", "Paris", "Madrid", "Rome"}, 1));
        questions.add(new QuizQuestion("Which planet is known as the Red Planet?", new String[]{"Earth", "Mars", "Jupiter", "Venus"}, 1));
        questions.add(new QuizQuestion("What is 5 + 7?", new String[]{"10", "11", "12", "13"}, 2));
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        for (QuizQuestion question : questions) {
            question.displayQuestion();

            int userAnswer = getUserAnswer(scanner);

            if (question.isCorrect(userAnswer)) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong! Correct answer was: " + (question.correctAnswer + 1));
            }
        }

        displayResults();
        scanner.close();
    }

    private int getUserAnswer(Scanner scanner) {
        final int[] userAnswer = {-1};
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<?> future = executor.submit(() -> {
            System.out.print("Enter your choice (1-4) within 10 seconds: ");
            userAnswer[0] = scanner.nextInt();
        });

        try {
            future.get(10, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.out.println("\nTime is up! Moving to the next question.");
            future.cancel(true);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }

        executor.shutdown();
        return userAnswer[0];
    }

    private void displayResults() {
        System.out.println("\nQuiz Over! Your final score: " + score + "/" + questions.size());
    }
}

public class QuizApp {
    public static void main(String[] args) {
        Quiz quiz = new Quiz();
        quiz.start();
    }
}
