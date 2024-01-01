#include <iostream>
#include <stdio.h>
#include <vector>
#include <string>

/*
 * @Author Brandon Jonathan Brown
 * @Note Simple program that displays the lowest to highest values from a list of given integers.Also, vice versa.
 * @Note I will give a simple example, on how to compile the cpp file via g++ from the shell terminal.
 */

template <class T> class Vector
{
public:
    T num;
    T temp;
    int length = 0;
    std::vector<T> arr;

    std::vector<T> add(T num)
    {
        arr.push_back(num);
        return arr;
    };

    std::vector<T> delete()
    {
        arr.pop_back();
        return arr;
    };

    std::vector<T> populate(int size)
    {
        printf("Please Enter %d Integers: \n", size);
        length = size;
        for(int i = 0; i < length; i++)
        {
            std::cin >> num;
            add(num);
        }

        return arr;
    };

    std::vector<T> lowestValues()
    {
        for(int i = 0; i < length; i++)
        {
            for(int j = i + 1; j < length; j++)
            {
                if(arr[i] > arr[j])
                {
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }

        return arr;
    };

    std::vector<T> hightestValues()
    {
        for(int i = 0; i < length; i++)
        {
            for(int j = i + 1; j < length; j++)
            {
                if(arr[i] < arr[j])
                {
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }

        return arr;
    };

    void printArr()
    {
        for(T t: arr)
        {
            std::cout << t << ", ";
        }
    };

};

int main(int argc, char* argv[])
{
    Vector<int> obj;

    int _size = std::stoi(argv[1]);
    obj.populate(_size);
    printf("%s\n","Smallest To Largest...");
    obj.lowestValues();
    obj.printArr();
    printf("\n");
    printf("%s\n","Largest To Smallest...");
    obj.hightestValues();
    obj.printArr();

    return -1;
}

