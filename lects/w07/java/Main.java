public class Main {
    public static void main(String[] args) {
        SharedBuffer buffer = new SharedBuffer(5, 10); // Buffer capacity is 5, maxValue is 10

        Producer producer1 = new Producer(buffer);
        Producer producer2 = new Producer(buffer);
        Consumer consumer1 = new Consumer(buffer);
        Consumer consumer2 = new Consumer(buffer);

        // Start producer and consumer threads
        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();


        // wait for producers to finish => done
              // Wait for producer threads to complete
        try {
            producer1.join();
            producer2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Wait for consumers to finish consuming the remaining items in the buffer
        while (!buffer.isProductionComplete()) {
            // Allow the consumers to continue consuming items in the buffer
            try {
                Thread.sleep(1000); // Check every 100 milliseconds
            } catch (InterruptedException e) {
                // Thread.currentThread().interrupt();
            }
        }

        System.out.println("All production is complete.");

        // Interrupt consumer threads once all items have been consumed and production is complete
        consumer1.interrupt();
        consumer2.interrupt();

        try {
            // Ensure consumer threads finish
            consumer1.join();
            consumer2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("All consumption is complete.");
    }
}