#include <iostream>

#include "../headers/Types.hpp"
#include "../headers/utils.hpp"
#include "../headers/inputGenerator.hpp"
#include "../headers/tokenGenerator.hpp"
#include "../headers/hashing.hpp"
#include "../headers/TokenEntry.hpp"
#include "../headers/LinkedList.hpp"

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
    string filename = "sampleInput.txt", fileContent, delimiters = ",. ";

    // input of word count of file
    int wordCount;
    cout << "Enter a sample file size in words: ";
    cin >> wordCount;

    // input of file name
    // cout << "Enter a name for the file to store the data in: ";
    // getline(cin >> ws, filename);

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

    DLL_TE_t tokenList;

    int n;
    cout << "Enter number of tokens you want to parse: ";
    cin >> n;

    for (int i = 0; i < n; i++) {
        tokenList.addNewNode(tokens[i]);
    }

    cout << tokenList << endl;

    return 0;
}