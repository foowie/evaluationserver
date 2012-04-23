#ifndef MAIN_H
#define	MAIN_H

#ifdef	__cplusplus
extern "C" {
#endif


	// Log registry on program fail
#define DEBUG_REGISTRY

	// Log program errors
#define DEBUG_ERRORS

#if __WORDSIZE == 64
#define ORIG_EAX_ADDR 8 * ORIG_RAX
#else
#define ORIG_EAX_ADDR 4 * ORIG_EAX
#endif    
    

#ifdef	__cplusplus
}
#endif

#endif	/* MAIN_H */

