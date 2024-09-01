#include <iostream>
#include <fstream>
#include <cstdlib>
#include <time.h>

#include "../headers/utils.hpp"
#include "../headers/inputGenerator.hpp"

using std::cin;
using std::cout;
using std::endl;
using std::string;
using std::ofstream;
using std::getline;
using std::ws;


/**
 * Main driver code
 */
void generateInput(int fileWordSize, string filename) {
    srand(time(0)); // seeding the random number generator with current time stamp

    string fileContent = "";

    while (fileWordSize--) {
        int wordSize = getWordSize(); // fetching a random word size
        fileContent += getWord(wordSize); // fetching a random word based on given word size
    }

    // cout << fileContent << endl; // just displaying for now
    writeToFile(fileContent, filename);
}