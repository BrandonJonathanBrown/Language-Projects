#include <iostream>
#include <stdio.h>
#include <vector>
#include <string>

using namespace std;

/*
 *
 * @Author Brandon Jonathan Brown
 * @Note Simple program that displays the lowest to highest value from a list of given integers. Also vice versa
 */

template <class T> class Vector
{
public:
    T num;
    T temp;
    int length = 0;
    vector<T> l;

    vector<T> add(T num)
    {
        l.push_back(num);
        return l;
    }

    vector<T> Delete()
    {
        l.pop_back();
        return l;
    }

    vector<T> populate(int size)
    {
        printf("Please Enter %d Integers: \n", size);
        length = size;
        for(int j = 0; j < length; j++)
        {
            cin >> num;
            add(num);
        }

        return l;
    }

    vector<T> lowestValue()
    {
        for(int i = 0; i < length; i++)
        {
            for(int j = i + 1; j < length; j++)
            {
                if(l[i] > l[j])
                {
                    temp = l[i];
                    l[i] = l[j];
                    l[j] = temp;
                }
            }
        }

        return l;
    }

    vector<T> hightestValue()
    {
        for(int i = 0; i < length; i++)
        {
            for(int j = i + 1; j < length; j++)
            {
                if(l[i] < l[j])
                {
                    temp = l[i];
                    l[i] = l[j];
                    l[j] = temp;
                }
            }
        }

        return l;
    }

    void Printarr()
    {
        for(T p: l)
        {
            cout << p << ", ";
        }
    }

};

int main(int argc, char* argv[])
{
    Vector<int> obj;
    int _size = stoi(argv[1]);
    obj.populate(_size);
    printf("%s\n","Smallest To Largest...");
    obj.lowestValue();
    obj.Printarr();
    printf("\n");
    printf("%s\n","Largest To Smallest...");
    obj.hightestValue();
    obj.Printarr();
}

