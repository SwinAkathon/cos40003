import java.util.concurrent.*;

/**
 * Implements the example in the slide, reusing class IncrementTest for task objects.
 */
public class IncrementThreadPool {
    public static void main(String[] args) {
        // create tasks
        Runnable task1 = new IncrementTest();
        Runnable task2 = new IncrementTest();
        Runnable task3 = new IncrementTest();

        // create pool to execute tasts

        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(task1);
        executor.execute(task2);
        executor.execute(task3);
        executor.shutdown(); // shutdown worker threads
        
        try {
          executor.awaitTermination(1, TimeUnit.NANOSECONDS);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
    }
}
