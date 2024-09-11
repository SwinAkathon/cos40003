#include <stdio.h>  // required for printf
#include <unistd.h> // required for getpid

void printinf(const int level, const int f) {
  int pid = getpid();
  if (f == 0) {
    // child 
    int ppid = getppid();
    printf("f%d. cid: %d (parent: %d)\n", level, pid, ppid); 
  } 
  else if (f > 0) {
    // parent
    printf("f%d. pid: %d\n", level, pid);
  }
}

// How many lines of output this program will produce:
void main()
{
  printf("Init. my pid is %d\n", getpid());
  int f = fork();
  printinf(1,f);
  f = fork();
  printinf(2,f);
  f = fork();
  printinf(3,f);
}

