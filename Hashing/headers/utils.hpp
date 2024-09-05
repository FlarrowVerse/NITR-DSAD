#pragma once

#include "Types.hpp"

int getRandom(int start, int range); // random ascii character generator
int getWordSize(); // random word size generator
std::string getWord(int wordSize); // random word generator
void writeToFile(std::string filecontent, std::string filename); // writing to file utility
std::string readFromFile(std::string filename); // readinf from a file
