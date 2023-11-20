class Stack<T> {
    private Node<T> top;
    private int size = 0;

    public Stack() {
        this.top = null;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void push(T data) {
        Node newNode = new Node(data);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return top.data;
    }
    
    public void display()
    {
        Node<T> current = top;
        
        while(current != null)
        {
            System.out.println(current.data);
            current = current.next;
        }
    }
    
    public int Size()
    {
        return size;
    }
}


