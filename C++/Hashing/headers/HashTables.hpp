#pragma once

/* core libs */
#include <iostream>
#include <tuple>
#include <cmath>
#include <memory>

/* user defined libs*/
#include "utils.hpp"
#include "Types.hpp"
#include "LinkedList.hpp"

template <typename T>
class OpenHashTables {
private:
    bool orderedChain; // determines if chaining is ordered or not
    size_t tableSize; // stores number of buckets
    std::vector<DoublyLinkedList<T>> hashTable; // actual hashtable

public:
    /**
     * Parameterized constructor
     */
    OpenHashTables(size_t size, bool ordered) : tableSize(size), orderedChain(ordered), hashTable(size) {}

    /**
     * Hash function - h(x) = address belongs to {0, size-1}
     */
    size_t getHash(int key) {
        double k = (sqrt(5) - 1) / 2; // golden ratio

        double product = k * key; // k * x
        double fraction = product - floor(product); // fraction(k * x)
        return floor(this->tableSize * fraction); // floor(m * fraction(k * x))
    }

    /**
     * Inserts a new token into the hash table
     */
    size_t insertToken(const T& token) {
        size_t homeAddress = getHash(token.getKey());
        if (this->orderedChain) {
            return this->hashTable[homeAddress].insertNewNode(token);
        } else {
            return this->hashTable[homeAddress].addNewNode(token);
        }
    }

    /**
     * Searches a token in the current hash table
     */
    std::tuple<bool, size_t> searchToken(const T& token) {
        size_t homeAddress = getHash(token.getKey());
        auto [node, probes] = this->hashTable[homeAddress].searchList(token); // searches the chain

        if (node != nullptr) {
            std::cout << token <<  ": FOUND" << std::endl;
        } else {
            std::cout << token <<  ": NOT FOUND" << std::endl;
        }
        return std::make_tuple(node != nullptr, probes); // returns both true/false and number of probes
    }

    /**
     * Searches and deletes a token in the current hash table
     */
    size_t deleteToken(const T& token) {
        size_t homeAddress = getHash(token.getKey());        
        if (this->hashTable[homeAddress].isEmpty()) {
            return 0;
        }
        return this->hashTable[homeAddress].deleteNode(token);
    }

    /**
     * Easy for printing current node
     */
    friend std::ostream& operator<<(std::ostream& os, const OpenHashTables<T>& oht) {
        std::cout << "Open Hash Table with ";
        if (oht.orderedChain) {
            std::cout << " Ordered Chaining::::>" << std::endl;
        } else {
            std::cout << " Unordered Chaining::::>" << std::endl;
        }
        for (int i = 0; i < oht.tableSize; i++) {
            std::cout << oht.hashTable[i] << std::endl;
        }
        return os;
    }

};

template <typename T>
class ClosedHashTables {
private:    
    size_t tableSize; // stores number of buckets
    std::vector<std::unique_ptr<T>> hashTable; // actual hashtable

public:
    /**
     * Parameterized constructor
     */
    ClosedHashTables(size_t size) : tableSize(size), hashTable(size) {}

    /**
     * Hash function - h(x) = address belongs to {0, size-1}
     */
    size_t getHash(int key) {
        double k = (sqrt(5) - 1) / 2; // golden ratio

        double product = k * key; // k * x
        double fraction = product - floor(product); // fraction(k * x)
        return floor(this->tableSize * fraction); // floor(m * fraction(k * x))
    }

    /**
     * Probe function - set to linear mode for now
     */
    size_t getProbe(size_t homeAddress, size_t i) {
        return (homeAddress + i) % this->tableSize;
    }

    /**
     * Inserts a new token into the hash table
     */
    size_t insertToken(const T& token) {
        size_t homeAddress = getHash(token.getKey()), address = homeAddress; // homeAddress
        size_t i = 0;
        
        while (i < this->tableSize && this->hashTable[address] != nullptr) {            
            address = getProbe(homeAddress, i); // get next linear probe            
            i++;
        }
        

        if (i >= this->tableSize) {
            std::cout << "ERROR: Cannot insert new token. Out of space!" << std::endl;
        } else {
            this->hashTable[address] = std::make_unique<TokenEntry>(token);
        }
        return i;
    }

    /**
     * Searches a token in the current hash table
     */
    std::tuple<bool, size_t> searchToken(const T& token) {
        size_t homeAddress = getHash(token.getKey()); // homeAddress
        size_t i = 0, address = homeAddress;
        bool found = false;
        while (i < this->tableSize && this->hashTable[address] != nullptr) {
            if (*(this->hashTable[address]) == token) { // break the loop if token is found
                found = true;
                break;
            }
            address = getProbe(homeAddress, i++); // get next linear probe            
        }

        if (!found) {
            std::cout << token << ": NOT FOUND" << std::endl;
            return std::make_tuple(false, i);
        } else {
            std::cout << token << ": FOUND" << std::endl;
            return std::make_tuple(true, i);
        }
    }

    /**
     * Searches and deletes a token in the current hash table
     */
    size_t deleteToken(const T& token) {
        size_t homeAddress = getHash(token.getKey()); // homeAddress
        size_t i = 0, address = homeAddress;
        bool found = false;
        
        while (i < this->tableSize && this->hashTable[address] != nullptr) {
            std::cout << "Checking element " << *(this->hashTable[address]) << std::endl;
            
            if (*(this->hashTable[address]) == token) { // break the loop if token is found                
                found = true;
                break;
            }
            address = getProbe(homeAddress, i++); // get next linear probe            
        }

        if (!found) {
            std::cout << token << ": NOT FOUND" << std::endl;
        } else {
            std::cout << token << ": FOUND AND DELETED" << std::endl;
            this->hashTable[address].reset();
        }
        return i;
    }

    /**
     * Easy for printing current node
     */
    friend std::ostream& operator<<(std::ostream& os, const ClosedHashTables<T>& cht) {
        std::cout << "Closed Hash Table with Linear Probing:::::>" << std::endl;
        for (int i = 0; i < cht.tableSize; i++) {
            if ((cht.hashTable[i])) {
                std::cout << *(cht.hashTable[i]) << std::endl;
            } else {
                std::cout << "<EMPTY>" << std::endl;
            }
        }
        return os;
    }

};