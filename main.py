# Author Brandon Jonathan Brown
import math

# Node class used to put together the stack.
class Node:
    def __init__(self, data):
        self.data = data
        self.next = None


# Stack data structure "LIFO"
class Stack:

    def __init__(self):
        self.head = None

    def is_empty(self):
        return self.head is None

    def push(self, data):
        new_node = Node(data)
        new_node.next = self.head
        self.head = new_node

    def pop(self):
        if self.is_empty():
            return None

        t_data = self.head.data
        self.head = self.head.next
        return t_data

    def peek(self):
        if self.is_empty():
            return None

        return self.head.data

    def display(self):
        current = self.head

        while current:
            print(current.data)
            current = current.next


if __name__ == "__main__":

    # Instantiation of "Stack Class" in main program.

    stack_obj = Stack()

    # Implementation of the stack data structure using math algebra.
    try:

        # sqrt(144) = 12
        stack_obj.push(math.sqrt(144));

        # pow(3,3) = 27
        stack_obj.push(math.pow(3, 3))

        # pow(e,0) = 1
        stack_obj.push(math.pow(math.e, 0))

        # log(100) = 2
        stack_obj.push(math.log(100, 10))

        # sin(30) = 1/2
        stack_obj.push(math.sin(math.radians(30)))

        # cos(45) = sqrt(2) / 2
        stack_obj.push(math.cos(math.radians(45)))

        # tan(60) = sqrt(3)
        stack_obj.push(math.tan(math.radians(60)))

        # 1 * 2 * 3 * 4 * 5 = 120
        stack_obj.push(math.factorial(5))

        # |-8| = +8
        stack_obj.push(math.fabs(-8))

        # (21 + (21 / 3)) = 28
        stack_obj.push(((7 * 3) + (21 / 1) / 3))

    except:
        print("Failed to push to the stack!")

    try:

        print("[Last In First Out - LIFO]")
        print("_____________________________________")

        # I am looping through the stack and grabbing the first element.
        # Then I am releasing that element at the end of the loop.
        current = 1
        for i in range(10):
            a = stack_obj.peek()
            print(f"{current}. item, Result = {a}")
            stack_obj.pop()
            current += 1

        print("_____________________________________")

    except:
        print("Failed to display stack!")


