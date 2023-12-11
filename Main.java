import java.util.Scanner;

/**
 * @Author Brandon Jonathan Brown
 * @Note Simple number guessing game in java
 */

public class Main {

    public static void main(String[] args) {



        Queue<Integer> Queue = new Queue<>();

        while(!false) {

            System.out.println("________________________________________________________________________");

            System.out.printf("%s\n","WELCOME TO MY GUESSING GAME!");

            for (int i = 0; i < 10; i++) {
                //Chooses a random number between 1-10
                //Adds it to the queue
                Queue.enqueue((int) (Math.random() * 10) + 1);
            }

            System.out.printf("%s\n", "Enter A Number Between 1-10");

            //Guess the number via input
            Scanner input = new Scanner(System.in);
            int n = input.nextInt();

            //If the number is correct and the first element you win a grand prize
            if(n == Queue.peek()){
                System.out.println("Correct Lucky Guess! You Won The Game" + "Number: " + n);
                       continue;
            }

            System.out.println("Numbers: ");

            // If the number you guessed is somewhere on the list, but not first place you win a regular prize
            while(!Queue.isEmpty()) {
                if(n == Queue.dequeue())
                {
                    System.out.println("Congratulations You Won " +  "The number you guessed is correct! " + "Number: " + n);
                }

               System.out.println(Queue.dequeue());
            }
        }

    }
}
