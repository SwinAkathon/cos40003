PThread Exercise 1:
---------------------

| File Name              | Description                                                                                                                                                                               |
|------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `arrayloops.c`, `arrayloops.f`  | Data decomposition by loop distribution. Fortran example only works under IBM AIX: see comments in source code for compilation instructions.                                      |
| `condvar.c`            | Condition variable example file. Similar to what was shown in the tutorial.                                                                                                               |
| `detached.c`           | Demonstrates how to explicitly create pthreads in a detached state.                                                                                                                       |
| `dotprod_mutex.c`, `dotprod_serial.c`      | Mutex variable example using a dot product program. Both a serial and pthreads version of the code are available.                                                                          |
| `hello.c`              | Simple "Hello World" example.                                                                                                                                                             |
| `hello32.c`            | "Hello World" pthreads program demonstrating thread scheduling behavior.                                                                                                                  |
| `hello_arg1.c`         | One correct way of passing the `pthread_create()` argument.                                                                                                                               |
| `hello_arg2.c`         | Another correct method of passing the `pthread_create()` argument, this time using a structure to pass multiple arguments.                                                                |
| `join.c`               | Demonstrates how to explicitly create pthreads in a joinable state for portability purposes. Also shows how to use the `pthread_exit` status parameter.                                    |
| `mpithreads_serial.c`, `mpithreads_threads.c`, `mpithreads_mpi.c`, `mpithreads_both.c`, `mpithreads.makefile` | A "series" of programs which demonstrate the progression for a serial dot product code to a hybrid MPI/pthreads implementation. Files include the serial version, pthreads version, MPI version, hybrid version, and a makefile. |
