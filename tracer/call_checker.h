#ifndef CALLCHECKER_H
#define	CALLCHECKER_H

#include <sys/user.h>
#include <unistd.h>

/**
 * Load file with allowed libraries
 * @return 0=ok -1=error
 */
int load_libraries(char * filename);

/**
 * Checks whenever given call is allowed
 * @return responses.h code
 */
int check_call(long int *eax, struct user_regs_struct *regs, pid_t *child);

#endif

