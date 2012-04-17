#include <stdio.h>
#include <stdlib.h>
#include <sys/ptrace.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <sys/reg.h>
#include <sys/user.h>
#include <sys/syscall.h>
#include <sys/stat.h>
#include <string.h>
#include <errno.h>

#include "main.h"
#include "call_checker.h"
#include "responses.h"
#include "limits.h"
#include "debug.h"
#include <fcntl.h>


char * responses[] = {
	"AC", // 0 Accepted
	"CE", // 1 Compile Error
	"PE", // 2 Presentation Error
	"WA", // 3 Wrong Answer
	"RE", // 4 Runtime Error
	"TL", // 5 Time Limit Exceeded
	"ML", // 6 Memory Limit Exceeded
	"OL", // 7 Output Limit Exceeded
	"RF" // 8 Restricted Function
};

void send_response(struct Limits * limit, int response) {
	stop_timer(limit);
	kill(limit->pid, SIGKILL);
	printf("%s %.0lf %ld", responses[response], limit->miliseconds, limit->memory_peek);
	close_debug();
	exit(0);
}

void fatal_error(char * message) {
	fprintf(stderr, "%s", message);
	exit(1);
}

void client_fatal_error(char * message) {
	fprintf(stderr, "%s", message);
	exit(1);
}

void execute_program(char * absolute_name, char * input_name, char * output_name) {
	int fin, fout;
	char * name = strrchr(absolute_name, '/') + 1;

	if (name == NULL)
		name = absolute_name;

	// set new stdin from file
	fin = open(input_name, O_RDONLY);
	if (fin == -1)
		client_fatal_error("Can't open input file");
	if (dup2(fin, STDIN_FILENO) == -1)
		client_fatal_error("Can't redirect STDIN");

	// set new stdout into output file
	fout = creat(output_name, S_IRWXU | S_IRWXG | S_IRWXO);
	if (fout == -1)
		client_fatal_error("Can't open input file");
	if (dup2(fout, STDOUT_FILENO) == -1)
		client_fatal_error("Can't redirect STDOUT");

	// start tracing
	if (ptrace(PTRACE_TRACEME, 0, NULL, NULL) == -1)
		client_fatal_error("Can't start ptrace");


	// run program
	execl(absolute_name, name, NULL);

	client_fatal_error("Error when running program");
}

int trace(struct Limits * limit) {
	pid_t child = limit->pid;
	int init = 1;
	long orig_eax;
	int status;
	int insyscall = 0;
	struct user_regs_struct regs;

	while (1) {
		wait(&status); // wait for system call or exit
		if (WIFEXITED(status)) { // exit check
			send_response(limit, ACCEPTED); // common termination
		}
		if (WIFSTOPPED(status)) { // signal
			switch (WSTOPSIG(status)) {
				case(SIGXFSZ):
				{ // output limit exceeded
					DUMP_ERROR("OUTPUT LIMIT EXCEEDED")
					limit->output_error = 1;
					send_response(limit, OUTPUT_LIMIT_EXCEEDED);
				}

				case(SIGTRAP):
				{ // breakpoint ... ?
					check_limits(limit);
					if (has_limit_errors(limit)) {
						if (limit->memory_error) {
							DUMP_ERROR("MEMORY LIMIT ERROR")
							send_response(limit, MEMORY_LIMIT_EXCEEDED);
						}
						DUMP_ERROR("UNKNOWN ERROR")
					}
					break;
				}

				case(SIGVTALRM): // timer notification
				case(SIGALRM):
				case(SIGPROF):
				{
					DUMP_ERROR("TIME LIMIT EXCEEDED")
					send_response(limit, TIME_LIMIT_EXCEEDED);
				}

				case(SIGSEGV):
				{ // segfalut or mem exceeded
					DUMP_ERROR("SEGMENTATION FALUT")
					send_response(limit, RUNTIME_ERROR);
				}
			}
		}

		orig_eax = ptrace(PTRACE_PEEKUSER, child, 4 * ORIG_EAX, NULL); // get system call type

		if (init == 1 && orig_eax == SYS_execve) { // initial program execute
			init = 0;
		} else {
			if (insyscall == 0) { // system call start
				insyscall = 1;
				if (ptrace(PTRACE_GETREGS, child, NULL, &regs) == -1)
					fatal_error("ptrace failed");
				int result = check_call(&orig_eax, &regs, &child);
				if (result != 0) {
					send_response(limit, result); // send response with fail code
				}
			} else { // system call ends
				insyscall = 0;
			}
		}
		if (ptrace(PTRACE_SYSCALL, child, NULL, NULL) == -1) // continue with syscall ...
			fatal_error("ptrace syscall failed");
	}
	send_response(limit, ACCEPTED);
}

int main(int argc, char *argv[]) {
	pid_t child;
	struct Limits limit;

	if (argc < 7 || argc > 9) {
		printf("Arguments for run are: program_to_run input_file_name output_file_name time_limit[ms] memory_limit[byte] output_limit[byte] [log_file_name|-] [libraries_file_name|-]\n");
		return -1;
	}

	sscanf(argv[4], "%ld", &limit.time_limit);
	sscanf(argv[5], "%ld", &limit.memory_limit);
	sscanf(argv[6], "%ld", &limit.output_limit);

	child = fork();

	if (child == 0) { // child proces
		init_limits(&limit, getpid());
		initialize_output_limit(&limit);
		initialize_memory_limit(&limit);
		initialize_time_limit(&limit);

		execute_program(argv[1], argv[2], argv[3]);
	} else if (child == -1) { // error
		fatal_error("fork failed");
		return 1;
	} else { // parent proces
		int result;
		if(argc > 8 && argv[8][0] != '-')
			if(load_libraries(argv[8]) != 0)
				fatal_error("cant load libraries");
			
		char * fileName = strrchr(argv[1], '/');
		init_debug((argc > 7 && argv[7][0] != '-') ? argv[7] : NULL, fileName == NULL ? argv[1] : fileName + 1);
		init_limits(&limit, child);
		result = trace(&limit);
		close_debug();
		return result;
	}
}
