import java.util.Scanner;
import java.lang.String;

/**
 * @Author Brandon Jonathan Brown
 * @Simple application for calculating percentage
 */

public class Main
{
    
    public static void main(String [] args)
    {
        
        Stack<Integer> stack = new Stack<Integer>();
        Double Percentage = 0.00;
        
        System.out.printf("%S\n", "Calculate The Percentage Of Shots Made: ");
        System.out.printf("%S\n","Enter how many shots taken?");
        
        Scanner input = new Scanner(System.in);
        Scanner amount = new Scanner(System.in);
        Scanner qt = new Scanner(System.in);
        
        Integer shotsTaken = input.nextInt();
        
        for(int i = 0; i < shotsTaken; i++)
        {
            int shots = amount.nextInt();
            if(shots <=4)
            {
                stack.push(shots);
            }
            
        }
        
        
        for(int i = 0; i < stack.Size(); i++)
        {
            int p = stack.pop();
            switch(p)
            {
                case 0:
                    Percentage += 0;
                    break;
                case 1:
                      Percentage += 1;
                    break;
                case 2:
                       Percentage += 1;
                    break;
                case 3:
                       Percentage += 1;
                    break;
                case 4:
                      Percentage += 1;
                    break;
            }
            
        }
        
        Percentage = (Percentage / shotsTaken) * 100;
        
        System.out.println("The number of shots made is " + Percentage + "%");
    }
    
    
}
