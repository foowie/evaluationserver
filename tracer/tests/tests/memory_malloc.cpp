#include <cstdlib>
#include <iostream>
#include <stdio.h>

using namespace std;


int main(int argc, char** argv) {
    #define SIZE 2000000
    unsigned long * testa = (unsigned long*) malloc(SIZE * sizeof(unsigned long));
    for(int i = 0; i < SIZE; i++)
        testa[i] = rand();
    for(int i = 0; i < SIZE; i++) {
        if(testa[i] < 1)
            printf("");
    }
    free (testa);
    return 0;
}


