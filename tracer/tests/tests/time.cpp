#include <cstdlib>
#include <iostream>
#include <stdio.h>

using namespace std;


int main(int argc, char** argv) {
    int x = 0;
    for(int i = 0; i < 1000000; i++)
        for(int j = 0; j < 1000000; j++)
            x += rand();
    return 0;
}


