
import java.util.*;

public class VectorDeadLock {

    public static void main(String[] args) {
        // Create two Vector objects
        Vector<Integer> v1 = new Vector<>();
        Vector<Integer> v2 = new Vector<>();

        // Populate the Vectors with some values
        v1.add(1);
        v1.add(2);
        v1.add(3);

        v2.add(4);
        v2.add(5);
        v2.add(6);

        // Thread 1: Attempts to add all elements of v2 into v1
        Thread thread1 = new Thread(() -> {
            System.out.println("Thread 1: Obtaining lock(v1)...");
            synchronized (v1) {
                System.out.println("Thread 1: Got lock(v1)");
                try {
                    // Sleep to increase the chance of deadlock
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 1: obtaining lock(v2) to add to v1...");
                v1.addAll(v2);  // This will lock v2 after locking v1
                System.out.println("Thread 1: Successfully added v2 to v1");
            }
        });

        // Thread 2: Attempts to add all elements of v1 into v2
        Thread thread2 = new Thread(() -> {
            System.out.println("Thread 2: Obtaining lock(v2) to add v1 to v2...");
            synchronized (v2) {
                System.out.println("Thread 2: Got lock(v2)");
                try {
                    // Sleep to increase the chance of deadlock
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 2: obtaining lock(v1) to add to v2...");
                v2.addAll(v1);  // This will lock v1 after locking v2
                System.out.println("Thread 2: Successfully added v1 to v2");
            }
        });

        // Start both threads
        thread1.start();
        thread2.start();
    }
}
