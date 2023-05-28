import java.util.Arrays;

public class Main<T> {
	
	public static DoublyLinkedList<Integer> dll = new DoublyLinkedList<Integer>();
	public static int index, average;
	public static int arr[] = new int[10];
	
	public static void main(String[] args)
	{
		
		for(int i = 0; i < 10; i++)
		{
			int rand = (int) (Math.random() * 10);
			System.out.println(rand);
			dll.addFirst(rand);
			arr[i] = dll.Pop();
			index++;
		}
		
		Arrays.sort(arr);
		for(int o: arr)
		{
			System.out.println(o + ", ");
		}
			
		   dll.printlistforward();
		   System.out.println("The average is " + (average / index)); 
		
	}

}
