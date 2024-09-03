#pragma once

#include <string>

class TokenEntry {
private:
    /* data */
    std::string token;
    int key;
public:
    TokenEntry(const std::string& token); // constructor
    ~TokenEntry(); // destructor

    /**
     * Getter methods
     */
    std::string getToken() const;
    int getKey() const;

    /**
     * Setter methods
     */
    void setToken(std::string token);
    void setKey();

    /**
     * For reading and writing to streams
     */
    friend std::ostream& operator<<(std::ostream& os, const TokenEntry& obj);
    friend std::istream& operator>>(std::istream& is, TokenEntry& obj);
};