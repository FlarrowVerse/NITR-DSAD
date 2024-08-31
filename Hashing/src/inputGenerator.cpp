#include <iostream>
#include <fstream>
#include <cstdlib>
#include <time.h>

using std::cin;
using std::cout;
using std::endl;
using std::string;
using std::ofstream;


/**
 * Function definitions
 */
int getASCII(); // random ascii character generator
int getWordSize(); // random word size generator
string getWord(int wordSize); // random word generator
void writeToFile(string filecontent, string filename); // writing to file utility
void generateInput(); // driver function



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
 * Main driver code
 */
void generateInput() {
    srand(time(0)); // seeding the random number generator with current time stamp

    int wordCount;
    cout << "Enter a sample file size in words: ";
    cin >> wordCount;

    string fileContent = "";

    while (wordCount--) {
        int wordSize = getWordSize(); // fetching a random word size
        fileContent += getWord(wordSize); // fetching a random word based on given word size
    }

    // cout << fileContent << endl; // just displaying for now
    writeToFile(fileContent, "sampleFile.txt");
}