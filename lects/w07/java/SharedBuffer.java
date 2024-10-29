import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SharedBuffer {
    private final Queue<Integer> buffer = new LinkedList<>();
    private final int capacity;
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private boolean done = false;

    // bounded buffer
    private final int maxValue;
    private int currentValue = 0;  // Track the current value produced

    public SharedBuffer(int capacity, int maxValue) {
        this.capacity = capacity;
        this.maxValue = maxValue;
    }

    // Producer adds item to the buffer
    public void produce(int value) throws InterruptedException {
        lock.lock();
        try {
            while (buffer.size() == capacity) {
                System.out.println("Buffer is full. Producer waiting...");
                notFull.await(); // Wait for buffer space
            }

            if (currentValue <= maxValue) {
                buffer.add(currentValue);
                System.out.println("Produced: " + currentValue);
                currentValue++;  // Increment the value for the next production
                notEmpty.signal(); // Signal consumers that buffer is not empty
            } else {
                System.out.println("Producer reached maxValue: " + maxValue + ". Stopping production.");
                done = true;
            }

            /* non-bounded version:
            buffer.add(value);
            System.out.println("Produced: " + value);
            notEmpty.signal(); // Signal consumers that buffer is not empty 
            */
        } finally {
            lock.unlock();
        }
    }

    // Consumer removes item from the buffer
    public int consume() throws InterruptedException {
        lock.lock();
        try {
            while (buffer.isEmpty()) {
                System.out.println("Buffer is empty. Consumer waiting...");
                notEmpty.await(); // Wait for items to consume
            }
            int value = buffer.poll();
            System.out.println("Consumed: " + value);
            notFull.signal(); // Signal producers that buffer has space
            return value;
        } finally {
            lock.unlock();
        }
    }

    // Check if the producer has finished
    public boolean isProductionComplete() {
        return done;
    }
}