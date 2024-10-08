/* core libs */
#include <iostream>
#include <limits>
#include <algorithm>
#include <cstring>
#include <time.h>

/* use defined libs */
#include "../headers/Types.hpp"
#include "../headers/utils.hpp"
#include "../headers/inputGenerator.hpp"
#include "../headers/tokenGenerator.hpp"

#include "../headers/TokenEntry.hpp"
#include "../headers/LinkedList.hpp"
#include "../headers/HashTables.hpp"
#include "../headers/BinarySearchTree.hpp"
#include "../headers/DataStructureAPI.hpp"

using std::cin;
using std::cout;
using std::endl;
using std::string;
using std::ifstream;
using std::stringstream;
using std::getline;
using std::ws;
using std::min;


/**
 * Generate sample input file
 */
void generateInputFile() {
    // list of forbidden characters
    string filename = "sampleInput.txt", fileContent, delimiters = ",. ";
    // input of word count of file
    int wordCount;
    cout << "Enter a sample file size in words: ";
    cin >> wordCount;

    generateInput(wordCount, filename); // generate a sample input file with random word count and given filename
}

/**
 * Does the initial setup work:
 * 1. Create sample text file
 * 2. Extract tokens from file
 * 3. Stores tokens in separate file
 */
void setup(tokenEntryList_t& tokenList) {
    srand(time(0)); // seeding the random number generator with current time stamp
    string filename = "sampleInput.txt", fileContent, delimiters = ",. ";    
    
    cout << "Enter the input filename: ";
    getline(cin >> ws, filename);
    
    fileContent = readFromFile(filename); // reading sample data from file
    // extract tokens
    extractTokens(fileContent, delimiters, tokenList);
}

/**
 * Main driver code
 */
int main() {

    int runs = 0; // number of runs
    string methods[4] = {"OHT-U", "OHT-O", "CHT-L", "BST-N"};
    string ops[4] = {"Not Found", "Found", "Inserted", "Deleted"};

    size_t probes[4][4];
    int count[4];

    memset(probes, 0, sizeof(probes));
    memset(count, 0, sizeof(count));

    // testing loop
    while (++runs) {
        cout << endl << "############################################# STARTING TEST LOOP: " << runs << " ########################################" << endl;
        tokenEntryList_t tokens;
        setup(tokens); // initial setup steps
        cout << "Tokens extracted from file...." << endl;

        // getting random number of methods to test for
        int M[4] = {1, 2, 3, 4}; // methods
        int m = getRandom(10, 20); // size of ds
        int n = getRandom(0, min((size_t)m, tokens.size())); // initial number of insertions
        int I = getRandom(0, INT_MAX); // get a random integer
        string operations = radix3(I); // radix-3 representation

        cout << "Data Structure Size: " << m << endl; 
        cout << "Initial Insertions: " << n << endl;
        cout << "Operations: " << I << "--(radix-3)-->" << operations << endl;     


        /**
         * Data structure declarations
         */
        OHT_t<TokenEntry> unorderedHT(m, false);        
        OHT_t<TokenEntry> orderedHT(m, true);
        CHT_t<TokenEntry> closedHT(m);
        BST_t<TokenEntry> bst;
        cout << "Empty Data Structures created...." << endl;

        /**
         * Inserting first n distinct tokens
         * into empty structure
         */
        cout << "Inserting first " << n << " tokens into empty Data structures....." << endl;
        for (int i = 0; i < n; i++) {     
            for (int method: M) {
                switch (method) {
                    case 1: operateOHT(unorderedHT, tokens[i], 1); break;
                    case 2: operateOHT(orderedHT, tokens[i], 1); break;
                    case 3: operateCHT(closedHT, tokens[i], 1); break;
                    case 4: operateBST(bst, tokens[i], 1); break;
                }
            }
        }
        cout << endl << "==========================INITIAL INSERTIONS COMPLETE=============================================================" << endl;

        int index = n;

        // scan rest of the files for performing the corresponding operations
        for (char c: operations) {            
            size_t probe = 0;
            bool status;
            for (int method: M) { // perform operation for each method
                int opCode = c - '0'; // operation code {0, 1, 2}              

                switch (method) {
                    case 1: std::tie(status, probe) = operateOHT(unorderedHT, tokens[index], opCode); break;
                    case 2: std::tie(status, probe) = operateOHT(orderedHT, tokens[index], opCode); break;
                    case 3: std::tie(status, probe) = operateCHT(closedHT, tokens[index], opCode); break;
                    case 4: std::tie(status, probe) = operateBST(bst, tokens[index], opCode); break;
                }
                cout << "***********************OPERATION COMPLETE ON SINGLE DS***********************************************************" << endl;

                if (opCode == 0 && !status) opCode = 0;
                else opCode++;

                probes[method - 1][opCode] += probe; // updating probe count
                count[opCode]++; // updating operation count
            }
            index++; 
            cout << "***********************SINGLE OPERATION COMPLETE ON ALL DS***********************************************************" << endl;
        }
        cout << "---------------------------------------ALL OPERATIONS COMPLETE ON ALL DS-------------------------------------------------" << endl;

        cout << "Do you want to take another test run? (Y/N): ";
        string choice;
        getline(cin >> ws, choice);
        if (choice.at(0) != 'y' && choice.at(0) != 'Y') break; // if not yes break loop
    }

    cout << "\n\t\t";
    for (string op: ops) {
        cout << op << "\t\t";
    }
    cout << endl;

    for (int i = 0; i < sizeof(methods)/sizeof(string); i++) {
        cout << methods[i] << "\t\t";
        for (int j = 0; j < sizeof(ops)/sizeof(string); j++) {
           cout << ((double)probes[i][j]/count[j]) << "\t\t\t";
        }
        cout << endl;
    }

    cout << ":::Legends:::" << endl;
    cout << "OHT-U: Open Hashtable with Unorderd Chaining" << endl;
    cout << "OHT-O: Open Hashtable with Orderd Chaining" << endl;
    cout << "CHT-L: Closed Hashtable with Linear Probing" << endl;
    cout << "BST-N: Binary Search Tree not height balanced" << endl;

    return 0;
}