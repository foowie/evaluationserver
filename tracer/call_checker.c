#include <stdio.h>
#include <stdlib.h>
#include <sys/ptrace.h>
#include <sys/syscall.h>
#include <string.h>

#include "call_checker.h"
#include "responses.h"
#include "debug.h"

#define LIBRARIES_COUNT 5
int libraries_count = LIBRARIES_COUNT;
const char * libraries[LIBRARIES_COUNT] = {
    "ld.so.cache", // chache of loaded libraries
    "libstdc++.so.6", // std
    "libm.so.6", // math
    "libgcc_s.so.1", // gcc
    "libc.so.6", // c
};

/**
 * Creates array of char of given length. In case there's original, is copied in front of new string
 * @param size String size
 * @param original Original string
 * @param original_size Length of original string
 * @return Pointer to new string or null on error
 */
char* create_string(int size, char* original, int original_size) {

    char* str = (char*) malloc(size * sizeof(char));

    if(original != NULL) {
	if(original_size > size) {
	    free(str);
	    return NULL;
	}
	memcpy(str, original, original_size);
	free(original);
    }

    return str;
}

/**
 * Get text from ptrace
 * @param child
 * @param addr
 * @param length Optional
 * @return Pointer to string
 */
char* get_ptrace_text(pid_t child, long addr, int length) {

    union u {
	long val;
	char chars[sizeof(long)];
    } data;

    int str_size = 50;
    char* str = create_string(str_size, NULL, 0);

    int str_pos = 0;
    int i;
    int end = 0;

    while(end == 0) {
	if(str_pos + 6 > str_size) {
	    str = create_string(str_size * 2, str, str_size);
	    str_size *= 2;
	}

	data.val = ptrace(PTRACE_PEEKDATA, child, addr + str_pos, NULL);
	for(i = 0; i < sizeof(long); i++) {
	    if((length == -1 && data.chars[i] == '\0') || (length != -1 && length == i+1)) {
		end = 1;
		break;
	    }
	}
	memcpy(str + str_pos, data.chars, ++i);
	str_pos += --i;
    }
    str[str_pos] = '\0';
    
    return str;
}

/**
 * Checks whenever given string has suffix "end"
 * @param string
 * @param end
 * @return 
 */
int ends_with(const char * string, const char * end) {
    int string_len = strlen(string);
    int end_len = strlen(end);
    
    if(string_len < end_len)
        return 0;
    return strcmp(string + string_len - end_len, end) == 0 ? 1 : 0;
}

/**
 * Checks whenever is given library allowed
 * @param name Name of library with absolute location
 * @return 0=OK, 1=NOT_ALLOWED
 */
int check_library(char* name) {
    int i;
    for(i = 0; i < libraries_count; i++) {
	if(ends_with(name, libraries[i]) != 0)
	    return 0;
    }
    return 1;
}

void print_ptrace_text(pid_t child, long addr, int length) {
    char* str = get_ptrace_text(child, addr, length);
    printf(" --> Argument: '%s'", str);
    free(str);
}

int check_call(long int *eax, struct user_regs_struct *regs, pid_t *child) {
    switch (*eax) {
	case SYS_read: {
	    if(regs->ebx == STDIN_FILENO)
		break; // standard input read
	    if(regs->orig_eax == 3 && regs->edx == 0x200 && regs->eax == 0xffffffda)
		break; // header read of lib
            DUMP_ERROR("SYS_read FROM %ld", regs->ebx)
            DUMP_REGISTRY(regs)
	    return RESTRICTED_FUNCTION; // illegal read
	}
	
	case SYS_write: {
	    if(regs->ebx != STDOUT_FILENO) {
                DUMP_ERROR("SYS_write INTO %ld", regs->ebx)
                DUMP_REGISTRY(regs)
		return RESTRICTED_FUNCTION; // write not to std out
            }
	} break;

        // debug only todo: obalit debugem
	case SYS_writev: {
	    if(regs->ebx != STDERR_FILENO) {
		return RESTRICTED_FUNCTION; // wrote not to std out
            }
	} break;

	case SYS_open:
	case SYS_openat: {
	    char* lib = get_ptrace_text(*child, regs->ebx, -1);
	    if(check_library(lib) != 0) {
                DUMP_ERROR("RESTRICTED FILE OPEN")
                #ifdef DEBUG_ERRORS
                        print_ptrace_text(*child, regs->ebx, -1);
                #endif
		free(lib);
		return RESTRICTED_FUNCTION;
	    }
	    free(lib);
	} break;


	// allowed
	case SYS_close: //6
	case SYS_access: //33
	case SYS_brk: //45
	case SYS_munmap: //91
	case SYS_mprotect: //125
	case SYS_mmap2: //192
	case SYS_fstat64: //197 --
	case SYS_set_thread_area: //243
	case SYS_exit_group: //252
	    break;
            
        case -1:
            DUMP_ERROR("UNKNOWN SYSTEM CALL -1")
            DUMP_REGISTRY(regs)
            return RUNTIME_ERROR;

	default:
            DUMP_ERROR("UNKNOWN SYSTEM CALL %ld", regs->orig_eax)
            DUMP_REGISTRY(regs)
	    return 1;
    }

    return 0;
}
