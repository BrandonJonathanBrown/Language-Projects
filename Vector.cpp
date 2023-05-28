#include <iostream>
#include <stdio.h>
#include <vector>

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
    
    vector<T> populate()
    {
        printf("%s\n","Please Enter Any Amount Of Integers:");
        
        cin >> length;
        
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
    for(int i = 0; i < argc; i++)
        cout << argv[i] << " ";
    
    Vector<int> obj;
    
    obj.populate();
    obj.lowestValue();
    obj.Printarr();
    obj.highestValue();
    obj.Printarr();
    
    
    return 0;
}
