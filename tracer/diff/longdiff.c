#include <stdio.h>
#include <stdlib.h>
#include <string.h>


#define ACCEPTED			0
#define PRESENTATION_ERROR	1
#define WRONG_ANSWER		2
#define INTERNAL_ERROR		3

char * responses[] = {
	"AC", // 0 Accepted
	"PE", // 1 Presentation Error
	"WA", // 2 Wrong Answer
	"IE" // 3 Internal error
};

int process(FILE * solution, FILE * input, FILE * output) {
	long tokenA, tokenB;
	int readedA, readedB;

	do {
		readedA = fscanf(solution, "%ld", &tokenA);
		readedB = fscanf(output, "%ld", &tokenB);

		if (readedA != readedB)
			return WRONG_ANSWER;
		if (tokenA != tokenB)
			return WRONG_ANSWER;
	} while (readedA != EOF);

	return ACCEPTED;
}

/**
 * @param argc 3
 * @param argv [solutionDataFile, inputDataFile, outputDataFile]
 * @return 
 */
int main(int argc, char** argv) {
	if (argc != 3) {
		printf("%s", responses[INTERNAL_ERROR]);
		return 1;
	}

	FILE* solution = fopen(argv[0], "r");
	FILE* input = fopen(argv[1], "r");
	FILE* output = fopen(argv[2], "r");

	if (solution == NULL || input == NULL || output == NULL) {
		printf("%s", responses[INTERNAL_ERROR]);
		return 1;
	}

	int response = process(solution, input, output);
	printf("%s", responses[response]);

	fclose(solution);
	fclose(input);
	fclose(output);

	return 0;
}

