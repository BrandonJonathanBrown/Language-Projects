import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Game extends JPanel implements Adapter{
	
	/**
	 * @author brandonjonathanbrown
	 */
	
	private static final long serialVersionUID = 1L;
	private JTextField [][] board = new JTextField[9][9];
	private int[][] mirror = new int[9][9];
	
	public Game()
	{
		init();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
            	 
                while(true) {
                  
                    try {
                        Thread.sleep(1000/60);
                        Placement();
                		boundaries();
                    }
                    catch(Exception ex)
                    {
                    	System.err.println(ex.getMessage());
                    }
                }
            }
           
        }); thread.start();
	
            
     }
	
	
	@Override
	public void init() {
	
		 
	 	for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				JTextField textField = new JTextField();
				    textField.addKeyListener(new KeyAdapter() {
				        @Override
				        public void keyTyped(KeyEvent e) {
				            if (textField.getText().length() >= 1 ) 
				                e.consume();
				        }
				    });
				    
				    textField.addKeyListener(new KeyAdapter() {
				    	  public void keyTyped(KeyEvent e) {
				    	    char c = e.getKeyChar();
				    	       if((Character.isAlphabetic(c) || (c==KeyEvent.VK_BACK_SPACE) || c==KeyEvent.VK_DELETE )) {
				    	        e.consume();  
				    	    }
				    	   }
				    	});

				  textField.setHorizontalAlignment(SwingConstants.CENTER);
				  textField.setFont(new Font("Arial", Font.PLAIN, 50));
				  board[i][j] = textField;
				  mirror[i][j] = 0; 
			}
			
		}	
	 
        this.setLayout(new GridLayout(9,9));
        
        for(int i = 0; i < 9; i++)
        {
        	for(int j = 0; j < 9; j++)
        	{
        		this.add(board[i][j]);
        	}
        }	
		
	}

	@Override
	public void boundaries() {
		

			duplicate(mirror);
			
		}
	
	@Override
	public int duplicateRow(int[][] arr)
	{
		  int index = 0;
		
		  for (int row = 0; row < arr.length; row++)
		    {
		        for (int col = 0; col < arr[row].length; col++)
		        {
		            int num = arr[row][col];
		            for (int otherCol = col + 1; otherCol < arr.length; otherCol++)
		            {
		                if (num == arr[row][otherCol])
		                {
		                    System.out.println("Duplicate Found In Array!");
		                }
		                else
		                {
		                	index++;
		                }
		            }
		        }
		    }
		  
		  return index;
	}
	
	@Override
	public void duplicate(int[][] arr) {
		
		
		
		    if(duplicateRow(arr) == 81 && duplicateCol(arr) == 81 && duplicateblock(arr) == 81)
		    {
		    	JOptionPane.showMessageDialog(null, "You Won The Game!", "MessageBox:" + "Sudoku", JOptionPane.INFORMATION_MESSAGE);
		    }
		 
		
	}
	
	@Override
	public void Placement() {
		 
		for(int i = 0; i < board.length; i++)
		 {
			 for(int j = 0; j < board.length; j++)
			 {
				 mirror[i][j] = 0;
			 }
		 }
		
		for(int i = 0; i < board.length; i++)
		 {
			 for(int j = 0; j < board.length; j++)
			 {
				 switch(Integer.parseInt(board[i][j].getText().toString()))
				 {
				 case 1:
					 mirror[i][j] = 1;
					 break;
				 case 2:
					 mirror[i][j] = 2;
					 break;
				 case 3: 
					 mirror[i][j] = 3;
					 break;
				 case 4:
					 mirror[i][j] = 4;
					 break;
				 case 5:
					 mirror[i][j] = 5;
					 break;
				 case 6:
					 mirror[i][j] = 6;
					 break;
				 case 7:
					 mirror[i][j] = 7;
					 break;
				 case 8:
					 mirror[i][j] = 8;
					 break;
				 case 9:
					 mirror[i][j] = 9;
					 break;
				default:
					System.err.println("ERROR");
					break;
				 }
			 }
		 }
		
	}



	@Override
	public int duplicateCol(int[][] arr) {
		
		int index = 0;
		
		   for (int col = 0; col < arr.length; col++)
		    {
		        for (int row = 0; row < arr[col].length; row++)
		        {
		            int num = arr[row][col];
		            for (int otherrow = row + 1; otherrow < arr.length; otherrow++)
		            {
		                if (num == arr[otherrow][col])
		                {
		                    System.out.println("Duplicate Found In Array!");
		                }
		                else
		                {
		                	index++;
		                }
		            }
		        }
		    }
		   
		   return index;
	}


	@Override
	public int duplicateblock(int[][] arr) {
	
		ArrayList<String> list = new ArrayList<String>();
		int index = 0;
		
		for (int i = 0; i < 9; i += 3) {   
			
		  for (int j = 0; j < 9; j += 3) {
			  
		    String curr = "";
		    
		    for (int k = 0; k < 3; k++) {
		    	
		      for (int l = 0; l < 3; l++) {
		    	  
		        curr += arr[i + k][j + l];
		      }
		    }
		    list.add(curr);
		  }
		}
		
		int k = 0;
		int[][] block = new int[list.size()][list.size()]; 
		
		for(String p: list)
		{
			
			for(int j = 0; j < list.size(); j++)
			{
				block[k][j] = Integer.parseInt(String.valueOf(p.charAt(j)));
			}
			
			k++;
		}
		
		 for (int row = 0; row < block.length; row++)
		    {
		        for (int col = 0; col < block[row].length; col++)
		        {
		            int num = block[row][col];
		            for (int otherCol = col + 1; otherCol < block.length; otherCol++)
		            {
		                if (num == block[row][otherCol])
		                {
		                    System.out.println("Duplicate Found In Array!");
		                }
		                else
		                {
		                	index++;
		                }
		            }
		        }
		    }
		 return index;
	}
	    
	
	
}
