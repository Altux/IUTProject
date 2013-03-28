/* 
 * File:   mutex.h
 * Author: godeau
 *
 * Created on March 1, 2013, 7:47 PM
 */

#ifndef MUTEX_H
#define	MUTEX_H

#ifdef	__cplusplus
extern "C" {
#endif

    typedef struct mutex{
        int read;
        int write;
    }mutex_t;

    int init_mutex(mutex_t *mutex);
    
    int lock_mutex(mutex_t *mutex);
    
    int unlock_mutex(mutex_t *mutex);
    
    void close_mutex(mutex_t *mutex);


#ifdef	__cplusplus
}
#endif

#endif	/* MUTEX_H */

