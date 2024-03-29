# Author: Brandon Jonathan Brown

class QuickSort:
    def __init__(self):
        print("[*] Starting Application!")

    def quicksort(self, arr):
        """Sorts an array using the quicksort algorithm."""
        if len(arr) <= 1:
            return arr
        else:
            pivot = arr[0]
            less = [x for x in arr[1:] if x < pivot]  # Elements less than pivot
            equal = [x for x in arr if x == pivot]    # Elements equal to pivot (including pivot itself)
            greater = [x for x in arr[1:] if x > pivot]  # Elements greater than pivot

            return self.quicksort(less) + equal + self.quicksort(greater)

if __name__ == "__main__":
    qs = QuickSort()
    lst = [8, 11, 4, 7, 3, 2, 9, 45, 33, 2]
    print(qs.quicksort(lst))
