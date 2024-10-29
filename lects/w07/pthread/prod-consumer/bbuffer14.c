#include <pthread.h>
#include <stdio.h>

// version5.0:
//    - Figure 30.13: The Correct Put And Get Routines
//    - Figure 30.14: The Correct Producer/Consumer Synchronization
//// implement the BUFFER

const int MAX = 100;
// CHANGE: store multiple values
int buffer[MAX];
int fill_ptr = 0;
int use_ptr = 0;
int count = 0;

void put(int value)
{
  buffer[fill_ptr] = value;
  fill_ptr = (fill_ptr + 1) % MAX;
  count++;
}

int get()
{
  int tmp = buffer[use_ptr];
  use_ptr = (use_ptr + 1) % MAX;
  count--;
  return tmp;
}

// END BUFFER

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
    pthread_mutex_lock(&mutex);          // p1
    while (count == MAX)                 // p2
      pthread_cond_wait(&empty, &mutex); // p3
    put(i);                              // p4
    pthread_cond_signal(&fill);          // p5
    pthread_mutex_unlock(&mutex);        // p6
  }
}

void *consumer(void *arg)
{
  int i;
  for (i = 0; i < loops; i++)
  {
    pthread_mutex_lock(&mutex);         // c1
    while (count == 0)                  // c2
      pthread_cond_wait(&fill, &mutex); // c3
    int tmp = get();                    // c4
    pthread_cond_signal(&empty);        // c5
    pthread_mutex_unlock(&mutex);       // c6
    printf("%d\n", tmp);
  }
}
