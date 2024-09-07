#pragma once

#include <iostream>
#include <tuple>

template <typename T>
class DoublyLinkedList;

/**
 * Node<prev, value, next>
 * Data for a single node in a doubly linked list
 */
template <typename T>
class Node {
private:
    // index int idx; 
    T value; // value of node
    /* pointers */
    Node<T>* next;
    Node<T>* prev;

public:
    Node(const T& value) : value(value), next(nullptr), prev(nullptr) {} // parameterized constructor

    /**
     * Getter
     */
    T getValue() const {
        return this->value;
    }
    Node<T>* getNext() const {
        return this->next;
    }
    Node<T>* getPrev() const {
        return this->prev;
    }

    /**
     * Setter
     */
    void setValue(const T& value) {
        this->value = value;
    }
    void setNext(const Node<T>& nextNode) {
        this->next = nextNode;
    }
    void setPrev(const Node<T>& prevNode) {
        this->prev = prevNode;
    }

    /**
     * Easy for printing current node
     */
    friend std::ostream& operator<<(std::ostream& os, const Node<T>& node) {
        os << node.value;
        return os;
    }

    /**
     * Compares two nodes based on value only
     */
    friend bool operator>(const Node<T>& left, const Node<T>& right) {
        return left.getValue() > right.getValue();
    }
    friend bool operator<(const Node<T>& left, const Node<T>& right) {
        return left.getValue() < right.getValue();
    }
    friend bool operator==(const Node<T>& left, const Node<T>& right) {
        return left.getValue() == right.getValue();
    }
    
    /**
     * Declaring DLL as friend class for easy access to nodes
     */
    friend class DoublyLinkedList<T>;
};


/**
 * LinkedList<head<prev, value, next>>
 * Handles functions for a doubly linked list
 */
template <typename T>
class DoublyLinkedList {
private:
    Node<T>* head;
    Node<T>* tail;

public:
    // stores size of linkedlist
    size_t size;

    DoublyLinkedList() : head(nullptr), tail(nullptr), size(0) {} // default constructor

    /**
     * Destructor - cleanup for the list
     */
    ~DoublyLinkedList() {
        Node<T>* curr = head; //setting the current node as head
        while (curr != nullptr) {
            Node<T>* nextNode = curr->next; // moving to next node
            delete curr; // delete current
            curr = nextNode; // setting the current as the next node in the list
        }
        this->size = 0;
        this->head = nullptr;
        this->tail = nullptr;
    }

    /**
     * Add a node into the list at the end without sorting
     */
    size_t addNewNode(T value) {
        Node<T>* newNode = new Node<T>(value); // create a new node

        if (!this->head) { // when the list is empty
            this->head = newNode;
            this->tail = this->head;
        } else {
            this->tail->next = newNode;
            newNode->prev = this->tail;
            this->tail = newNode;
        }
        this->size = 0;
        return 0;
    }

    /**
     * Insert a node into the list at the correct position by performing insertion sort
     */
    size_t insertNewNode(T value) {
        Node<T>* newNode = new Node<T>(value);
        size_t index = 0;

        if (!this->head) { // empty list
            this->head = newNode;
            this->tail = this->head;
        } else {
            Node<T>* curr = head;
            while (curr && curr->value <= newNode->value) { // finding a key greater than current key
                curr = curr->next; index++;
            }

            if (curr == this->head) { // insert a new head
                this->head->prev = newNode;
                newNode->next = this->head;
                this->head = newNode;
            } else if (curr == nullptr) { // insert at the end
                this->tail->next = newNode;
                newNode->prev = this->tail;
                this->tail = newNode;
            } else { // insert in the middle at the correct position
                newNode->next = curr;
                newNode->prev = curr->prev;
                curr->prev->next = newNode;
                curr->prev = newNode;
            }
        }
        this->size++;
        return index;
    }

    /**
     * delete a node based on key value
     */
    std::tuple<Node<T>*, size_t> searchList(T value) {
        Node<T>* start = this->head;
        Node<T>* end = this->tail; // two pointers
        int startIdx = 0, endIdx = this->size - 1;

        // searching from either side
        while ((start->prev != end || start != end) && (start->value != value || end->value != value)) {
            start = start->next; startIdx++;
            end = end->prev; endIdx--;
        }

        if (start->value == value) {
            return std::make_tuple(start, startIdx);
        } else if (end->value == value) {
            return std::make_tuple(end, endIdx);
        } else {
            return std::make_tuple(nullptr, startIdx);
        }
    }

    size_t deleteNode(T value) {
        auto [node, index] = searchList(value); // searching and getting the node that contains the value

        if (node) {
            node->prev->next = node->next;
            node->next->prev = node->prev;
            delete node;
        }
        this->size--;
        return index;
    }

    /**
     * For easy display
     */
    friend std::ostream& operator<<(std::ostream& os, const DoublyLinkedList& list) {
        Node<T>* curr = list.head;
        while (curr) {
            os << *curr << " <--> ";
            curr = curr->next;
        }
        os << "nullptr";
        return os;        
    }
};

