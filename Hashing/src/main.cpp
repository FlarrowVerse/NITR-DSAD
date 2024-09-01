#include <iostream>

#include "../headers/Types.hpp"
#include "../headers/utils.hpp"
#include "../headers/inputGenerator.hpp"
#include "../headers/tokenGenerator.hpp"
#include "../headers/hashing.hpp"
#include "../headers/TokenEntry.hpp"

using std::cin;
using std::cout;
using std::endl;
using std::string;
using std::ifstream;
using std::stringstream;
using std::getline;
using std::ws;


/**
 * Does the initial setup work:
 * 1. Create sample text file
 * 2. Extract tokens from file
 * 3. Stores tokens in separate file
 */
void setup(tokenEntryList_t& tokenList) {
    // list of forbidden characters
    string filename, fileContent, delimiters = ",. ";

    // input of word count of file
    int wordCount;
    cout << "Enter a sample file size in words: ";
    cin >> wordCount;

    // input of file name
    cout << "Enter a name for the file to store the data in: ";
    getline(cin >> ws, filename);

    generateInput(wordCount, filename); // generate a sample input file with random word count and given filename

    fileContent = readFromFile(filename); // reading sample data from file

    // extract tokens
    extractTokens(fileContent, delimiters, tokenList);
}

/**
 * Main driver code
 */
int main() {

    tokenEntryList_t tokens;

    setup(tokens); // initial setup steps

    for (const auto& token: tokens) {
        cout << token << endl;
    }

    return 0;
}