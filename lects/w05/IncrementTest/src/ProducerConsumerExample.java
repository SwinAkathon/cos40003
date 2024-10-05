import java.util.ArrayList;
import java.util.List;

/**
 * @overview a simple example of a thread application that uses wait() and notify() to demonstrate inter-thread communication.
 * In this example, we have two threads: A Producer that continously produces data and places it into a buffer. 
 * A Consumer that consumes data from the buffer. 
 * The Consumer wait for the Producer to produce data, and the Producer notify the Consumer when data is available.
 */
public class ProducerConsumerExample {

    public static void main(String[] args) {
        Producer producer = new Producer(5); // Producer encapsulates the shared data

        Thread producerThread = new Thread(producer, "Producer");
        producerThread.start();

        Runnable consumer = new Consumer(producer);
        new Thread(consumer, "Consumer").start();
    }
}

class Producer implements Runnable {
    private final Object lock = new Object();
    private final List<Integer> buffer = new ArrayList<>();
    private final int MAX_CAPACITY; // Maximum buffer size

    public Producer(int capacity) {
        this.MAX_CAPACITY = capacity;
    }

    @Override
    public void run() {
        while (true) { // Periodically produce values
            synchronized (lock) {
                while (buffer.size() == MAX_CAPACITY) {
                    try {
                        System.out.println("Buffer is full, waiting...");
                        lock.wait(); // Wait if the buffer is full
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Handle interrupt
                    }
                }

                // Produce data and add it to the buffer
                int data = (int) (Math.random() * 100);
                buffer.add(data);
                System.out.println("Produced: " + data + " | Buffer: " + buffer);

                lock.notify(); // Notify the consumer that data is available
            }

            // Simulate periodic production with a sleep
            try {
                Thread.sleep(1000); // Produces a new number every second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Handle interrupt
            }
        }
    }

    public void consume() {
        while (true) {
            synchronized (lock) {
                while (buffer.isEmpty()) {
                    try {
                        System.out.println("Buffer is empty, waiting...");
                        lock.wait(); // Wait if the buffer is empty
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Handle interrupt
                    }
                }

                // Consume the first item from the buffer
                int data = buffer.remove(0);
                System.out.println("Consumed: " + data + " | Buffer: " + buffer);

                lock.notify(); // Notify the producer that space is available
            }

            // Simulate periodic consumption with a sleep
            try {
                Thread.sleep(1500); // Consumes an item every 1.5 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Handle interrupt
            }
        }
    }
}

class Consumer implements Runnable {
    private final Producer producer;

    public Consumer(Producer producer) {
        this.producer = producer;
    }

    @Override
    public void run() {
        // Consumer calls the method provided by Producer to consume the data
        producer.consume();
    }
}
