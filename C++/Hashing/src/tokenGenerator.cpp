#include <iostream>
#include <fstream>
#include <sstream>
#include <cstdlib>
#include <time.h>

#include "../headers/Types.hpp"
#include "../headers/tokenGenerator.hpp"
#include "../headers/TokenEntry.hpp"

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
 * Given a token process the token to appropriate length
 */
string processToken(string token) {
    if (token.length() == 10) {
        return token;
    } else if (token.length() > 10) { // if length of token is more than 10 characters
        return token.substr(0, 10); // return just first ten characters
    } else {
        int maxCount = 10-(token.length());
        token.append(maxCount, '*');
        return token;
    }
}

/**
 * Given a sample text extract tokens from it based on the forbidden characters
 * Forbidden characters = {, . whitespace}
 */
void extractTokens(const string &text, const string &delimiters, tokenEntryList_t &tokens) {
    stringstream parser(text); // string stream parser
    string token; // to store individual token

    size_t start = 0, end; // pointers to extract a portion of the string
    while ((end = text.find_first_of(delimiters, start)) != string::npos) {
        if (end != start) {
            // if end and start index is not same, extract everything in between
            tokens.push_back(TokenEntry(text.substr(start, end-start))); 
        }
        start = end + 1; // set start as start of next token
    }

    if (start < text.size()) {
        tokens.push_back(text.substr(start)); // add rest of the string as token
    }
    
}