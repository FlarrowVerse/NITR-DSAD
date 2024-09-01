#pragma once

#include "../headers/Types.h"

/**
 * definitions of functions for this file
 */
std::string processToken(std::string token); // process a given token
void extractTokens(const std::string &text, const std::string &delimiters, stringList_t &tokens); // split given text into tokens