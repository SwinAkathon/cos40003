We'll assume we have a shared data structure, such as a `List<Integer>`, that multiple threads may want to read from and write to. We'll use a `ReadWriteLock` to allow multiple readers but ensure only one writer at a time.

- `ReadWriteLock` allows multiple threads to read at the same time as long as no thread is writing. It provides better performance in scenarios with many reads and fewer writes.
- `ReentrantLock` provides exclusive access to the critical section, allowing only one thread (reader or writer) to access the resource at a time. It is simpler but can be less efficient for read-heavy workloads.

`ReadWriteLock` ensures that multiple readers can access the shared data simultaneously, but when a writer wants to modify the data, all readers are blocked until the writer finishes its work. This makes it suitable for scenarios where there are many reads and fewer writes.

### Description

1. **`ReadWriteLock`:**
   - We used `ReentrantReadWriteLock`, which provides two locks:
     - **Read lock**: Allows multiple threads to read concurrently as long as no thread is writing.
     - **Write lock**: Ensures that only one thread can write at a time, and no readers can access the data during the write.
   
2. **`readLock` and `writeLock`:**
   - We defined `readLock` and `writeLock` using the `ReadWriteLock`. The `readLock` is acquired when threads want to read, and the `writeLock` is acquired when a thread wants to write to the shared data.

3. **Writer (`Writer` class):**
   - The `Writer` thread periodically writes values to the shared data structure, acquiring the `writeLock` to ensure exclusive access during the write operation.

4. **Reader (`Reader` class):**
   - The `Reader` threads read the shared data concurrently, acquiring the `readLock`. Multiple readers can hold the `readLock` simultaneously.

### Differences Between `ReadWriteLock` and `ReentrantLock`:

1. **Multiple Readers with `ReadWriteLock`:**
   - **`ReadWriteLock`** allows multiple threads to hold the **read lock** simultaneously. As long as no thread is writing, multiple readers can read concurrently. This improves performance when you have multiple readers but only occasional writers, as it minimizes contention on shared resources.
   - **`ReentrantLock`**, on the other hand, is a standard exclusive lock where only one thread can hold the lock at a time, whether for reading or writing. This means that even when multiple threads only want to read, they must acquire the lock one at a time, which can lead to unnecessary blocking.

2. **Writer Exclusivity with `ReadWriteLock`:**
   - **`ReadWriteLock`** ensures that when a thread holds the **write lock**, no other thread can read or write. This guarantees exclusive access to shared resources when a write operation is being performed.
   - **`ReentrantLock`** also guarantees exclusive access, but it does not distinguish between reading and writing. A thread holding the lock prevents both readers and writers from accessing the shared resource.

3. **Use Case Flexibility:**
   - **`ReadWriteLock`** is ideal in scenarios where there are many reads and fewer writes. It allows for better scalability because multiple threads can read simultaneously. However, it introduces more complexity compared to a simple `ReentrantLock`.
   - **`ReentrantLock`** is simpler and useful when you need exclusive access for both reads and writes, without needing to distinguish between the two operations. It is appropriate for cases where both read and write operations are frequent or need to be strictly serialized.


