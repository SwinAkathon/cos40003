import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

class ConcurrentSum {
    private static final int ARRAY_SIZE = 100_000_000;  // Increase array size for a larger workload
    private static final int NUM_THREADS = 
      Runtime.getRuntime().availableProcessors();
    private static int[] array = new int[ARRAY_SIZE];
    private static AtomicReference<Double> totalSum = new AtomicReference<>(0.0);

    public static void main(String[] args) {
        // Initialize the array with random numbers
        Random random = new Random();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            int num = random.nextInt(100);
            num = (num == 0) ? 50 : num;
            array[i] = num;
        }

        // Count the number of threads before computation
        int threadCount = Thread.getAllStackTraces().keySet().size();
        System.out.println("Number of threads BEFORE computation: " + threadCount);

        // Start time tracking
        long startTime = System.nanoTime();

        // Use a thread pool
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        int chunkSize = ARRAY_SIZE / NUM_THREADS;

        for (int i = 0; i < NUM_THREADS; i++) {
            final int start = i * chunkSize;
            final int end = (i == NUM_THREADS - 1) ? ARRAY_SIZE : (i + 1) * chunkSize;  // Handle any remaining elements
            
            executor.submit(() -> {
                double localSum = 0;
                for (int j = start; j < end; j++) {
                    localSum = Compute.opt.apply(array[j], localSum);
                }
                // totalSum.addAndGet(localSum);  // Atomic update to avoid synchronization
                // Atomically update the total sum using AtomicReference
                final double localSumFinal = localSum;
                totalSum.getAndUpdate(current -> current + localSumFinal);
            });
        }

        // Count the number of threads during computation
        threadCount = Thread.getAllStackTraces().keySet().size();
        System.out.println("Number of threads during computation: " + threadCount);

        // Shut down the executor and wait for all tasks to finish
        executor.shutdown();
        while (!executor.isTerminated()) {}

        // End time tracking
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;  // Convert to milliseconds

        // Format the total sum with thousand separators
        String formattedSum = Compute.format(totalSum.get());

        System.out.println("Total Sum (Concurrent with Thread Pool): " + formattedSum);
        System.out.println("Computation Time (Concurrent with Thread Pool): " + duration + " ms");

        // Count the number of threads after computation
        threadCount = Thread.getAllStackTraces().keySet().size();
        System.out.println("Number of threads after computation: " + threadCount);

    }
}
