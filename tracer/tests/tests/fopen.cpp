#include <cstdlib>
#include <iostream>
#include <stdio.h>

using namespace std;


int main(int argc, char** argv) {
  FILE* file;
  file = fopen("fopen.cpp", "r");
  fclose(file);

  return 0;
}

