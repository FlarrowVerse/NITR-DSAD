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
    std::vector<DDL_t<TokenEntry>> hashTable; // actual hashtable

public:
    /**
     * Parameterized constructor
     */
    OpenHashTables(size_t size, bool ordered) : size(size), orderedChain(ordered), hashTable(size) {}

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
    size_t insertToken(const TokenEntry& token) {
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
    std::tuple<bool, size_t> searchToken(const TokenEntry& token) {
        size_t homeAddress = getHash(token.getKey());
        auto [node, probes] = this->hashTable[homeAddress].searchList(token); // searches the chain
        return std::make_tuple(node != nullptr, probes); // returns both true/false and number of probes
    }

    /**
     * Searches and deletes a token in the current hash table
     */
    size_t deleteToken(const TokenEntry& token) {
        size_t homeAddress = getHash(token.getKey());
        return this->hashTable[homeAddress].deleteNode(token);        
    }

};

template <typename T>
class ClosedHashTables {
private:    
    size_t tableSize; // stores number of buckets
    std::vector<std::unique_ptr<TokenEntry>> hashTable; // actual hashtable

public:
    /**
     * Parameterized constructor
     */
    ClosedHashTables(size_t size) : size(size), hashTable(size) {}

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
        if (i >= this->tableSize) {
            std::cout << "ERROR: Wrong probe length!!!" << endl;
            return -1;
        }

        return homeAddress + i;
    }

    /**
     * Inserts a new token into the hash table
     */
    size_t insertToken(const TokenEntry& token) {
        size_t homeAddress = getHash(token.getKey()); // homeAddress
        size_t i = 0, address = homeAddress;
        while (i < this->tableSize || this->hashTable[address] != nullptr) {
            address = getProbe(homeAddress, i++); // get next linear probe
        }

        if (i == this->tableSize) {
            std::cout << "ERROR: Cannot insert new token. Out of space!" << endl;            
        } else {
            this->hashTable[address] = std::make_unique<TokenEntry>(token.getToken());
        }
        return i;
    }

    /**
     * Searches a token in the current hash table
     */
    std::tuple<bool, size_t> searchToken(const TokenEntry& token) {
        size_t homeAddress = getHash(token.getKey()); // homeAddress
        size_t i = 0, address = homeAddress;
        bool found = false;
        while (i < this->tableSize || this->hashTable[address] != nullptr) {
            if (this->hashTable[address] == token) { // break the loop if token is found
                found = true;
                break;
            }
            address = getProbe(homeAddress, i++); // get next linear probe            
        }

        if (!found) {
            std::cout << "Token not found in hash table." << endl;
            return std::make_tuple(false, i);
        } else {            
            return std::make_tuple(true, i);
        }
    }

    /**
     * Searches and deletes a token in the current hash table
     */
    size_t deleteToken(const TokenEntry& token) {
        size_t homeAddress = getHash(token.getKey()); // homeAddress
        size_t i = 0, address = homeAddress;
        bool found = false;
        while (i < this->tableSize || this->hashTable[address] != nullptr) {
            if (this->hashTable[address] == token) { // break the loop if token is found
                found = true;
                break;
            }
            address = getProbe(homeAddress, i++); // get next linear probe            
        }

        if (!found) {
            std::cout << "Token not found in hash table." << endl;
        } else {            
            this->hashTable[address].reset();
        }
        return i;
    }

};