C Program to Demonstrate Process States
============================================
To demonstrate the process states and their transitions in a C program using threads, we can create a simple program where:
- threads simulate different states, such as Running, Blocked (Sleeping), and Final (Zombie)
- use `pthread` library to create and manage threads.


Here's a C program that creates multiple threads, some of which will run, some will sleep (block), and some will complete (terminate).


# Commands to view process states


## `ps`

```
$ ps -ef | grep fork2

ducmle   2165629 2165304  0 12:20 pts/4    00:00:00 fork2
ducmle   2165630 2165629  0 12:20 pts/4    00:00:00 fork2

```

```
$ ps -L -p $(pidof thread_states)

    PID     LWP TTY          TIME CMD
2053321 2053321 pts/4    00:00:00 thread_states3
2053321 2053322 pts/4    00:00:00 thread_states3
2053321 2053324 pts/4    00:00:00 thread_states3
```

## `htop`
- Configuration while running: 
  - Filter (F4): `thread_states`

```
$ htop
```

![`htop` output](images/htop.png)