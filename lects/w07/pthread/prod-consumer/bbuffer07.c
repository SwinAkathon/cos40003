#include <pthread.h>
#include <stdio.h>

//  simple version (without locking): Figure 30.7: Producer/Consumer Threads (v1)

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

void *producer(void *arg)
{
  int i;
  int loops = (int)arg;
  for (i = 0; i < loops; i++)
  {
    put(i);
  }
}

void *consumer(void *arg)
{
  while (1)
  {
    int tmp = get();
    printf("%d\n", tmp);
  }
}
