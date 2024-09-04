import java.util.*;

class ConcurrentSumBasic {
    private static final int ARRAY_SIZE = 100_000_000;
    private static final int NUM_THREADS = 4;
    private static int[] array = new int[ARRAY_SIZE];
    private static long totalSum = 0;

    public static void main(String[] args) {
        // Initialize the array with random numbers
        Random random = new Random();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            int num = random.nextInt(100);
            num = (num == 0) ? 50 : num;
            array[i] = num;
        }

        // Start time tracking
        long startTime = System.nanoTime();

        // Create threads
        Thread[] threads = new Thread[NUM_THREADS];
        int chunkSize = ARRAY_SIZE / NUM_THREADS;

        for (int i = 0; i < NUM_THREADS; i++) {
            final int start = i * chunkSize;
            final int end = (i + 1) * chunkSize;
            
            threads[i] = new Thread(() -> {
                long localSum = 0;
                for (int j = start; j < end; j++) {
                    localSum += array[j];
                }
                // Synchronize the access to totalSum to prevent race conditions
                synchronized (ConcurrentSum.class) {
                    totalSum += localSum;
                }
            });
            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // End time tracking
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;  // Convert to milliseconds

        System.out.println("Total Sum (Concurrent): " + totalSum);
        System.out.println("Computation Time (Concurrent): " + duration + " ms");
    }
}
