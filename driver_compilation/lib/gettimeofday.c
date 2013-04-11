#include <windows.h>
#include "gettimeofday.h"

#define FACTOR (0x19db1ded53e8000LL)
#define NSPERSEC 10000000LL

typedef long long LONGLONG;

static int inited = 0;
static LARGE_INTEGER primed_pc, primed_ft;
static double freq;
static int justdelta = 0;

static void prime() {
    LARGE_INTEGER ifreq;
    if (!QueryPerformanceFrequency(&ifreq)) {
        inited = -1;
        return;
    }

    FILETIME f;
    int priority = GetThreadPriority(GetCurrentThread());
    SetThreadPriority(GetCurrentThread(), THREAD_PRIORITY_TIME_CRITICAL);
    if (!QueryPerformanceCounter(&primed_pc)) {
        SetThreadPriority(GetCurrentThread(), priority);
        inited = -1;
        return;
    }

    GetSystemTimeAsFileTime(&f);
    SetThreadPriority(GetCurrentThread(), priority);

    inited = 1;
    primed_ft.HighPart = f.dwHighDateTime;
    primed_ft.LowPart = f.dwLowDateTime;
    primed_ft.QuadPart -= FACTOR;
    primed_ft.QuadPart /= 10;
    freq = (double) ((double) 1000000. / (double) ifreq.QuadPart);
    return;
}

static LONGLONG usec() {
    if (!inited)
        prime();

    LARGE_INTEGER now;
    if (!QueryPerformanceCounter(&now)) {
        return -1;
    }

    // FIXME: Use round() here?
    now.QuadPart = (LONGLONG) (freq * (double) (now.QuadPart - primed_pc.QuadPart));
    LONGLONG res = justdelta ? now.QuadPart : primed_ft.QuadPart + now.QuadPart;
    return res;
}

int gettimeofday(struct timeval *tv, void *tz) {
    LONGLONG now = usec();
    if (now == (LONGLONG) - 1)
        return -1;

    tv->tv_sec = now / 1000000;
    tv->tv_usec = now % 1000000;

    return 0;
}

//retourne des centièmes de secondes
int diff_time(struct timeval *debut, int precision) {
    struct timeval *fin = (struct timeval *) malloc(sizeof (struct timeval));
    gettimeofday(fin, NULL);

    int sec = fin->tv_sec - debut->tv_sec;
    int msec = fin->tv_usec - debut->tv_usec;

    free(fin);

    return (1000000 * sec + msec) / precision;
}

