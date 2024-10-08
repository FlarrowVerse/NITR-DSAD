#include "../headers/TokenEntry.hpp"
#include "../headers/utils.hpp"
#include "../headers/tokenGenerator.hpp"

#include <iostream>

using std::string;

// constructor
TokenEntry::TokenEntry(const string& token) : token(token) {
    setKey();
}

TokenEntry::~TokenEntry() {
}

/**
 * Getter methods
 */
string TokenEntry::getToken() const {
    return this->token;
}

int TokenEntry::getKey() const {
    return this->key;
}

/**
 * Setter methods
 */
void TokenEntry::setKey() {
    string processedToken = processToken(this->token);
    this->key = generateKey(processedToken); // store the key based on the token value
}

/**
 * For easy input output
 */
std::ostream& operator<<(std::ostream& os, const TokenEntry& obj) {
    os << "<" << obj.key << ", " << obj.token << ">";
    return os;
}

std::istream& operator>>(std::istream& is, TokenEntry& obj) {
    is >> obj.key >> obj.token;
    return is;
}

/**
 * For comparisons
 */
bool operator>(const TokenEntry& left, const TokenEntry& right) {
    return left.key > right.key;
}

bool operator<(const TokenEntry& left, const TokenEntry& right) {
    return left.key < right.key;
}

bool operator<=(const TokenEntry& left, const TokenEntry& right) {
    return left.key <= right.key;
}

bool operator>=(const TokenEntry& left, const TokenEntry& right) {
    return left.key >= right.key;
}

bool operator==(const TokenEntry& left, const TokenEntry& right) {
    return left.token == right.token;
}

bool operator!=(const TokenEntry& left, const TokenEntry& right) {
    return left.token != right.token;
}