#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#include <stdarg.h>
#include <time.h>

#include "debug.h"

#define DEBUG_PREFIX_LENGTH 255

FILE * log_file;
char log_prefix[DEBUG_PREFIX_LENGTH];

void init_debug(const char * file_name, const char * prefix) {
    log_file = fopen(file_name, "a");
    
    strncpy(log_prefix, prefix, DEBUG_PREFIX_LENGTH - 1);
    log_prefix[DEBUG_PREFIX_LENGTH - 1] = '\0';
}

void log_time() {
    time_t now;
    struct tm *date;
    char li[21];

    time(&now);
    date = localtime(&now);

    strftime(li, 20, "%d.%m.%Y %H:%M:%S", date);

    fprintf(log_file, "%s", li);
}

void log_message(const char * name, const char * format, ...) {
    va_list arg;
    va_start(arg, format);
    log_time();
    fprintf(log_file, " - %s - %s - ", name, log_prefix);
    vfprintf(log_file, format, arg);
    fprintf(log_file, "\n");
    va_end(arg);
}

void close_debug() {
    fclose(log_file);
}