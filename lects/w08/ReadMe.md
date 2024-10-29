### 1. What is a Semaphore?

- **Semaphore**: Used for controlling access to a shared resource by multiple threads. Can allow multiple threads at once depending on the number of permits.
- **Mutex**: A special case of semaphore that enforces strict mutual exclusion, allowing only one thread to enter the critical section at a time.

A **semaphore** is a synchronization primitive that controls access to a shared resource by multiple threads in a concurrent system. It maintains a counter (called a "permit" or "available resources") that represents the number of threads that can access the resource simultaneously.

There are two types of semaphores:
- **Counting Semaphore**: This semaphore allows a fixed number of threads to access the resource. The counter is initialized to the maximum number of threads that can access the resource simultaneously.
- **Binary Semaphore (Mutex)**: This is a special case of the counting semaphore with the counter initialized to 1. It functions similarly to a mutex lock (i.e., only one thread can access the resource at a time).

### 2. Why Use a Semaphore?

Semaphores are used in multithreading environments for:
- **Limiting access**: You might want to restrict how many threads can access a resource, such as a connection pool, to prevent overuse or resource exhaustion.
- **Synchronization**: You can use semaphores to coordinate between threads, signaling when a resource becomes available and allowing other threads to proceed.

Key benefits of using a semaphore:
- **Control resource access**: Semaphores can control access to a finite number of resources (e.g., 3 threads allowed to access a resource at the same time).
- **Fairness**: Depending on the semaphore implementation, you can enforce fairness by making sure threads are allowed to acquire resources in the order they requested access (FIFO).

### 3. Compare and Contrast: Semaphore vs. Mutex (Mutual Exclusion)

#### **Semaphore**:
- **Multiple access**: Semaphores can allow more than one thread to access the resource at the same time (if it's a counting semaphore).
- **Signaling**: Semaphores can be used to signal between threads. One thread can release a permit, and another can acquire it, making semaphores good for communication between threads.
- **Non-ownership**: A thread that acquires a permit does not have to be the one that releases it. This makes semaphores flexible for coordination purposes.

#### **Mutex**:
- **Exclusive access**: A mutex (binary semaphore) is a specific type of lock that only allows one thread to access the resource at a time.
- **Ownership**: A mutex is owned by the thread that locks it and can only be released by the same thread. This ensures proper locking/unlocking and prevents deadlocks.
- **Simplicity**: Mutexes are simpler because they only control exclusive access to a shared resource. They don't count permits or allow multiple threads access at once.

### Key Differences:
- **Concurrency**: A counting semaphore allows multiple threads to access a shared resource simultaneously, while a mutex only allows one thread at a time.
- **Usage**: Semaphores are useful for signaling and controlling multiple resources. Mutexes are used for mutual exclusion to protect critical sections of code.
- **Ownership**: Mutexes are owned by the thread that locks them, whereas semaphores do not have ownership rules.


### Code example
- `./semphore`: In the **semaphore example**, multiple threads (up to 3) can access the shared resource at the same time. Threads that exceed this limit must wait until one of the others releases a permit.
- `./mutex`: In the **mutex example**, only one thread can enter the critical section at a time, enforcing exclusive access (mutual exclusion).

