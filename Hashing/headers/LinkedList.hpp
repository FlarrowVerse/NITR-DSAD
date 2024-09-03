#pragma once

#include <iostream>


/**
 * Node<prev, value, next>
 * Data for a single node in a doubly linked list
 */
template <typename T>
class Node {
private:
    int idx; // index
    T value; // value of node
    /* pointers */
    Node* next, prev;

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
        os << this->idx << ". " << this->value;
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
};


/**
 * LinkedList<head<prev, value, next>>
 * Handles functions for a doubly linked list
 */
template <typename T>
class LinkedList {
private:
    Node<T>* head;

public:
    LinkedList() : head(nullptr) {} // default constructor

    /**
     * Destructor - cleanup for the list
     */
    ~LinkedList() {
        Node<T>* curr = head; //setting the current node as head
        while (curr != nullptr) {
            Node<T>* nextNode = curr->next; // moving to next node
            delete curr; // delete current
            curr = nextNode; // setting the current as the next node in the list
        }
    }

    /**
     * Insert a node into the list at the end without sorting
     */
    void insertNewNode(T value) {
        Node<T>* newNode = new Node<T>(value); // create a new node

        if (!head) {
            head = newNode;
        } else {
            Node<T>* curr = head;
            // find the last node
            while (curr->getNext() != nullptr) {
                curr = curr->getNext();
            }
            
            curr->setNext(newNode);
            newNode->setPrev(curr);
        }
        
    }

    /**
     * Insert a node into the list at the correct position by performing insertion sort
     */

    /**
     * delete a node based on value
     */
};

