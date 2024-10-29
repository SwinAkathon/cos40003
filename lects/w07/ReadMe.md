
# Bounded buffer

To demonstrate, use these shell commands on the `java` subfolder and compare the result. The second command counts the number of lines which should be the same on the last line number shown outputed by first command.

```shell
grep int *.java | nl 
grep int *.java | wc -l
```

# Java: Producer/Consumer program

A program that demonstrates the producer/consumer pattern with a bounded buffer. The buffer is bounded by a max value. 

In Java, you can use `ReentrantLock` and `Condition` to implement a producer-consumer design where one or more producer threads add items to a shared buffer, and one or more consumer threads remove items from it. The `ReentrantLock` allows explicit locking and unlocking, while the `Condition` object can be used for thread signaling (i.e., `await()` and `signal()` or `signalAll()`).

## Explanation

1. **SharedBuffer Class**:
   - This is the shared buffer where producers add items, and consumers remove items.
   - `ReentrantLock lock`: The `lock` object ensures that the critical section (where items are added or removed from the buffer) is protected.
   - `Condition notFull` and `Condition notEmpty`: These condition variables help signal when the buffer is full or empty, allowing producers to wait when the buffer is full and consumers to wait when the buffer is empty.
   
2. **Producer Class**:
   - The producer continuously produces integers and tries to add them to the shared buffer.
   - If the buffer is full, the producer waits (`await()`) until the buffer has space.

3. **Consumer Class**:
   - The consumer continuously consumes integers from the shared buffer.
   - If the buffer is empty, the consumer waits (`await()`) until the buffer has items to consume.

4. **Main Program**:
   - Two producer threads and two consumer threads are created and started. They share the same buffer with a capacity of 5.
   - The producers produce items, and the consumers consume items concurrently.

## Key Points:

- **ReentrantLock**: Provides explicit locking and unlocking for critical sections.
- **Condition Variables**: Used to coordinate between producer and consumer threads when the buffer is full or empty.
  - `await()`: Causes the current thread to wait until it is signaled.
  - `signal()` / `signalAll()`: Wakes up waiting threads when the condition is met (e.g., buffer has space or items).
- **Buffer Synchronization**: The `produce()` method ensures that producers wait when the buffer is full, and the `consume()` method ensures that consumers wait when the buffer is empty.

## Output Example:

```
Produced: 0
Produced: 1
Produced: 2
Produced: 3
Produced: 4
Buffer is full. Producer waiting...
Consumed: 0
Consumed: 1
Produced: 5
Produced: 6
Buffer is full. Producer waiting...
Consumed: 2
```