/**
 * Core libs
 */
#include <vector>
#include <string>

/**
 * Custom classes
 */
#include "TokenEntry.hpp"
#include "LinkedList.hpp"

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