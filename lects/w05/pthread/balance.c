#include <pthread.h>
#include <stdio.h>

int main()
{
  int balance = 1000;
  pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;

  pthread_mutex_lock(&lock);
  // critical section
  balance = balance + 1;
  pthread_mutex_unlock(&lock);

  printf("balance: %d\n",balance);
}