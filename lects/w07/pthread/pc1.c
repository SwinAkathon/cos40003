#include <pthread.h>
#include <stdio.h>
#include <unistd.h>

volatile int done = 0;

void *child(void *arg)
{
  printf("child...\n");
  sleep(3);
  done = 1;
  return NULL;
}

int main(int argc, char *argv[])
{
  printf("parent: begin\n");
  pthread_t c;
  pthread_create(&c, NULL, child, NULL); // child
  while (done == 0)
    ; // spin ==> inefficient
  printf("parent: end\n");
  return 0;
}