

## C language: PThread tutorial
URL: https://hpc-tutorials.llnl.gov/posix/

Demo `exercise1`:
- `hello.c`
- `hello_arg1.c`
- `hello_arg2.c`
- `hello32.c`: needs to be compiled with flag `-lm` (to link the library `libm`, which implements the `sin`, `tan` functions)
  ```
  gcc hello32.c -o hello32 -lm
  ```
- `join.c`

## Java threading
- Implement the Java examples in the lecture slide
- See folder `./java`

### References
- Thread pool tutorial: https://docs.oracle.com/javase/tutorial/essential/concurrency/pools.html
- Thread pool API: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ThreadPoolExecutor.html
