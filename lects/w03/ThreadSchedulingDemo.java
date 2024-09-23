public class ThreadSchedulingDemo {

    public static void main(String[] args) {
        // Create multiple threads
        for (int i = 1; i <= 3; i++) {
            int threadNumber = i;
            Thread thread = new Thread(() -> {
                Thread thisThread = Thread.currentThread();
                for (int j = 1; j <= 10; j++) {
                    System.out.println("Thread(" + thisThread.getName() + ", P = " + thisThread.getPriority()+"): " + j);
                    try {
                        // Sleep for a short time to allow context switching
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        // Thread.currentThread().interrupt();
                    }
                }
            });

            // Set thread name and priority
            thread.setName("Worker-" + threadNumber);
            thread.setPriority(Thread.NORM_PRIORITY + threadNumber - 1); // Different priorities
            thread.start();
        }
    }
}
