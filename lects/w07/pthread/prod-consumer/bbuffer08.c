#include <pthread.h>
#include <stdio.h>

// version2.0: Figure 30.8: Producer/Consumer: Single CV And If Statement

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
pthread_cond_t cond = PTHREAD_COND_INITIALIZER;

void *producer(void *arg)
{
  int i;
  for (i = 0; i < loops; i++)
  {
    pthread_mutex_lock(&mutex);         // p1
    if (count == 1)                     // p2
      pthread_cond_wait(&cond, &mutex); // p3
    put(i);                             // p4
    pthread_cond_signal(&cond);         // p5
    pthread_mutex_unlock(&mutex);       // p6
  }
}

void *consumer(void *arg)
{
  int i;
  for (i = 0; i < loops; i++)
  {
    pthread_mutex_lock(&mutex);         // c1
    if (count == 0)                     // c2
      pthread_cond_wait(&cond, &mutex); // c3
    int tmp = get();                    // c4
    pthread_cond_signal(&cond);         // c5
    pthread_mutex_unlock(&mutex);       // c6
    printf("%d\n", tmp);
  }
}
