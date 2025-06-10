import java.util.*;

public class QuizApplication {

    static class Question {
        String question;
        String[] options;
        int correctAnswer;

        Question(String question, String[] options, int correctAnswer) {
            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }
    }

    static Scanner scanner = new Scanner(System.in);
    static int score = 0;
    static List<String> summary = new ArrayList<>();
    static boolean answered = false;

    public static void main(String[] args) {
        List<Question> questions = List.of(
            new Question("What is the capital of France?",
                    new String[]{"1. Berlin", "2. Madrid", "3. Paris", "4. Rome"}, 3),
            new Question("Which language runs in a web browser?",
                    new String[]{"1. Java", "2. C", "3. Python", "4. JavaScript"}, 4),
            new Question("What is 2 + 2?",
                    new String[]{"1. 3", "2. 4", "3. 5", "4. 22"}, 2)
        );

        System.out.println("=== QUIZ APPLICATION ===");
        System.out.println("You have 10 seconds per question.\n");

        for (int i = 0; i < questions.size(); i++) {
            answered = false;
            askQuestion(questions.get(i), i + 1);
        }

        // Final result
        System.out.println("\n=== QUIZ RESULT ===");
        System.out.println("Your Score: " + score + "/" + questions.size());
        System.out.println("\nSummary:");
        summary.forEach(System.out::println);
    }

    private static void askQuestion(Question q, int number) {
        System.out.println("Q" + number + ": " + q.question);
        for (String option : q.options) {
            System.out.println(option);
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                if (!answered) {
                    System.out.println("\n⏰ Time's up! Moving to next question.\n");
                    summary.add("Q" + number + ": ✘ (Time up)");
                    answered = true;
                }
                timer.cancel();
            }
        }, 10000); // 10 seconds

        int answer = -1;

        try {
            System.out.print("Your answer (1-4): ");
            answer = scanner.nextInt();
            if (!answered) {
                if (answer == q.correctAnswer) {
                    System.out.println("✔ Correct!\n");
                    score++;
                    summary.add("Q" + number + ": ✔");
                } else {
                    System.out.println("✘ Incorrect. Correct answer was: " + q.correctAnswer + "\n");
                    summary.add("Q" + number + ": ✘");
                }
                answered = true;
            }
        } catch (InputMismatchException e) {
            if (!answered) {
                System.out.println("Invalid input! Skipping question.\n");
                summary.add("Q" + number + ": ✘ (Invalid input)");
                scanner.next(); // clear invalid input
                answered = true;
            }
        }

        // Wait for a moment to ensure the timer doesn't run after the answer
        try {
            Thread.sleep(500); // short delay
        } catch (InterruptedException e) {
            // ignore
        }

        // Make sure the timer is cancelled after answering
        timer.cancel();
    }
}
