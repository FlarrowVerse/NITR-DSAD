#pragma once

#include <iostream>
#include <tuple>

template <typename T>
class BinarySearchTree;

/**
 * TreeNode<left, value, right>
 * Data for a single node in a BST
 */
template <typename T>
class TreeNode {
private:
    // index int idx; 
    T value; // value of node
    /* pointers */
    TreeNode<T>* left;
    TreeNode<T>* right;
    TreeNode<T>* parent;

public:
    TreeNode(const T& value) : value(value), left(nullptr), right(nullptr), parent(nullptr) {} // parameterized constructor

    /**
     * Getter
     */
    T getValue() const {
        return this->value;
    }
    TreeNode<T>* getLeft() const {
        return this->left;
    }
    TreeNode<T>* getRight() const {
        return this->right;
    }
    TreeNode<T>* getParent() const {
        return this->parent;
    }

    /**
     * Setter
     */
    void setValue(const T& value) {
        this->value = value;
    }
    void setLeft(const TreeNode<T>& leftChild) {
        this->left = leftChild;
    }
    void setRight(const TreeNode<T>& rightChild) {
        this->right = rightChild;
    }
    void setParent(const TreeNode<T>& parent) {
        this->parent = parent;
    }

    /**
     * Easy for printing current node
     */
    friend std::ostream& operator<<(std::ostream& os, const TreeNode<T>& node) {
        os << node.value;
        return os;
    }

    /**
     * Returns true if a node has no children
     */
    bool isLeaf() {
        return !this->left && !this->right;
    }

    /**
     * Compares two nodes based on value only
     */
    friend bool operator>=(const TreeNode<T>& left, const TreeNode<T>& right) {
        return left.getValue() >= right.getValue();
    }
    friend bool operator<(const TreeNode<T>& left, const TreeNode<T>& right) {
        return left.getValue() < right.getValue();
    }
    friend bool operator==(const TreeNode<T>& left, const TreeNode<T>& right) {
        return left.getValue() == right.getValue();
    }
    
    /**
     * Declaring BST as friend class for easy access to nodes
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
    TreeNode<T>* root;

    void deleteTree(TreeNode<T>* node) {
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
    ~BinarySearchTree() {
        deleteTree(this->root);
        this->root = nullptr;
    }    

    /**
     * Insert a node into the list at the correct position by performing insertion sort
     */
    size_t insertToken(const T& value) {
        TreeNode<T>* newNode = new TreeNode<T>(value);
        size_t probes = 0;

        if (!this->root) { // empty tree
            this->root = newNode;
        } else {
            TreeNode<T>* curr = root;
            while (curr || curr->left || curr->right) {
                // check if new node is less than current node
                if (newNode < curr) {
                    if (curr->left) { // if already left child exists
                        curr = curr->left;
                        probes++;
                    } else {
                        curr->left = newNode;
                        newNode->parent = curr;
                        break;
                    }
                } else { // if node is greater than or equal to current node
                    if (curr->right) { // if already right child exists
                        curr = curr->right;
                        probes++;
                    } else {
                        curr->right = newNode;
                        newNode->parent = curr;
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
    std::tuple<TreeNode<T>*, size_t> searchToken(const T& value) {
        TreeNode<T>* start = this->root; // start from root and traverse
        size_t probes = -1;

        while (start) { // traverse until tree ends
            probes++;
            if (start->value == value) { // found
                return std::make_tuple(start, probes);
            } else if (value < start->value) {
                start = start->left; // search left sub-tree
            } else {
                start = start->right; // search right sub-tree
            }
        }
        return std::make_tuple(nullptr, probes);
    }

    /**
     * Find the inorder successor of the given node
     */
    std::tuple<TreeNode<T>*, size_t> getInorderSuccessor(TreeNode<T>* node) {
        TreeNode<T>* start = node->right; // start from right child of node and traverse
        size_t probes = 0;

        while (start && start->left) { // traverse until tree ends
            probes++;
            start = start->left;
        }
        return std::make_tuple(start, probes);
    }

    /**
     * Cleanup the connections
     */
    void cleanupLinks(TreeNode<T>* node) {
        TreeNode<T>* parent = node->parent;
        if (parent->left == node) {
            parent->left = nullptr;
        } else if (parent->right == node) {
            parent->right = nullptr;
        }
    }

    /**
     * Delete a value from tree
     */
    size_t deleteToken(const T& value) {
        auto [node, probes] = searchToken(value); // searching and getting the node that contains the value

        if (node) {
            if (node->isLeaf()) { // if node to be deleted is a leaf
                if (node == this->root) this->root = nullptr;
                else cleanupLinks(node);
            } else if (node->left && node->right) { // when both children are present
                // for all internal nodes with 2 children
                auto [inorderSuccessor, probes_2] = getInorderSuccessor(node);
                cleanupLinks(inorderSuccessor);
                inorderSuccessor->left = node->left;
                inorderSuccessor->right = node->right;

                // if node to be deleted is root
                if (node == this->root) this->root = inorderSuccessor;
                probes += probes_2;
            } else { // when only one of the children are present
                // if node to be deleted is root
                if (node == this->root) {
                    this->root = (node->left)? node->left: node->right;
                }
                // other internal node
                TreeNode<T>* parent = node->parent;
                if (parent->left == node) {
                    parent->left = (node->left)? node->left: node->right;
                } else if (parent->right == node) {
                    parent->right = (node->left)? node->left: node->right;
                }                
            }

            // delete the node after processing all the links and removing them
            delete node;
        }
        
        return probes;
    }

    /**
     * For easy display
     */
    friend std::ostream& operator<<(std::ostream& os, const BinarySearchTree& tree) {
        os << "nullptr";
        return os;        
    }
};

