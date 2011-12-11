#include <stdio.h>
#include <stdlib.h>

#include "proc_stat.h"

int get_stat(pid_t pid, struct Stat * stat) {
    #define BUFFER_SIZE 8191
    char file_name[256];
    char buffer[BUFFER_SIZE];
    FILE * file;
    size_t readed;
    char * token = buffer;
    
    // prepare file name
    sprintf(file_name, "/proc/%d/stat", pid);
    
    // load data
    file = fopen(file_name, "r"); // todo 
    if(file == NULL)
        return 1;
    readed = fread(buffer, sizeof(char), BUFFER_SIZE - 1, file);
    fclose(file);
    buffer[readed] = '\0';
    // todo too low buffer? or not ...
    
    //load info - "man proc"
    #define READ_TOKEN strsep(&token, " ");
    READ_TOKEN // %d pid 
    READ_TOKEN // %s comm
    READ_TOKEN // %c state
    READ_TOKEN // %d ppid
    READ_TOKEN // %d pgrp
    READ_TOKEN // %d session
    READ_TOKEN // %d tty_nr
    READ_TOKEN // %d tpgid
    READ_TOKEN // %u flags
    READ_TOKEN // %lu minflt
    READ_TOKEN // %lu cminflt
    READ_TOKEN // %lu majflt
    READ_TOKEN // %lu cmajflt
    READ_TOKEN // %lu utime
    READ_TOKEN // %lu stime
    READ_TOKEN // %ld cutime
    READ_TOKEN // %ld cstime
    READ_TOKEN // %ld priority
    READ_TOKEN // %ld nice
    READ_TOKEN // %ld num_threads
    READ_TOKEN // %ld itrealvalue
    READ_TOKEN // %llu starttime
    sscanf(token, "%lu", &stat->vsize);  
    READ_TOKEN // %lu vsize
    READ_TOKEN // %ld rss
    READ_TOKEN // %lu rsslim
    READ_TOKEN // %lu startcode
    READ_TOKEN // %lu endcode
    READ_TOKEN // %lu startstack
    READ_TOKEN // %lu kstkesp
    READ_TOKEN // %lu kstkeip
    READ_TOKEN // %lu signal
    READ_TOKEN // %lu blocked
    READ_TOKEN // %lu sigignore
    READ_TOKEN // %lu sigcatch
    READ_TOKEN // %lu wchan
    READ_TOKEN // %lu nswap
    READ_TOKEN // %lu cnswap
    READ_TOKEN // %d exit_signal
    READ_TOKEN // %d processor
    READ_TOKEN // %u rt_priority
    READ_TOKEN // %u policy
    READ_TOKEN // %llu delayacct_blkio_ticks
    READ_TOKEN // %lu guest_time
    READ_TOKEN // %ld cguest_time
}

