#include <iostream>
#include <fstream>
#include <sstream>
#include <cstdlib>
#include <time.h>
#include <vector>
#include <string>

/**
 * All the things we need from std namespace
 */
using std::cin;
using std::cout;
using std::endl;
using std::string;
using std::ifstream;
using std::stringstream;
using std::getline;
using std::ws;

/**
 * All the typedefs
 */
typedef std::vector<string> stringList_t;

/**
 * External functions first
 */
extern void writeToFile(string fileContent, string filename);
extern void generateInput();

/**
 * definitions of functions for this file next
 */
string readFromFile(string filename); // readinf from a file
string processToken(string token); // process a given token
void extractTokens(const string &text, const string &delimiters, stringList_t &tokens); // split given text into tokens


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
        tokenString += token + "\n";        
    }    

    writeToFile(tokenString, "tokensFile.txt");

    return 0;
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

/**
 * Given a token process the token to appropriate length
 */
string processToken(string token) {
    if (token.length() == 10) {
        return token;
    } else if (token.length() > 10) { // if length of token is more than 10 characters
        return token.substr(0, 10); // return just first ten characters
    } else {
        int maxCount = 10-(token.length());
        for (int i = 0; i < maxCount; i++) {
            token += '*';
        }
        return token;
    }
}

/**
 * Given a sample text extract tokens from it based on the forbidden characters
 * Forbidden characters = {, . whitespace}
 */
void extractTokens(const string &text, const string &delimiters, stringList_t &tokens) {
    stringstream parser(text); // string stream parser
    string token; // to store individual token

    size_t start = 0, end; // pointers to extract a portion of the string
    while ((end = text.find_first_of(delimiters, start)) != string::npos) {
        if (end != start) {
            // cout << text.substr(start, end-start) << endl;
            // if end and start index is not same, extract everything in between
            tokens.push_back(processToken(text.substr(start, end-start))); 
        }
        start = end + 1; // set start as start of next token
    }

    if (start < text.size()) {
        tokens.push_back(text.substr(start)); // add rest of the string as token
    }
    
}