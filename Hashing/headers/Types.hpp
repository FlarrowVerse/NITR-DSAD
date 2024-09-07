#pragma once
/**
 * Core libs
 */
#include <vector>
#include <string>
#include <functional>

/**
 * Custom classes
 */
#include "TokenEntry.hpp"
#include "LinkedList.hpp"
#include "HashTables.hpp"
#include "BinarySearchTree.hpp"

/**
 * All the typedefs
 */
typedef std::vector<std::string> stringList_t;
typedef std::vector<TokenEntry> tokenEntryList_t;

/**
 * All aliases
 */
template <typename T>
using DLL_t = DoublyLinkedList<T>;
template <typename T>
using OHT_t = OpenHashTables<T>;
template <typename T>
using CHT_t = ClosedHashTables<T>;
template <typename T>
using BST_t = BinarySearchTree<T>;
