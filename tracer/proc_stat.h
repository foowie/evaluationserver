#ifndef PROCSTAT_H
#define	PROCSTAT_H

#ifdef	__cplusplus
extern "C" {
#endif

    // Program statistics
    struct Stat {
        long unsigned int vsize; // Program size in memory
    };
    
    /**
     * Get statistics for given process
     */
    int get_stat(pid_t pid, struct Stat * stat);

#ifdef	__cplusplus
}
#endif

#endif	/* PROCSTAT_H */

