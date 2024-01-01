/**
@Author Brandon Jonathan Brown
@About Simple Singly linked list data structure c++
*/
#include <iostream>  

template <class T> class Node
{

public:
    
    T data;
    Node<T>* next;


    Node()
    {
        data = 0;
        next = nullptr;
    }

    Node(T data)
    {
        this->data = data;
    }
    
 
};


template <class T> class SinglyLinkList
{

public:

    Node<T>* head;

    SinglyLinkList() :head(nullptr)
    {
    }


    void Insert(T data)
    {
        Node<T>* newNode = new Node<T>(data);
        
        if (head == nullptr)
        {
           head = newNode;
        }
        else
        {
            newNode->next = head;
            head = newNode;
        }

    }

    void Delete()
    {
        if (head == nullptr)
        {
            std::cout << "List is empty!" << std::endl;
        }
        else
        {
            Node<T>* temp = head;
            head = head->next;
            delete temp;

        }
    }

    void ListTraversal()
    {
        Node<T>* current = head;

        while (current != nullptr)
        {
            std::cout << current->data << ",";
            current = current->next;
        }
    }
      


};




int main(int argc, char** argv)
{

    SinglyLinkList<int> list;
    
    list.Insert(6);
    list.Insert(7);
    list.Insert(12);

    list.Delete();

    list.ListTraversal();

    return -1;
};
