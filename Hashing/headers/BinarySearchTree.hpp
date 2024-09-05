#pragma once

#include <iostream>
#include <tuple>

template <typename T>
class BinarySearchTree;

/**
 * Node<left, value, right>
 * Data for a single node in a BST
 */
template <typename T>
class Node {
private:
    // index int idx; 
    T value; // value of node
    /* pointers */
    Node<T>* left;
    Node<T>* right;

public:
    Node(const T& value) : value(value), left(nullptr), right(nullptr) {} // parameterized constructor

    /**
     * Getter
     */
    T getValue() const {
        return this->value;
    }
    Node<T>* getLeft() const {
        return this->left;
    }
    Node<T>* getRight() const {
        return this->right;
    }

    /**
     * Setter
     */
    void setValue(const T& value) {
        this->value = value;
    }
    void setLeft(const Node<T>& leftChild) {
        this->left = leftChild;
    }
    void setRight(const Node<T>& rightChild) {
        this->right = rightChild;
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
    friend bool operator>=(const Node<T>& left, const Node<T>& right) {
        return left.getValue() >= right.getValue();
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
    friend class BinarySearchTree<T>;
};


/**
 * Binary<root<prev, value, next>>
 * Handles functions for a BST
 */
template <typename T>
class BinarySearchTree {
private:
    Node<T>* root;

    void deleteTree(Node<T>* node) {
        if (node) {
            deleteTree(node->left);  // Delete left subtree
            deleteTree(node->right); // Delete right subtree
            delete node;             // Delete the current node
        }
    }

public:
    BinarySearchTree() : root(nullptr) {} // default constructor

    /**
     * Destructor - cleanup for the list
     */
    ~DoublyLinkedList() {
        deleteTree(this->root);
        this->root = nullptr;
    }

    /**
     * Insert a node into the list at the correct position by performing insertion sort
     */
    size_t insertNewNode(T value) {
        Node<T>* newNode = new Node<T>(value);
        size_t probes = 0;

        if (!this->root) { // empty tree
            this->root = newNode;
        } else {
            Node<T>* curr = root;
            while (curr || curr->left || curr->right) {
                // check if new node is less than current node
                if (newNode < curr) {
                    if (curr->left) { // if already left child exists
                        curr = curr->left;
                        probes++;
                    } else {
                        curr->left = newNode;
                        break;
                    }
                } else { // if node is greater than or equal to current node
                    if (curr->right) { // if already right child exists
                        curr = curr->right;
                        probes++;
                    } else {
                        curr->right = newNode;
                        break;
                    }
                }
            }
        }
        
        return probes;
    }

    /**
     * search tree for value
     */
    std::tuple<bool, size_t> searchList(T value) {
        
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

