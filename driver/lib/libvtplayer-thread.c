#include <time.h>
#ifdef __linux
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>
#include <stdio.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/types.h>
#include <sys/time.h>
#include "mutex.h"
#define CLEF 12345
#elif _WIN32
#include <process.h>
#include <windows.h>
#include "gettimeofday.h"
#endif
#include "libvtplayer-thread.h"

typedef struct Config {
    BYTE bytes[VTP_DATA_LENGTH * 2];
    int ecart;
} CONFIG;

// GLOBAL SECTION
#ifdef __linux
pid_t pid;
mutex_t mutex;
int mem_id;
CONFIG *ptr_mem_share = NULL;
#elif _WIN32
CONFIG DATA;
CONFIG *ptr_mem_share = &DATA;
CRITICAL_SECTION DATA_ACCESS;
uintptr_t pid = 0;
#else
#error unknown operating systems
#endif
// END GLOBAL SECTION

/**
 * Copie la configuration de bytes.
 * 
 * @param target tableau ou copier les bytes.
 * @param source tableau ou sont les bytes a copier.
 */
void inline copy_bytes(BYTE *target, BYTE *source) {
    int cpt;
    for (cpt = 0; cpt < VTP_DATA_LENGTH * 2; cpt++) {
        target[cpt] = source[cpt];
    }
}

/**
 * 
 */
void set_pads_config_thread(void *_ignore_) {
    short cpt = 0;
    struct timeval start = {0};
    gettimeofday(&start, NULL);
    BYTE old_bytes[VTP_DATA_LENGTH * 2] = {NULL_BYTE};

    while (1) {
#ifdef DEBUG
        printf("[%d thread] try to lock the mutex\n", getpid());
#endif
#ifdef __linux
        lock_mutex(&mutex);
#elif _WIN32
        EnterCriticalSection(&DATA_ACCESS);
#endif
#ifdef DEBUG
        printf("[%d thread] have lock the mutex\n", getpid());
#endif
        if (ptr_mem_share->ecart != SET_CONFIG) {
#ifdef DEBUG
            printf("[%d thread] the actual configuration is : %x, %x, %x, %x, cpt = %d\n", getpid(),
                    *(&ptr_mem_share->bytes[0] + cpt),
                    *(&ptr_mem_share->bytes[1] + cpt),
                    *(&ptr_mem_share->bytes[2] + cpt),
                    *(&ptr_mem_share->bytes[3] + cpt),
                    cpt);
#endif
            if (!equal_byte(old_bytes, ptr_mem_share->bytes)) {
#ifdef DEBUG
                printf("[%d thread] the bytes configuration have change\n", getpid());
#endif
                cpt = 0; // réinitialisation du compteur
                copy_bytes(old_bytes, ptr_mem_share->bytes);
                vtplayer_buffer_set(ptr_mem_share->bytes); // mise en place de la nouvelle configuration
                gettimeofday(&start, NULL); // redémarrage du timer
            } else {
                //clock_t now = clock();
                struct timeval now = {0};
                gettimeofday(&now, NULL); // on récupère le temps actuel

#ifdef DEBUG
                printf("[%d thread] now(%d) - start(%d) = %d \tecart = %d\n",
                        getpid(),
                        (int) (now.tv_sec * 1000 + now.tv_usec / 1000),
                        (int) (start.tv_sec * 1000 + start.tv_usec / 1000),
                        (int) ((now.tv_sec * 1000 + now.tv_usec / 1000) - (start.tv_sec * 1000 + start.tv_usec / 1000)),
                        ptr_mem_share->ecart);
#endif
                // si la différence et supérieur ou egale a l'écart souhaité
                if ((now.tv_sec * 1000 + now.tv_usec / 1000) - (start.tv_sec * 1000 + start.tv_usec / 1000) >= ptr_mem_share->ecart) {
#ifdef DEBUG
                    printf("[%d thread] we change the configuration\n", getpid());
#endif
                    vtplayer_buffer_set(ptr_mem_share->bytes + cpt); // on met l'autre configuration de pads

                    start = now; // réinitialisation du timer

                    cpt = ((cpt == 0) ? VTP_DATA_LENGTH : 0); // changement de l'état du compteur
                }
            }
        }
#ifdef __linux
        unlock_mutex(&mutex);
#elif _WIN32
        LeaveCriticalSection(&DATA_ACCESS);
#endif
#ifdef DEBUG
        printf("[%d thread] unlock mutex\n\n", getpid());
#endif
    }
}

