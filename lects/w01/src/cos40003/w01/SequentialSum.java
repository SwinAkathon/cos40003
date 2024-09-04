import java.util.*;

class SequentialSum {
    // Array size set to 100 million
    private static final int ARRAY_SIZE = 
      100_000_000
      // 1000
      ; 


    public static void main(String[] args) {
        // Initialize the array with random numbers
        int[] array = new int[ARRAY_SIZE];
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

        // Compute the total sum sequentially
        double totalSum = 0;
        for (int i = 0; i < ARRAY_SIZE; i++) {
            totalSum = Compute.opt.apply(array[i], totalSum);
        }

        // Count the number of threads during computation
        threadCount = Thread.getAllStackTraces().keySet().size();
        System.out.println("Number of threads during computation: " + threadCount);

        // End time tracking
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Convert to milliseconds

        // Format the total sum with thousand separators
        String formattedSum = Compute.format(totalSum);

        System.out.println("Total Sum (Sequential): " + formattedSum);
        System.out.println("Computation Time (Sequential): " + duration + " ms");

        // Count the number of threads after computation
        threadCount = Thread.getAllStackTraces().keySet().size();
        System.out.println("Number of threads AFTER computation: " + threadCount);
    }
}
