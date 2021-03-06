#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define ACCEPTED		0
#define PRESENTATION_ERROR	1
#define WRONG_ANSWER		2
#define INTERNAL_ERROR		3

char * responses[] = {
	"AC", // 0 Accepted
	"PE", // 1 Presentation Error
	"WA", // 2 Wrong Answer
	"IE" // 3 Internal error
};

/**
 * Evaluate solution
 * @param solution solution data
 * @param input input data
 * @param output output data (may be null)
 * @return ACCEPTED|PRESENTATION_ERROR|WRONG_ANSWER|INTERNAL_ERROR
 */
int process(FILE * solution, FILE * input, FILE * output) {
	return ACCEPTED;
}

/**
 * @param argc 3|4
 * @param argv [runFile, solutionDataFile, inputDataFile, outputDataFile-optional]
 * @return 
 */
int main(int argc, char** argv) {
	if (argc < 3 || argc > 4) {
		printf("%s", responses[INTERNAL_ERROR]);
		return 1;
	}

	FILE* solution = fopen(argv[1], "r");
	FILE* input = fopen(argv[2], "r");
	FILE* output = argc == 2 ? NULL : fopen(argv[3], "r");

	if (solution == NULL || input == NULL || (argc != 3 && output == NULL)) {
		printf("%s", responses[INTERNAL_ERROR]);
		return 1;
	}

	int response = process(solution, input, output);
	printf("%s", responses[response]);

	fclose(solution);
	fclose(input);
	if(argc != 3)
		fclose(output);

	return 0;
}

