#include <iostream>
#include <stdio.h>
#include <vector>
#include <string>

using namespace std;

/*
 Algorithm for lowest value and maximum value
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
        printf("Please Enter %d Integers: \n",size);

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
        for (int i = 0; i < length; i++) {
              for (int j = i + 1; j < length; j++) {
                  if (l[i] > l[j]) {
                      temp = l[i];
                      l[i] = l[j];
                      l[j] = temp;
                  }
              }
          }

        return l;
    }

    vector<T> highestValue()
    {
        for (int i = 0; i < length; i++) {
              for (int j = i + 1; j < length; j++) {
                  if (l[i] < l[j]) {
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
        for(T k: l)
        {
            cout << k << ", ";
        }
    }

};

int main(int argc, char *argv[])
{

    Vector<int> obj;
    int _size = stoi(argv[1]);
    obj.populate(_size);
    printf("%s\n","From Smallest To Largest!");
    obj.lowestValue();
    obj.Printarr();
    printf("\n");
    printf("%s\n","From Largest To Smallest!");
    obj.highestValue();
    obj.Printarr();


    return 0;
}
