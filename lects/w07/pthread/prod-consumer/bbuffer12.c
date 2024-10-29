#include <pthread.h>
#include <stdio.h>

// version4.0:  Figure 30.12: Producer/Consumer: Two CVs And While

//// implement the BUFFER
int buffer;
int count = 0; // initially, empty

void put(int value)
{
  assert(count == 0);
  count = 1;
  buffer = value;
}

int get()
{
  assert(count == 1);
  count = 0;
  return buffer;
}

// end BUFFER

int loops; // must initialize somewhere...
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t    // TWO conditional variables
  empty = PTHREAD_COND_INITIALIZER, 
  fill = PTHREAD_COND_INITIALIZER;

void *producer(void *arg)
{
  int i;
  for (i = 0; i < loops; i++)
  {
    pthread_mutex_lock(&mutex);
    while (count == 1)
      pthread_cond_wait(&empty, &mutex);
    put(i);
    pthread_cond_signal(&fill);
    pthread_mutex_unlock(&mutex);
  }
}

void *consumer(void *arg)
{
  int i;
  for (i = 0; i < loops; i++)
  {
    pthread_mutex_lock(&mutex);
    while (count == 0)
      pthread_cond_wait(&fill, &mutex);
    int tmp = get();
    pthread_cond_signal(&empty);
    pthread_mutex_unlock(&mutex);
    printf("%d\n", tmp);
  }
}
