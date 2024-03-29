import java.util.Arrays;
import java.util.Scanner;

public class BinarySearchExample implements Runnable {

	/**
 		Author Brandon Jonathan Brown
	*/

    public static void main(String[] args) {
        Thread thread = new Thread(new BinarySearchExample());
        thread.start();
    }

    private static int binarySearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int middlePosition = low + (high - low) / 2; // To prevent potential overflow
            int middleValue = arr[middlePosition];

            if (target == middleValue) {
                return middlePosition;
            }

            if (target < middleValue) {
                high = middlePosition - 1;
            } else {
                low = middlePosition + 1;
            }
        }
        return -1; // Element not found
    }

    @Override
    public void run() {
        int[] arr = {1, 6, 3, 2, 9, 11, 19, 27};

        // Sorting the array before performing binary search
        Arrays.sort(arr);

        System.out.println("Sorted array: " + Arrays.toString(arr));
        System.out.print("Enter a number from the array to find its index: ");
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        scanner.close();

        int index = binarySearch(arr, number);

        if (index != -1) {
            System.out.println("Index of " + number + " is: " + index);
        } else {
            System.out.println(number + " is not in the array.");
        }
    }
}
