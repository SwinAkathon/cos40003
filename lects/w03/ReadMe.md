# Week 03
- Demo Java's threaded programs and view threads in action

## Command to view all JVM threads

```
ps -eLf | grep java
```

**Example output**: 

```
leminhduc   2008616 1994580 2008616  0   19 10:16 pts/2    00:00:00 java ThreadHelloWorld
leminhduc   2008616 1994580 2008617  2   19 10:16 pts/2    00:00:00 java ThreadHelloWorld
leminhduc   2008616 1994580 2008618  0   19 10:16 pts/2    00:00:00 java ThreadHelloWorld
leminhduc   2008616 1994580 2008619  0   19 10:16 pts/2    00:00:00 java ThreadHelloWorld
leminhduc   2008616 1994580 2008620  0   19 10:16 pts/2    00:00:00 java ThreadHelloWorld
leminhduc   2008616 1994580 2008621  0   19 10:16 pts/2    00:00:00 java ThreadHelloWorld
leminhduc   2008616 1994580 2008622  0   19 10:16 pts/2    00:00:00 java ThreadHelloWorld
leminhduc   2008616 1994580 2008623  0   19 10:16 pts/2    00:00:00 java ThreadHelloWorld
leminhduc   2008616 1994580 2008624  0   19 10:16 pts/2    00:00:00 java ThreadHelloWorld
leminhduc   2008616 1994580 2008625  0   19 10:16 pts/2    00:00:00 java ThreadHelloWorld
leminhduc   2008616 1994580 2008626  0   19 10:16 pts/2    00:00:00 java ThreadHelloWorld
leminhduc   2008616 1994580 2008627  0   19 10:16 pts/2    00:00:00 java ThreadHelloWorld
leminhduc   2008616 1994580 2008628  0   19 10:16 pts/2    00:00:00 java ThreadHelloWorld
leminhduc   2008616 1994580 2008629  0   19 10:16 pts/2    00:00:00 java ThreadHelloWorld
leminhduc   2008616 1994580 2008630  0   19 10:16 pts/2    00:00:00 java ThreadHelloWorld
leminhduc   2008616 1994580 2008631  0   19 10:16 pts/2    00:00:00 java ThreadHelloWorld
leminhduc   2008616 1994580 2008632  0   19 10:16 pts/2    00:00:00 java ThreadHelloWorld
leminhduc   2008616 1994580 2008633  0   19 10:16 pts/2    00:00:00 java ThreadHelloWorld
leminhduc   2008616 1994580 2008634  0   19 10:16 pts/2    00:00:00 java ThreadHelloWorld
```

### Understanding the `ps -eLf` Command Output

The `ps -eLf` command provides detailed information about all threads in the system, and `grep java` filters the output to show only those threads related to your Java program (`java ThreadHelloWorld`).

### Summary

- **Three Threads in Total:**
  - The output shows that there are three threads (`LWP` values `2008616`, `2008617`, `2008618`) running within the same Java process (`PID` `2008616`).
  - The first thread (`LWP` `2008616`) is the main thread, as its LWP is the same as the PID.
  - The other two threads (`LWP` `2008617` and `2008618`) are additional threads created by the JVM.

- **Thread Scheduling:**
  - The CPU usage (`C`) shows that the second thread (`LWP` `2008617`) has utilized some CPU time (`2`), while the others have not used any CPU time yet (`0`).
  - This output suggests that the JVM has created multiple threads to perform its internal operations (like garbage collection, JIT compilation, etc.), or these are user-created threads.

The `ps` output shows that the Java process (`java ThreadHelloWorld`) is running with multiple threads (with different `LWP` values) within the same process (`PID` `2008616`). This behavior is normal for a Java application, as the JVM creates multiple threads for various purposes, such as garbage collection, JIT compilation, or executing user-defined threads.

#### Column Breakdown

1. **USER (`leminhduc`):**
   - The name of the user who owns the process (in this case, `leminhduc`).

2. **PID (`2008616`):**
   - The **Process ID (PID)** of the Java process (`java ThreadHelloWorld`). All threads of the same Java process share the same PID (`2008616`), indicating that they belong to the same process.

3. **PPID (`1994580`):**
   - The **Parent Process ID (PPID)**, which is the ID of the process that started the Java process. Here, `1994580` is the parent process ID, which could be a shell or another program that launched the Java process.

4. **LWP (`2008616`, `2008617`, `2008618`):**
   - **Light Weight Process (LWP)** ID, also known as the thread ID (TID) on Linux. 
   - This column shows the unique thread IDs within the process. You can see that there are three entries with different LWP values (`2008616`, `2008617`, `2008618`), indicating that there are three threads running within the Java process.
   - The LWP of `2008616` is the same as the PID of the process, which represents the main thread of the Java program.

