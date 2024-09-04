

# References
- [CPU vs. GPU demonstration](https://youtu.be/-P28LKWTzrI?si=Mk-wC8ki-AxObPMa)
- [CPU vs. GPU: explained](https://youtu.be/Axd50ew4pco?si=eouSS5zSp4VDzB3l)

# Demo 3 Sum programs:

1. `SequentialSum`
1. `ConcurrentSum`
1. `ParallelSum`

## ParallelSum
The `ParallelSum` program is considered a true parallel program because it leverages parallel computing techniques to divide the task into smaller subtasks that can be executed simultaneously across multiple CPU cores. Here's a breakdown of why `ParallelSum` qualifies as a true parallel program:

### Key Characteristics of `ParallelSum` as a True Parallel Program

1. **Use of `ForkJoinPool` for Parallel Execution:**
   - The program uses Java's `ForkJoinPool`, a framework designed explicitly for parallel programming. The `ForkJoinPool` is part of the `java.util.concurrent` package and provides a mechanism to distribute tasks across multiple threads efficiently.
   - `ForkJoinPool` uses a work-stealing algorithm to balance the workload among threads. If a thread completes its tasks, it can "steal" tasks from other threads that still have work to do. This dynamic load balancing helps to utilize CPU cores effectively.

2. **Recursive Task Splitting with `RecursiveTask`:**
   - The `SumTask` class extends `RecursiveTask<Long>`, which represents a task that can compute a result and can be subdivided into smaller subtasks.
   - The `compute` method is the core of the parallel execution. It divides the problem (summing the array) into smaller subproblems whenever the current range of the array (`start` to `end`) is larger than a predefined threshold (`THRESHOLD`).

3. **Concurrent Execution of Subtasks:**
   - The `compute` method splits the array into two halves and creates two new `SumTask` instances (`leftTask` and `rightTask`) to handle each half. 
   - The `fork()` method is called on `leftTask`, which schedules it to run asynchronously in a separate thread. Meanwhile, the `rightTask` is computed directly in the current thread. This allows both halves of the array to be processed concurrently.

4. **Exploitation of Multiple Cores:**
   - The `ForkJoinPool` is designed to exploit multiple CPU cores. When `leftTask.fork()` is called, the `ForkJoinPool` assigns the task to one of its worker threads, which runs on a separate core if available. 
   - Since the tasks run in parallel (rather than merely concurrently), the program can utilize all available cores. For example, on an 8-core CPU, up to 8 tasks can run simultaneously if the workload is sufficient and the `ForkJoinPool` has enough threads.

5. **Reduction of Synchronization Overhead:**
   - Unlike traditional multithreaded programs where threads often need to synchronize access to shared data (which can cause contention and reduce parallel efficiency), the `ForkJoinPool` model minimizes synchronization overhead. Each task works on its own segment of data, reducing the need for locking mechanisms and allowing threads to run more freely in parallel.
   
6. **Divide and Conquer Approach:**
   - The program employs a "divide and conquer" strategy, breaking down a large problem (summing the entire array) into smaller, independent subproblems (summing portions of the array). These smaller problems can be solved in parallel and then combined to form the final result.

7. **Scalability:**
   - The `ForkJoinPool` framework is highly scalable. It dynamically manages the creation and scheduling of tasks to make full use of the available processing power. This scalability makes `ParallelSum` more efficient on systems with multiple cores, providing true parallelism where tasks are performed simultaneously across different cores.

### True Parallelism vs. Concurrency

- **True Parallelism:** 
  - In true parallelism, tasks run simultaneously on different processors or cores. `ParallelSum` achieves this by using the `ForkJoinPool` to schedule different tasks on different cores simultaneously, depending on the number of available cores and the size of the tasks.
  - For example, if the array size is large enough and there are 8 cores available, `ParallelSum` will create multiple tasks that can be executed concurrently on those 8 cores, resulting in true parallel execution.

- **Concurrency (vs. Parallelism):** 
  - Concurrency is the concept of dealing with multiple tasks at the same time, but not necessarily running them simultaneously. For example, in `ConcurrentSum`, multiple threads are created, and they can make progress by interleaving their execution, but they may not run at the exact same time on different cores.
  - `ParallelSum`, on the other hand, is explicitly designed to run tasks in parallel, using multiple cores concurrently, providing true parallel execution when the system and task size allow it.

### Why `ParallelSum` is Efficient for Parallel Execution

- **Optimized Task Scheduling:** The `ForkJoinPool` is optimized for parallel execution. It dynamically balances the workload and efficiently uses all available cores.
- **Reduced Context Switching:** Unlike traditional multithreading models, the `ForkJoinPool` minimizes context switching by reducing the number of threads needed and employing a work-stealing algorithm to efficiently use idle threads.
- **Minimal Locking:** The recursive task model minimizes synchronization requirements, as each task works independently on its data segment.

### Conclusion

The `ParallelSum` program is a true parallel program because it uses the `ForkJoinPool` framework to divide the work into smaller tasks that can be executed simultaneously on different cores. This enables the program to take full advantage of multi-core CPUs, providing genuine parallel execution and performance gains compared to a concurrent but not truly parallel approach like the one used in `ConcurrentSum`.