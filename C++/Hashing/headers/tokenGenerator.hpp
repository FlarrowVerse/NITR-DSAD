#pragma once

#include "../headers/Types.hpp"

/**
 * definitions of functions for this file
 */
std::string processToken(std::string token); // process a given token
void extractTokens(const std::string &text, const std::string &delimiters, tokenEntryList_t &tokens); // split given text into tokens