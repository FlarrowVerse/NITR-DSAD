#include <iostream>
#include <fstream>
#include <cstdlib>
#include <time.h>

#include "../headers/utils.h"
#include "../headers/inputGenerator.h"

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
void generateInput() {
    srand(time(0)); // seeding the random number generator with current time stamp

    // input of word count of file
    int wordCount;
    cout << "Enter a sample file size in words: ";
    cin >> wordCount;

    // input of file name
    string filename;
    cout << "Enter a name for the file to store the data in: ";
    getline(cin >> ws, filename);

    string fileContent = "";

    while (wordCount--) {
        int wordSize = getWordSize(); // fetching a random word size
        fileContent += getWord(wordSize); // fetching a random word based on given word size
    }

    // cout << fileContent << endl; // just displaying for now
    writeToFile(fileContent, filename);
}