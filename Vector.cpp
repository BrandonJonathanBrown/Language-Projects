#include <iostream>
#include <vector>

/**
* @Author Brandon Jonathan Brown
* @Note This is a simple program that displays the lowest to highest values from a list of give integers. Also, vice versa.
* @Note I will show you an example, on how to compile a cpp file in the terminal.
*/

template <class T>
class Vector {
public:
    void populate(int size);
    void sortAscending();
    void sortDescending();
    void printArray() const;

private:
    std::vector<T> arr;
};

template <class T>
void Vector<T>::populate(int size) {
    std::cout << "Please Enter " << size << " Integers:\n";
    T num;
    for (int i = 0; i < size; ++i) {
        std::cin >> num;
        arr.push_back(num);
    }
}

template <class T>
void Vector<T>::sortAscending() {
    std::sort(arr.begin(), arr.end());
}

template <class T>
void Vector<T>::sortDescending() {
    std::sort(arr.rbegin(), arr.rend());
}

template <class T>
void Vector<T>::printArray() const {
    for (const T &t : arr) {
        std::cout << t << ", ";
    }
    std::cout << '\n';
}

int main(int argc, char* argv[]) {
    Vector<int> obj;

    obj.populate(std::stoi(argv[1]));

    std::cout << "Smallest to Largest:\n";
    obj.sortAscending();
    obj.printArray();

    std::cout << "Largest to Smallest:\n";
    obj.sortDescending();
    obj.printArray();

    return 0;
}
