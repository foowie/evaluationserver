#ifndef DEBUG_H
#define	DEBUG_H

#ifdef	__cplusplus
extern "C" {
#endif


    #ifdef DEBUG_REGISTRY
        #define DUMP_REGISTRY(regs) log_message("System call", "SYS CALL ORIG_EAX=%ld  EAX=%lx EBX=%lx ECX=%lx EDX=%lx\n", regs->orig_eax, regs->eax, regs->ebx, regs->ecx, regs->edx);
    #endif
    #ifndef DEBUG_REGISTRY
        #define DUMP_REGISTRY(regs) ;
    #endif

    #ifdef DEBUG_ERRORS
        #define DUMP_ERROR(...) log_message("Error", __VA_ARGS__);
    #endif
    #ifndef DEBUG_REGISTRY
        #define DUMP_ERROR(...) ;
    #endif

    /**
     * Initialize log
     * @param file_name Name of log file
     * @param prefix Prefix for log messages
     */
    void init_debug(const char * file_name, const char * prefix);
    
    /**
     * Log message into file, printf format
     */
    void log_message(const char * name, const char * format, ...);
    
    /**
     * Finalize debug
     */
    void close_debug(); 


#ifdef	__cplusplus
}
#endif

#endif	/* DEBUG_H */

