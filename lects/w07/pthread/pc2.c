#include <pthread.h>
#include <stdio.h>
// #include <unistd.h>

int done = 0;
pthread_mutex_t m = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t c = PTHREAD_COND_INITIALIZER;

void thr_exit()
{
  
  pthread_mutex_lock(&m);   // child: acquires the lock
  done = 1;                 // set done = true
  pthread_cond_signal(&c);  // signal parent via c (therefore waking it up)
  pthread_mutex_unlock(&m); // child: releases lock
}

void *child(void *arg)
{
  printf("child\n");  // do something
  thr_exit();         // finish off
  return NULL;
}

void thr_join()
{
  pthread_mutex_lock(&m);       // parent: acquires the lock
  while (done == 0)             // is it done?
    pthread_cond_wait(&c, &m);  // wait (in the queue)
  pthread_mutex_unlock(&m);     // parent: releases the lock
}

int main(int argc, char *argv[])
{
  printf("parent: begin\n");
  pthread_t p;  
  pthread_create(&p, NULL, child, NULL);    // create child process of p
  thr_join();                               // wait for child to finish
  printf("parent: end\n");              
  return 0;
}
