#include <iostream>

#include "../headers/Types.h"
#include "../headers/utils.h"
#include "../headers/inputGenerator.h"
#include "../headers/tokenGenerator.h"
#include "../headers/hashing.h"

using std::cin;
using std::cout;
using std::endl;
using std::string;
using std::ifstream;
using std::stringstream;
using std::getline;
using std::ws;

/**
 * Main driver code
 */
int main() {

    generateInput();
    
    string filename, fileContent, delimiters = ",. ";
    stringList_t tokens;

    cout << "Enter the sample file name:";
    
    getline(cin >> ws, filename); // filename input
    fileContent = readFromFile(filename); // reading sample data from file

    // extract tokens
    extractTokens(fileContent, delimiters, tokens);
    string tokenString = "";
    for (const auto &token : tokens) {
        cout << generateKey(token) << endl;
        tokenString += token + "\n";        
    }

    writeToFile(tokenString, "tokensFile.txt");

    return 0;
}