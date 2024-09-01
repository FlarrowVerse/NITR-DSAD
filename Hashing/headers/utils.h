#pragma once

#include "Types.h"

int getASCII(); // random ascii character generator
int getWordSize(); // random word size generator
std::string getWord(int wordSize); // random word generator
void writeToFile(std::string filecontent, std::string filename); // writing to file utility
std::string readFromFile(std::string filename); // readinf from a file