int init_thread() {
#ifdef __linux
#ifdef DEBUG
    printf("[%d thread] init the mutex\n", getpid());
#endif
    init_mutex(&mutex);
#ifdef DEBUG
    printf("[%d thread] start the second processus\n", getpid());
#endif
    pid = fork();
    if (pid == 0) {
#ifdef DEBUG
        printf("[%d thread] startting\n", getpid());
#endif
        // appel de la fonction du fils avec boucle infinie
        set_pads_config_thread(NULL);
        close_mutex(&mutex);
        exit(EXIT_SUCCESS);
    }
    return (pid > 0) ? 0 : -1;
#elif _WIN32
    // initialisation de la zone critique
    InitializeCriticalSection(&DATA_ACCESS);

    // démarrage du thread
    pid = _beginthread(set_pads_config_thread, 0, NULL);
    return (pid > 0) ? 0 : -1;
#endif
}

#ifdef __linux

int init_data() {
#ifdef DEBUG
    printf("create a memory share\n");
#endif
    mem_id = shmget(CLEF, sizeof (CONFIG), 0666 | IPC_CREAT);
#ifdef DEBUG
    printf("attach the share memory\n");
#endif
    ptr_mem_share = (CONFIG *) shmat(mem_id, NULL, 0);
    if (ptr_mem_share) {
        int cpt;
        for (cpt = 0; cpt < VTP_DATA_LENGTH * 2; cpt++) {
            ptr_mem_share->bytes[cpt] = NULL_BYTE;
        }

        ptr_mem_share->ecart = SET_CONFIG;
    }
    return (ptr_mem_share > 0) ? 0 : -1;
}
#elif _WIN32

void init_data() {
    int cpt;
    for (cpt = 0; cpt < VTP_DATA_LENGTH * 2; cpt++) {
        ptr_mem_share->bytes[cpt] = NULL_BYTE;
    }

    ptr_mem_share->ecart = SET_CONFIG;
}
#endif

/**
 * 
 * @param old
 * @param new
 * @return 
 */
int inline equal_byte(BYTE *old, BYTE *new) {
    int equal = 1, cpt = 0;
    while (equal && cpt < VTP_DATA_LENGTH * 2) {
        equal = old[cpt] == new[cpt];
        cpt++;
    }
    return equal;
}

void vtplayer_set_thread(BYTE b1, BYTE b2, BYTE b3, BYTE b4, BYTE b5, BYTE b6, BYTE b7, BYTE b8, int ecart) {
#ifdef DEBUG
    printf("[%d thread] try to lock mutex\n", getpid());
#endif
#ifdef __linux
    lock_mutex(&mutex);
#elif _WIN32
    EnterCriticalSection(&DATA_ACCESS);
#endif
#ifdef DEBUG
    printf("[%d thread] have lock the thread\n", getpid());
#endif

    if (ecart != NOT_SET_CONFIG) {
#ifdef DEBUG
        printf("[%d thread] set the configuration\n", getpid());
#endif
        ptr_mem_share->bytes[0] = b1;
        ptr_mem_share->bytes[1] = b2;
        ptr_mem_share->bytes[2] = b3;
        ptr_mem_share->bytes[3] = b4;
        ptr_mem_share->bytes[4] = b5;
        ptr_mem_share->bytes[5] = b6;
        ptr_mem_share->bytes[6] = b7;
        ptr_mem_share->bytes[7] = b8;

        ptr_mem_share->ecart = ecart;
    } else {
        ptr_mem_share->ecart = SET_CONFIG;
        vtplayer_set(b1, b2, b3, b4);
    }
#ifdef __linux
    unlock_mutex(&mutex);
#elif _WIN32
    LeaveCriticalSection(&DATA_ACCESS);
#endif
#ifdef DEBUG
    printf("[%d thread] unlock mutex\n\n", getpid());
#endif
}

void stop_thread() {
#ifdef DEBUG
    printf("stopping the thread\n");
#endif
#ifdef __linux
    lock_mutex(&mutex);
    kill(pid, SIGKILL);
    wait(NULL);
    unlock_mutex(&mutex);

    shmctl(mem_id, IPC_RMID, NULL);
    close_mutex(&mutex);
#elif _WIN32
    EnterCriticalSection(&DATA_ACCESS);
    _endthreadex(pid);
    pid = 0;
    LeaveCriticalSection(&DATA_ACCESS);
#endif
#ifdef DEBUG
    printf("the second thread is stop.\n");
#endif
}
