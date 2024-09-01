#include <iostream>
#include <string>

#include "../headers/hashing.h"

using std::string;

int generateKey(string token) {
    int asciiSum = 0;
    for (auto &tokenChar : token) {
        asciiSum += tokenChar;
    }

    return asciiSum;
    
}