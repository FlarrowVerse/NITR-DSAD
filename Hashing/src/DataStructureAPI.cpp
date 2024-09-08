#include <iostream>
#include <tuple>

#include "../headers/Types.hpp"
#include "../headers/TokenEntry.hpp"
#include "../headers/DataStructureAPI.hpp"

/**
 * Operate on the Open hash table
 */
std::tuple<bool, size_t> operateOHT(OHT_t<TokenEntry>& hashTable, const TokenEntry& token, int opCode) {
    size_t probes = 0;
    bool success = true;

    switch (opCode) {
        case 0: std::cout << "Searching for " << token << " in Open Hash table " << std::endl << hashTable << std::endl;
            std::tie(success, probes) = hashTable.searchToken(token); 
            break;
        case 1: std::cout << "Inserting " << token << " into Open Hash table " << std::endl << hashTable << std::endl;
            probes = hashTable.insertToken(token); break;
        case 2: std::cout << "Deleting " << token << " from Open Hash table " << std::endl << hashTable << std::endl;
            probes = hashTable.deleteToken(token); break;
    }
    return std::make_tuple(success, probes);
}

/**
 * Operate on closed hash table
 */
std::tuple<bool, size_t> operateCHT(CHT_t<TokenEntry>& hashTable, const TokenEntry& token, int opCode) {
    size_t probes = 0;
    bool success = true;

    switch (opCode) {
        case 0: std::cout << "Searching for " << token << " in Closed Hash table " << std::endl << hashTable << std::endl;
            std::tie(success, probes) = hashTable.searchToken(token); break;
        case 1: std::cout << "Inserting " << token << " into Closed Hash table " << std::endl << hashTable << std::endl;
            probes = hashTable.insertToken(token); break;
        case 2: std::cout << "Deleting " << token << " from Closed Hash table " << std::endl << hashTable << std::endl;
            probes = hashTable.deleteToken(token); break;
    }
    return std::make_tuple(success, probes);
}

/**
 * Operate on BST
 */
std::tuple<bool, size_t> operateBST(BST_t<TokenEntry>& bst, const TokenEntry& token, int opCode) {
    size_t probes = 0;
    bool success = true;
    TreeNode<TokenEntry>* node;
    switch (opCode) {
        case 0: std::cout << "Searching for " << token << " in Binary Search Tree " << std::endl << bst << std::endl;
            std::tie(node, probes) = bst.searchToken(token); 
            success = !node;
            break;
        case 1: std::cout << "Inserting " << token << " into Binary Search Tree " << std::endl << bst << std::endl;
            probes = bst.insertToken(token); break;
        case 2: std::cout << "Deleting " << token << " from Binary Search Tree " << std::endl << bst << std::endl;
            probes = bst.deleteToken(token); break;
    }

    return std::make_tuple(success, probes);
}
