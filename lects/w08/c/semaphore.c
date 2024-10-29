#include <semaphore.h>
sem_t s;

/* declare a semaphore s and initialize it to the value 1 by passing 1 in as the third argument. 
The second argument to sem init() will be set to 0, indicating that the semaphore is shared between threads in the same process. 
See the man page for details on other usages of semaphores (namely, how they can be used to synchronize access across different processes), which require a different value for that second argument.
 */
sem_init(&s, 0, 1);

int sem_wait(sem_t *s)
{
  // decrement the value of semaphore s by 1
  //    wait if value of semaphore s is negative
}

int sem_post(sem_t *s)
{
  // increment the value of semaphore s by 1 
  // if there are one or more threads waiting, wake one
}
