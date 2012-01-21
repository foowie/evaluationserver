#ifndef CALLCHECKER_H
#define	CALLCHECKER_H

#include <sys/user.h>
#include <unistd.h>

/**
 * Checks whenever given call is allowed
 * @return responses.h code
 */
int check_call(long int *eax, struct user_regs_struct *regs, pid_t *child);

#endif

