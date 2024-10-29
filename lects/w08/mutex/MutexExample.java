
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SharedResource {

    private Lock mutex = new ReentrantLock();  // ReentrantLock acts as a mutex

    public void accessCriticalSection(String threadName) {
        mutex.lock();  // Acquire the lock (only one thread can proceed at a time)
        try {
            System.out.println(threadName + " has entered the critical section.");

            // Simulate critical section work
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(threadName + " is leaving the critical section.");
            mutex.unlock();  // Release the lock
        }
    }
}

public class MutexExample {

    public static void main(String[] args) {
        SharedResource example = new SharedResource();

        // Create 3 threads that will attempt to enter the critical section
        for (int i = 1; i <= 3; i++) {
            String threadName = "Thread " + i;
            new Thread(() -> example.accessCriticalSection(threadName)).start();
        }
    }
}
