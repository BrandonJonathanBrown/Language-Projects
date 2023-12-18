#Author Brandon Jonathan Brown
import math


class Node:

    def __init__(self,data):
        self.data = data
        self.next = None


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
            print(" ")

if __name__ == "__main__":


    # Instantiation of Stack class
    Obj = Stack()

    # Start off with simple arithmetic
    # 12*2 = 24
    # 27 / 1 = 27
    # (24) + (27) = 51
    print("BODMAS", (12 * 2) + 27 / 1)

    # Implementation of the stack data structure using math algebra
    # Square root of 144 = 12
    x = math.sqrt(144)
    Obj.push(f"Square root {x}")
    # 3^3 = 27
    x = math.pow(3, 3)
    Obj.push(f"Exponent {x}")
    # e^0 = 1
    x = math.exp(0)
    Obj.push(f"e^0 {x}")
    # e^e = 1
    x = math.log(math.e)
    Obj.push(f"e^e {x}")

    # Trigonometric ratios
    # sin(30) = 1/2
    x = math.sin(math.radians(30))
    Obj.push(f"sin(30) {x}")
    # cos(45) = sqrt(2)/2
    x = math.cos(math.radians(45))
    Obj.push(f"cos(45) {x}")
    # tan(60) = sqrt(3)
    x = math.tan(math.radians(60))
    Obj.push(f"tan(60) {x}")

    # Other functions
    # 1 * 2 * 3 * 4 * 5 = 120
    x = math.factorial(5)
    Obj.push(f"Factorial {x}")
    #Absolute value of -8 = +8 or postive 8
    x = math.fabs(-8)
    Obj.push(f"Absolute Value {x}")

    # All variables pushed to stack, lets see what the output is!
    Obj.display()
