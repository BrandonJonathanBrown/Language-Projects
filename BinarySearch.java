import java.lang.Runnable;
import java.util.Scanner;

/**
 * @Author Brandon Jonathan Brown
 * @Note Simple algorithm that searches for index in a array!
 */

public class BinarySearch implements Runnable
{
    public static void main(String[] args)
    {
        Thread thread = new Thread(new BinarySearch());
        thread.start();
    }

    public static int BinarySearch(int arr[], int num)
	  {

        	int low = 0;
		      int high = arr.length - 1;

		        while(low <= high)
		        {
			           int middlePosition = (low + high) / 2;
			           int middleNumber = arr[middlePosition];

			              if(num == middleNumber)
				                return middlePosition;

			              if(num < middleNumber)
				                high = middlePosition - 1;
			              else
				                low = middlePosition + 1;
		        }
        return -1;
	}


    @Override
    public void run()
    {

	int arr[] = new int[] {1, 6, 3, 2, 9, 11, 19, 27};
	System.out.printf("%S","Enter a number from the array, so you can find the index!");
	Scanner input = new Scanner(System.in);
	int n = input.nextInt();
	System.out.printf("%s%d","Index is ", BinarySearch(arr,n));

    }

}
