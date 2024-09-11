import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

class ParallelSum {
    private static final int ARRAY_SIZE = 100_000_000; // Increased array size for a larger workload

    public static void main(String[] args) {
        // Initialize the array with random numbers
        int[] array = new int[ARRAY_SIZE];
        Random random = new Random();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            int num = random.nextInt(100);
            num = (num == 0) ? 50 : num;
            array[i] = num;
        }

        // Count the number of threads during computation
        int threadCount = Thread.getAllStackTraces().keySet().size();
        System.out.println("Number of threads BEFORE computation: " + threadCount);

        // Define the threshold for task splitting
        int threshold = ARRAY_SIZE / (Runtime.getRuntime().availableProcessors() * 10); // Dynamic threshold based on available processors

        // Determine the number of available processors
        int numProcessors = Runtime.getRuntime().availableProcessors();

        // Create a ForkJoinPool with the number of available processors
        ForkJoinPool pool = new ForkJoinPool(numProcessors);

        // Start time tracking
        long startTime = System.nanoTime();

        // Execute the parallel sum task with a dynamic threshold
        double totalSum = pool.invoke(new SumTask(array, 0, ARRAY_SIZE, threshold));

        // Count the number of threads during computation
        threadCount = Thread.getAllStackTraces().keySet().size();
        System.out.println("Number of threads during computation: " + threadCount);

        // Shut down the executor and wait for all tasks to finish
        pool.shutdown();
        while (!pool.isTerminated()) {}

        // End time tracking
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Convert to milliseconds

        // Format the total sum with thousand separators
        String formattedSum = Compute.format(totalSum);

        System.out.println("Total Sum (Parallel): " + formattedSum);
        System.out.println("Computation Time (Parallel): " + duration + " ms");

        // Count the number of threads during computation
        threadCount = Thread.getAllStackTraces().keySet().size();
        System.out.println("Number of threads AFTER computation: " + threadCount);
    }

    static class SumTask extends RecursiveTask<Double> {
        private int[] array;
        private int start, end;
        private int threshold; // Dynamic threshold

        SumTask(int[] array, int start, int end, int threshold) {
            this.array = array;
            this.start = start;
            this.end = end;
            this.threshold = threshold; // Initialize with the provided threshold
        }

        @Override
        protected Double compute() {
            if (end - start <= threshold) { // Use the dynamic threshold
                // Base case: compute sum directly
                double sum = 0;
                for (int i = start; i < end; i++) {
                    // sum += Math.sqrt(array[i]);
                    sum = Compute.opt.apply(array[i], sum);
                }
                return sum;
            } else {
                // Split the task
                int mid = (start + end) / 2;
                SumTask leftTask = new SumTask(array, start, mid, threshold);
                SumTask rightTask = new SumTask(array, mid, end, threshold);

                // Fork and join tasks
                leftTask.fork(); // Start left task in a new thread
                double rightResult = rightTask.compute(); // Compute right task in current thread
                double leftResult = leftTask.join(); // Wait for the left task to finish

                return leftResult + rightResult;
            }
        }
    }
}
