#include <unistd.h>
#include "mutex.h"

int init_mutex(mutex_t* mutex) {
    int fd[2];
    int ret = pipe(fd);
    mutex->read = fd[0];
    mutex->write = fd[1];
    unlock_mutex(mutex);
    return ret;
}

int lock_mutex(mutex_t* mutex) {
    char val;
    return read(mutex->read, &val, sizeof (char));
}

int unlock_mutex(mutex_t* mutex) {
    char val = '1';
    return write(mutex->write, &val, sizeof (char));
}

void close_mutex(mutex_t* mutex) {
    close(mutex->read);
    close(mutex->write);
}

