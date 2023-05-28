
public class DoublyLinkedList<T> {
	
	/**
	 * 
	 * @author brandonjonathanbrown
	 */
	
	public Node<T> tail;
	public Node<T> head;
	public T data;
	
	
	public DoublyLinkedList()
	{
		this.head = null;
		this.tail = null;
	}
	
	public void addFirst(T Value)
	{

		Node<T> newnode = new Node<T>(Value);
		newnode.next = this.head;
		this.head = newnode;
	}
	
	public void addLast(T Value)
	{
		
		Node<T> newnode = new Node<T>(Value);			
	    newnode.prev = this.tail;
	    this.tail = newnode;
		
	}
	
	public void insertBefore(Node<T> node, T Value)
	{
			if(node == null)
				return;
			
			Node<T> newnode = new Node<T>(Value);
			
			newnode.prev = node.prev;
			node.prev = newnode;
			newnode.next = node;
			
			
	}
	
	
	public void insertAfter(Node<T> node, T Value)
	{
		
		if(node == null)
			return;
		
		Node<T> newnode = new Node<T>(Value);
		
		newnode.next = node.next;
		node.next = newnode;
		newnode.prev = node;
		
		
	}
	
	public T Pop()
	{
		Node<T> current = this.head;
		this.head = current.next;
		return current.data;
	}
	


	public void printlistbackward()
	{
		Node<T> pivot = this.tail;
		
		while(pivot != null)
		{
			System.out.println(pivot.data);
			pivot = pivot.prev;
		}
		
	}
	
	
	public void printlistforward()
	{
		Node<T> pivot = this.head;
		
		while(pivot != null)
		{
			System.out.println(pivot.data);
			pivot = pivot.next;
		}
		
	}
	
	
	
}
	
	


