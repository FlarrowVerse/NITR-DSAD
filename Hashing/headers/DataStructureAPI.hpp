#pragma once

#include <iostream>

#include "../headers/Types.hpp"
#include "../headers/TokenEntry.hpp"

/**
 * Operate on the Open hash table
 */
std::tuple<bool, size_t> operateOHT(OHT_t<TokenEntry>& hashTable, const TokenEntry& token, int opCode);

/**
 * Operate on closed hash table
 */
std::tuple<bool, size_t> operateCHT(CHT_t<TokenEntry>& hashTable, const TokenEntry& token, int opCode);

/**
 * Operate on BST
 */
std::tuple<bool, size_t> operateBST(BST_t<TokenEntry>& bst, const TokenEntry& token, int opCode);
