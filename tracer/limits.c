#include <stdio.h>
#include <stdlib.h>
#include <sys/resource.h>
#include <sys/time.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>

#include "limits.h"
#include "proc_stat.h"
#include "debug.h"

void init_limits(struct Limits *limit, pid_t pid) {
	limit->pid = pid;

	limit->memory_error = 0;
	limit->output_error = 0;
	limit->time_error = 0;

	limit->memory_peek = 0;

	gettimeofday(&limit->start_time, NULL);
	limit->miliseconds = 0.0f;
	memset(&limit->stop_time, 0, sizeof (struct timeval));

}

int has_limit_errors(struct Limits * limit) {
	return limit->memory_error || limit->output_error || limit->time_error;
}

int disable_core_dump(struct Limits *limit) {
	if (getpid() != limit->pid) {
		DUMP_ERROR("INVALID PID ON disableCoreDump")
		return -1;
	}

	struct rlimit rimit;
	// read old limit (hard limit)
	if (getrlimit(RLIMIT_CORE, &rimit) != 0) {
		DUMP_ERROR("CORE DUMP LIMIT GET FAILED ERRNO=%d", errno)
		return -1;
	}

	// assign max limit (soft limit)
	rimit.rlim_cur = 0;
	// set max limit
	if (setrlimit(RLIMIT_CORE, &rimit) != 0) {
		DUMP_ERROR("CORE DUMP LIMIT SET FAILED ERRNO=%d", errno)
		return -1;
	}
	return 0;
}

int initialize_memory_limit(struct Limits *limit) {
	return 0;
}

int initialize_output_limit(struct Limits *limit) {
	if (getpid() != limit->pid) {
		DUMP_ERROR("INVALID PID ON setOutputLimit")
		return -1;
	}

	struct rlimit rimit;
	// read old limit (soft limit)
	if (getrlimit(RLIMIT_FSIZE, &rimit) != 0) {
		DUMP_ERROR("OUTPUT LIMIT GET FAILED ERRNO=%d", errno)
		return -1;
	}

	// assign max limit (hard limit)
	rimit.rlim_cur = limit->output_limit;
	// set max limit
	if (setrlimit(RLIMIT_FSIZE, &rimit) != 0) {
		DUMP_ERROR("OUTPUT LIMIT SET FAILED ERRNO=%d", errno)
		return -1;
	}
	return 0;
}

int initialize_time_limit(struct Limits *limit) {
	struct itimerval time;
	if (getpid() != limit->pid) {
		DUMP_ERROR("INVALID PID ON initializeTimeLimit")
		return -1;
	}

	memset(&time, 0, sizeof (struct itimerval));
	time.it_value.tv_usec = (limit->time_limit % 1000) * 1000;
	time.it_value.tv_sec = limit->time_limit / 1000;

	if (setitimer(ITIMER_VIRTUAL, &time, NULL) != 0) {
		DUMP_ERROR("TIMER SET ERROR ERRNO=%d", errno)
		return -1;
	}

	return 0;
}

void check_limits(struct Limits *limit) {
	struct Stat stat;

	// get /proc/[pid]/stat
	get_stat(limit->pid, &stat);

	// test memory limit
	if (stat.vsize > limit->memory_peek)
		limit->memory_peek = stat.vsize;
	if (stat.vsize > limit->memory_limit)
		limit->memory_error = 1;
}

void stop_timer(struct Limits *limit) {
	gettimeofday(&limit->stop_time, NULL);

	limit->miliseconds =
			(limit->stop_time.tv_sec - limit->start_time.tv_sec) * 1000.0
			+ ((limit->stop_time.tv_usec - limit->start_time.tv_usec) / 1000.0);
}



