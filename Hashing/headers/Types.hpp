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
using DLL_TE_t = DoublyLinkedList<TokenEntry>;