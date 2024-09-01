#include <iostream>
#include <fstream>
#include <cstdlib>
#include <time.h>

#include "../headers/utils.h"

using std::cin;
using std::cout;
using std::endl;
using std::string;
using std::getline;
using std::ifstream;
using std::ofstream;

/**
 * Returns the ASCII code of a printable character
 * ASCII range = [32, 126]
 */
int getASCII() {
    return (rand() % 95) + 32;
}

/**
 * Returns a random word size between 5 and 45
 */
int getWordSize() {
    return (rand() % 41) + 5;
}

/**
 * Returns a word based on size provided
 */
string getWord(int wordSize) {
    string word = "";
    for (int charCount = 0; charCount < wordSize; charCount++) {
        word += (char)getASCII(); // getting a random ascii code and appending it to the file as string        
    }
    // randomly deciding to add a forbidden character at the end of a word
    if (rand() % 2) {
        switch (rand() % 3) {
        case 0:
            word += ",";
            break;
        case 1:
            word += ".";
            break;
        case 2:
            word += " ";
            break;
        }
    }
    return word;
}

/**
 * Given a string writes to a text file
 */
void writeToFile(string fileContent, string filename) {
    // creating and opening file in write mode
    ofstream file ("files/"+filename);

    // writing to file
    file << fileContent;

    file.close(); // closing file
}

/**
 * Given a file name reads it and returns contents as a string
 */
string readFromFile(string filename) {
    // creating and opening file in read mode
    ifstream file ("files/"+filename);

    string line; // to hold a single line from the file
    string fileContent = "";

    // read from file

    // checking if the file is actually open or not
    if (file.is_open()) { 
        while (getline(file, line)) {
            fileContent += line + "\n";
        }        
    }   

    file.close(); // closing file

    return fileContent;
}