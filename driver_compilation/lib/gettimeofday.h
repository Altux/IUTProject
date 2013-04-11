#ifndef __GETTIMEOFDAY__
#define __GETTIMEOFDAY__
#include <sys/time.h>

int gettimeofday (struct timeval *tv, void *tz);
int diff_time (struct timeval *debut, int precision);

#endif
