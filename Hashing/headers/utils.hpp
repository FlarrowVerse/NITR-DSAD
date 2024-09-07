#pragma once

#include "Types.hpp"

/**
 * Random ops
 */
int getRandom(int start, int range); // random ascii character generator
int getWordSize(); // random word size generator
std::string getWord(int wordSize); // random word generator

/**
 * Number ops
 */
std::string radix3(int i);

/**
 * File ops
 */
void writeToFile(std::string filecontent, std::string filename); // writing to file utility
std::string readFromFile(std::string filename); // readinf from a file

/**
 * DS ops
 */
int generateKey(std::string token); // generates a key given a token