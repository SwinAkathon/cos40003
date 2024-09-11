#include <stdio.h>  // required for printf
#include <unistd.h> // required for getpid

// How many lines of output this program will produce:
void main()
{
  printf("Init. my pid is %d\n", getpid());
  fork();
  fork();
  fork();
  printf("my pid is %d\n", getpid());

}