5. **C (`0`, `2`, `0`):**
   - **CPU usage (C):** This shows the processor utilization for the process (in terms of scheduling priority). 
   - `0`, `2`, `0` values indicate the percentage of CPU time used by each thread at the time of the snapshot. Here, the second thread has used some CPU time (2%), while the other two have not.

6. **PRI (`19`):**
   - **Priority (PRI):** The priority of the threads. All three threads have a priority of `19` by default, which is typical for user-space processes. Lower numbers mean higher priority, but Linux generally uses `19` for normal user threads.

7. **TIME (`10:16`):**
   - **TIME:** The time at which the process was started. All three entries show `10:16`, meaning they were all started at the same time when the Java process was initiated.

8. **TTY (`pts/2`):**
   - **TTY (Teletype Terminal):** The terminal associated with the process. `pts/2` indicates the pseudo-terminal where the Java process is running.

9. **TIME (`00:00:00`):**
   - **TIME (CPU Time):** The cumulative CPU time used by the thread so far. All three threads show `00:00:00`, which indicates they have used little to no CPU time up to this point.

10. **CMD (`java ThreadHelloWorld`):**
    - **Command (`CMD`):** The command that started the process. In this case, it is `java ThreadHelloWorld`, the Java program that you are running.

## `ThreadSchedulingDemo.java`

1. **Creating Threads:**
   - The program creates three threads (`Thread-1`, `Thread-2`, and `Thread-3`).
   - Each thread prints a sequence of numbers from 1 to 10.

2. **Thread Sleep:**
   - Each thread sleeps for 100 milliseconds (`Thread.sleep(100)`) between prints to simulate some work and give other threads an opportunity to run. This also helps to demonstrate context switching between threads.

3. **Thread Priorities:**
   - Each thread is given a different priority (`Thread.NORM_PRIORITY + threadNumber - 1`).
   - The priority is an integer value between `Thread.MIN_PRIORITY` (1) and `Thread.MAX_PRIORITY` (10). The JVM may use these priorities to suggest to the OS scheduler which thread should run next.
   - In practice, the OS may or may not strictly follow these priorities, but they can influence the scheduling.

4. **Thread Start:**
   - `thread.start()` is called to begin execution of each thread. This tells the JVM to create a native OS thread and hand it off to the OS scheduler.

## Scheduling algorithms
Implementing different thread scheduling algorithms such as **Multi-Level Feedback Queue (MLFQ)**, **Lottery Scheduling**, and **Stride Scheduling** directly in Java is challenging because these scheduling algorithms are typically implemented at the operating system level, not at the application level. However, we can simulate the behavior of these algorithms using Java threads to help demonstrate how they work.

### Overview of Scheduling Algorithms

1. **Multi-Level Feedback Queue (MLFQ):**
   - MLFQ uses multiple queues with different priority levels. Processes can move between queues based on their behavior (e.g., CPU-bound vs. I/O-bound).
   - The scheduler gives preference to processes in higher-priority queues.

2. **Lottery Scheduling:**
   - Processes are assigned lottery tickets, and the scheduler randomly selects a ticket to choose the next process. This gives each process a probability proportional to its number of tickets of being selected.

3. **Stride Scheduling:**
   - A deterministic version of lottery scheduling where each process has a stride (inversely proportional to the number of tickets it holds). The process with the lowest pass value is selected to run next.

### Implementation of Scheduling Algorithms in Java

To demonstrate these scheduling algorithms in Java, we'll create three separate programs named `MLFQDemo`, `LotterySchedulingDemo`, and `StrideSchedulingDemo`.

### Explanation of Each Program

1. **`MLFQDemo`:**
   - **Multi-Level Feedback Queue (MLFQ):** Simulates multiple priority levels by creating separate queues for each priority. Tasks are processed in order of priority.
   - Tasks are assigned to different queues (priority levels), and the scheduler processes higher-priority tasks before lower-priority tasks.

2. **`LotterySchedulingDemo`:**
   - **Lottery Scheduling:** Each task is given a certain number of lottery tickets. A random ticket is drawn to decide which task to run next.
   - Tasks with more tickets have a higher probability of being chosen, simulating fairness with a probabilistic approach.

3. **`StrideSchedulingDemo`:**
   - **Stride Scheduling:** A deterministic version of lottery scheduling. Each task has a stride inversely proportional to the number of tickets it holds.
   - Tasks with fewer tickets (higher stride) will run less frequently. The task with the lowest pass value (sum of strides) is selected next.