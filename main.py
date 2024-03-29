# Author: Brandon Jonathan Brown

import math

class Node:
    """A Node class for use in a linked list-based stack."""
    def __init__(self, data):
        self.data = data
        self.next = None

class Stack:
    """A Stack data structure class implementing LIFO (Last In, First Out) principle."""
    def __init__(self):
        self.head = None

    def is_empty(self):
        """Check if the stack is empty."""
        return self.head is None

    def push(self, data):
        """Push an item onto the stack."""
        new_node = Node(data)
        new_node.next = self.head
        self.head = new_node

    def pop(self):
        """Remove and return the item at the top of the stack. Returns None if stack is empty."""
        if self.is_empty():
            return None
        top_data = self.head.data
        self.head = self.head.next
        return top_data

    def peek(self):
        """Return the item at the top of the stack without removing it. Returns None if stack is empty."""
        if self.is_empty():
            return None
        return self.head.data

    def display(self):
        """Print all items in the stack."""
        current = self.head
        while current:
            print(current.data)
            current = current.next

if __name__ == "__main__":
    stack_obj = Stack()

    # Using mathematical operations to demonstrate stack usage
    math_operations = [
        math.sqrt(144),        # sqrt(144) = 12
        math.pow(3, 3),        # pow(3,3) = 27
        math.exp(0),           # e^0 = 1 (using exp for base e exponentiation)
        math.log(100, 10),     # log base 10 of 100 = 2
        math.sin(math.radians(30)),   # sin(30 degrees)
        math.cos(math.radians(45)),   # cos(45 degrees)
        math.tan(math.radians(60)),   # tan(60 degrees)
        math.factorial(5),     # 5! = 120
        math.fabs(-8),         # Absolute value of -8 = 8
        (21 + (21 / 3))        # Arbitrary calculation = 28
    ]

    try:
        for operation in math_operations:
            stack_obj.push(operation)
    except Exception as e:
        print(f"Failed to push to the stack: {e}")

    try:
        print("[Last In First Out - LIFO]")
        print("_____________________________________")
        current = 1
        while not stack_obj.is_empty():
            a = stack_obj.peek()
            print(f"{current}. item, Result = {a}")
            stack_obj.pop()
            current += 1
        print("_____________________________________")
    except Exception as e:
        print(f"Failed to display stack: {e}")
