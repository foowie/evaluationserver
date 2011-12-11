#include <cstdlib>
#include <iostream>
#include <stdio.h>

using namespace std;

int main(int argc, char** argv) {
    #define SIZE 2000000
    unsigned long testa[SIZE];
    for(int i = 0; i < SIZE; i++)
        testa[i] = rand();
    for(int i = 0; i < SIZE; i++) {
        if(testa[i] < 1)
            printf("");
    }
    return 0;
}


