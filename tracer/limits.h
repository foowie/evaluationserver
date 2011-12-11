#ifndef LIMITS_H
#define	LIMITS_H

#ifdef	__cplusplus
extern "C" {
#endif

    
    // System limits for executed program
    struct Limits {
        pid_t pid;
        
        rlim_t time_limit; // Running time in ms
        rlim_t memory_limit; // Memory limit in bytes
        rlim_t output_limit; // Output size limit in bytes
        
        rlim_t memory_peek; // Maximal memory usage
        
        struct timeval start_time; // Start time of program
        struct timeval stop_time; // Stop time of program
        double miliseconds; // Runnin time of program in ms
        
        int memory_error; // Is there memory exceeded
        int time_error; // Is there time exceeded
        int output_error; // Is there output exceeded
    };

    /**
     * Initialize limit scructure for given process
     */
    void init_limits(struct Limits *limit, pid_t pid);
    
    /**
     * Disable creating dump files of core
     */
    int disable_core_dump(struct Limits *limit);
    
    /**
     * Has limit errors
     */
    int has_limit_errors(struct Limits * limit);
    
    /**
     * Initializes checking for output limit
     */
    int initialize_output_limit(struct Limits *limit);

    /**
     * Initializes checking for memory limit
     */
    int initialize_memory_limit(struct Limits *limit);
    
    /**
     * Initializes checking for time limit
     */
    int initialize_time_limit(struct Limits *limit);
    
    /**
     * Check / update limit state
     */
    void check_limits(struct Limits *limit);
    
    /**
     * Stop timer
     */
    void stop_timer(struct Limits *limit);

#ifdef	__cplusplus
}
#endif

#endif	/* LIMITS_H */

