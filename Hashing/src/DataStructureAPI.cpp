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
        case 0: std::tie(success, probes) = hashTable.searchToken(token); break;
        case 1: probes = hashTable.insertToken(token); break;
        case 2: probes = hashTable.deleteToken(token); break;
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
        case 0: std::tie(success, probes) = hashTable.searchToken(token); break;
        case 1: probes = hashTable.insertToken(token); break;
        case 2: probes = hashTable.deleteToken(token); break;
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
        case 0: std::tie(node, probes) = bst.searchToken(token); 
            success = !node;
            break;
        case 1: probes = bst.insertToken(token); break;
        case 2: probes = bst.deleteToken(token); break;
    }

    return std::make_tuple(success, probes);
}
