#Author Brandon Jonathan Brown

class QuickSort:

    def __init__(self):
        print("[*] Starting Application!")


    def quicksort(self, arr):

        if len(arr) <= 1:
            return arr
        else:
            pivot = arr[0]
            less = [x for x in arr[1:] if x <= pivot]
            greater = [x for x in arr[1:] if x >= pivot]

            return self.quicksort(less) + [pivot] + self.quicksort(greater)


if __name__ == "__main__":

    Obj = QuickSort()

    l = [8,11,4,7,3,2,9,45,33]

    print(Obj.quicksort(l))
