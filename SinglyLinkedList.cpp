#include <iostream>
#include <memory>

// Author Brandon Jonathan Brown

template <class T>
class Node {
public:
    T data;
    std::unique_ptr<Node<T>> next;

    Node() : data(0), next(nullptr) {}
    Node(T data) : data(data), next(nullptr) {}

    // Disable copy operations
    Node(const Node&) = delete;
    Node& operator=(const Node&) = delete;

    // Enable move operations
    Node(Node&&) noexcept = default;
    Node& operator=(Node&&) noexcept = default;

    ~Node() = default;
};

template <class T>
class SinglyLinkList {
public:
    std::unique_ptr<Node<T>> head;

    SinglyLinkList() : head(nullptr) {}

    // Disable copy operations
    SinglyLinkList(const SinglyLinkList&) = delete;
    SinglyLinkList& operator=(const SinglyLinkList&) = delete;

    // Enable move operations
    SinglyLinkList(SinglyLinkList&&) noexcept = default;
    SinglyLinkList& operator=(SinglyLinkList&&) noexcept = default;

    ~SinglyLinkList() {
        while (head != nullptr) {
            head = std::move(head->next);
        }
    }

    void Insert(T data) {
        auto newNode = std::make_unique<Node<T>>(data);
        if (!head) {
            head = std::move(newNode);
        } else {
            newNode->next = std::move(head);
            head = std::move(newNode);
        }
    }

    void Delete() {
        if (!head) {
            std::cout << "List is empty!\n";
        } else {
            head = std::move(head->next);
        }
    }

    void ListTraversal() {
        if (!head) {
            std::cout << "List is empty!\n";
            return;
        }
        Node<T>* current = head.get();
        while (current != nullptr) {
            std::cout << current->data;
            current = current->next.get();
            if (current) std::cout << ", ";
        }
        std::cout << std::endl;
    }
};

int main() {
    SinglyLinkList<int> list;

    list.Insert(6);
    list.Insert(7);
    list.Insert(12);

    list.Delete();
    list.ListTraversal();

    return 0;
}

